package com.expediaclon.backend.service

import com.expediaclon.backend.config.HashEncoder
import com.expediaclon.backend.dto.*
import com.expediaclon.backend.exception.*
import com.expediaclon.backend.model.PasswordResetToken
import com.expediaclon.backend.model.RefreshToken
import com.expediaclon.backend.model.User
import com.expediaclon.backend.repository.PasswordResetTokenRepository
import com.expediaclon.backend.repository.RefreshTokenRepository
import com.expediaclon.backend.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.MessageDigest
import java.time.Instant
import java.util.*

@Service
class UserService(
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val hashEncoder: HashEncoder,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val passwordResetTokenRepository: PasswordResetTokenRepository,
    private val mailService: MailService
) {
    fun register(input: UserRequestDto): User {
        val user = userRepository.findByEmail(input.email.trim())

        if (user != null) {
            throw UserAlreadyExistsException("A user with that email already exists")
        }
        return userRepository.save(
            User(
                name = input.name.trim(),
                lastname = input.lastname.trim(),
                phone = input.phone,
                password = hashEncoder.encode(input.password),
                email = input.email
            )
        )
    }

    fun login(email: String, password: String): LoginResponseDto {
        val user = userRepository.findByEmail(email) ?: throw InvalidCredentialsException("Invalid credentials")

        if (!hashEncoder.matches(password, user.password)) {
            throw InvalidCredentialsException("Invalid credentials")
        }

        val newAccessToken = jwtService.generateAccessToken(user.id.toString())
        val newRefreshToken = jwtService.generateRefreshToken(user.id.toString())

        storeRefreshToken(user.id, newRefreshToken)

        return LoginResponseDto(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
            user = UserDtoForBooking(
                id = user.id.toString(),
                name = user.name,
                lastname = user.lastname,
                email = user.email,
                phone = user.phone
            )
        )
    }

    @Transactional
    fun refresh(refreshToken: String): LoginResponseDto {
        if (!jwtService.validateRefreshToken(refreshToken)) {
            throw InvalidCredentialsException("Invalid refresh token")
        }

        val userId = jwtService.getUserIdFromToken(refreshToken)
        val user = userRepository.findById(userId.toLong()).orElseThrow {
            InvalidCredentialsException("Invalid refresh token")
        }

        val hashed = hashToken(refreshToken)
        refreshTokenRepository.findByUserIdAndHashedToken(user.id, hashed)
            ?: throw InvalidCredentialsException(
                "Refresh token not recognized (maybe used or expires)"
            )

        refreshTokenRepository.deleteByUserIdAndHashedToken(user.id, hashed)

        val newAccessToken = jwtService.generateAccessToken(userId)
        val newRefreshToken = jwtService.generateRefreshToken(userId)

        storeRefreshToken(user.id, newRefreshToken)

        return LoginResponseDto(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
            user = UserDtoForBooking(
                id = user.id.toString(),
                name = user.name,
                lastname = user.lastname,
                email = user.email,
                phone = user.phone
            )
        )
    }

    @Transactional
    fun requestPasswordReset(email: String): Boolean {
        val user = userRepository.findByEmail(email)
            ?: throw UserNotFoundException("User not found")

        passwordResetTokenRepository.deleteByUserId(user.id)

        val newResetPasswordToken = jwtService.generateResetPasswordToken(user.id.toString())
        val expiryMs = jwtService.resetPasswordTokenValidityMS
        val expiresAt = Instant.now().plusMillis(expiryMs)

        val resetToken = PasswordResetToken(
            token = newResetPasswordToken,
            userId = user.id,
            expiresAt = expiresAt
        )
        passwordResetTokenRepository.save(resetToken)

        mailService.sendPasswordResetEmail(user.email, newResetPasswordToken)

        return true
    }

    @Transactional
    fun resetPassword(request: PasswordResetRequest): User {
        val resetToken = passwordResetTokenRepository.findByToken(request.token).orElseThrow {
            throw InvalidCredentialsException("Invalid or expired token")
        }

        if (resetToken.expiresAt.isBefore(Instant.now()) || resetToken.isUsed) {
            throw InvalidCredentialsException("Token has expired or has already been used")
        }

        val user = userRepository.findById(resetToken.userId).orElseThrow {
            throw UserNotFoundException("User associated with token not found")
        }

        user.password = hashEncoder.encode(request.newPassword)
        userRepository.save(user)

        resetToken.isUsed = true
        passwordResetTokenRepository.save(resetToken)

        return user
    }

    fun updateUser(input: UserRequestUpdateDto): User {
        val user = getUserById()

        val userByEmail = userRepository.findByEmail(input.email.trim())

        if (userByEmail != null && userByEmail.id != user.id) {
            throw InvalidCredentialsException("A user with that email already exists")
        }

        user.name = input.name.trim()
        user.lastname = input.lastname.trim()
        user.email = input.email.trim()
        user.phone = input.phone
        user.updatedAt = Instant.now()

        return userRepository.save(user)
    }

    private fun storeRefreshToken(userId: Long, rawRefreshToken: String) {
        val hashed = hashToken(rawRefreshToken)
        val expiryMs = jwtService.refreshTokenValidityMS
        val expiresAt = Instant.now().plusMillis(expiryMs)
        refreshTokenRepository.save(
            RefreshToken(
                userId = userId,
                expiresAt = expiresAt,
                hashedToken = hashed
            )
        )
    }

    private fun hashToken(token: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(token.encodeToByteArray())
        return Base64.getEncoder().encodeToString(hashBytes)
    }

    fun getUserById(): User {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw NotAuthenticatedException("User not authenticated")
        val userIdString = authentication.principal.toString()

        val userId = userIdString.toLongOrNull()
            ?: throw InvalidCredentialsException("Invalid token")

        return userRepository.findById(userId).orElseThrow {
            UserNotFoundException("User not found")
        }
    }
}
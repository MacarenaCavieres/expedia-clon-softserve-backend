package com.expediaclon.backend.controller

import com.expediaclon.backend.dto.PasswordResetRequest
import com.expediaclon.backend.dto.TokenPair
import com.expediaclon.backend.dto.UserRequestDto
import com.expediaclon.backend.model.User
import com.expediaclon.backend.service.UserService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class UserGraphQLController(private val userService: UserService) {
    data class RefreshRequest(val refreshToken: String)

    @MutationMapping
    fun registerUser(@Argument input:UserRequestDto):User{
        val created = userService.register(input)
        return created
    }

    @MutationMapping
    fun loginUser(@Argument email: String, @Argument password:String): TokenPair{
        return userService.login(email,password)
    }

    @MutationMapping
    fun refreshToken(@Argument input: RefreshRequest):TokenPair{
        return userService.refresh(input.refreshToken)
    }

    @MutationMapping
    fun forgotPassword(@Argument email: String): Boolean {
        return userService.requestPasswordReset(email)
    }

    // Nuevo: Restablecer la contraseña
    @MutationMapping
    fun resetPassword(@Argument input: PasswordResetRequest): User {
        return userService.resetPassword(input)
    }
}
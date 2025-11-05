package com.expediaclon.backend.controller

import com.expediaclon.backend.dto.PasswordResetRequest
import com.expediaclon.backend.dto.LoginResponseDto
import com.expediaclon.backend.dto.UserRequestDto
import com.expediaclon.backend.dto.UserRequestUpdateDto
import com.expediaclon.backend.model.User
import com.expediaclon.backend.service.UserService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class UserGraphQLController(private val userService: UserService) {

    @MutationMapping
    fun registerUser(@Argument input: UserRequestDto): User {
        val created = userService.register(input)
        return created
    }

    @MutationMapping
    fun loginUser(@Argument email: String, @Argument password: String): LoginResponseDto {
        return userService.login(email, password)
    }

    @MutationMapping
    fun forgotPassword(@Argument email: String): Boolean {
        return userService.requestPasswordReset(email)
    }

    @MutationMapping
    fun resetPassword(@Argument input: PasswordResetRequest): User {
        return userService.resetPassword(input)
    }

    @QueryMapping
    fun getUserInfo(): User {
        return userService.getUserById()
    }

    @MutationMapping
    fun updateUserInfo(@Argument input: UserRequestUpdateDto): User {
        return userService.updateUser(input)
    }
}
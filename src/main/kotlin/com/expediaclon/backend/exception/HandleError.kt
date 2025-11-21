package com.expediaclon.backend.exception

import graphql.ErrorType

abstract class BusinessException(message: String) : RuntimeException(message) {
    abstract fun getErrorCode(): String
}

class UserAlreadyExistsException(message: String) : BusinessException(message) {
    override fun getErrorCode(): String = "CONFLICT"
}

class InvalidCredentialsException(message: String) : BusinessException(message) {
    override fun getErrorCode(): String = "UNAUTHORIZED"
}

class UserNotFoundException(message: String) : BusinessException(message) {
    override fun getErrorCode(): String = "NOT FOUND"
}

class NotAuthenticatedException(message: String) : BusinessException(message) {
    override fun getErrorCode(): String = "BAD REQUEST"
}

class BadRequestMessageException(message: String) : BusinessException(message) {
    override fun getErrorCode(): String = "BAD REQUEST"
}
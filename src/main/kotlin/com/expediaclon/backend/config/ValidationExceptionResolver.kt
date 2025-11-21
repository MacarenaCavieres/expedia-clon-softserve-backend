package com.expediaclon.backend.config

import graphql.GraphQLError
import graphql.GraphqlErrorBuilder
import graphql.schema.DataFetchingEnvironment
import jakarta.validation.ConstraintViolationException
import org.springframework.graphql.execution.DataFetcherExceptionResolver
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class ValidationExceptionResolver : DataFetcherExceptionResolver {

    override fun resolveException(ex: Throwable, env: DataFetchingEnvironment): Mono<List<GraphQLError>> {
        if (ex is ConstraintViolationException) {
            val errors = ex.constraintViolations.map { violation ->
                val field = violation.propertyPath.toString().substringAfterLast('.')
                val message = "$field: ${violation.message}"
                GraphqlErrorBuilder.newError(env)
                    .message(message)
                    .extensions(mapOf("classification" to "BAD_USER_INPUT", "field" to field))
                    .build()
            }
            return Mono.just(errors)
        }
        return Mono.empty()
    }
}
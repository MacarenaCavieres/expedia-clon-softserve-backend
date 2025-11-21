package com.expediaclon.backend.config

import com.expediaclon.backend.exception.BusinessException
import graphql.GraphQLError
import graphql.ErrorType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.DataFetcherExceptionResolver

import reactor.core.publisher.Mono

@Configuration
class GraphQlConfig {

    @Bean
    fun customExceptionResolver(): DataFetcherExceptionResolver {
        return DataFetcherExceptionResolver { ex, env ->
            if (ex is BusinessException) {
                val error = GraphQLError.newError()
                    .errorType(ErrorType.DataFetchingException)
                    .message(ex.message)
                    .path(env.executionStepInfo.path)
                    .extensions(mapOf("code" to ex.getErrorCode()))
                    .build()

                Mono.just(listOf(error))
            } else {
                Mono.empty()
            }
        }
    }
}
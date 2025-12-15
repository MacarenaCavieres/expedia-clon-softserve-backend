package com.expediaclon.backend.ai

import com.expediaclon.backend.dto.HotelCardDto
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class SemanticSearchGraphQLController(
    private val service: SemanticHotelSearchService
) {

    @QueryMapping
    fun semanticSearch(
        @Argument query: String,
        @Argument limit: Int?
    ): List<HotelCardDto> {
        return service.search(
            query = query,
            limit = limit ?: 4
        )
    }
}

package com.expediaclon.backend.controller

import com.expediaclon.backend.dto.HotelCardDto
import com.expediaclon.backend.dto.HotelDetailDto
import com.expediaclon.backend.service.HotelService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class HotelGraphQLController(
    private val hotelService: HotelService
) {
    @QueryMapping
    fun searchHotels(@Argument city: String, @Argument passengerCount: Int): List<HotelCardDto> =
        hotelService.searchHotels(city, passengerCount)


    @QueryMapping
    fun hotelDetailsById(@Argument id: Long): HotelDetailDto =
        hotelService.getHotelDetails(id)

}
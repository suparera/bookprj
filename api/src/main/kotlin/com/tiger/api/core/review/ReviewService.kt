package com.tiger.api.core.review

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

interface ReviewService {

    @GetMapping("/review", produces = [APPLICATION_JSON_VALUE])
    fun getReviews(
        @RequestParam("productId", required = true) productId: Int,
    ): List<Review>
}
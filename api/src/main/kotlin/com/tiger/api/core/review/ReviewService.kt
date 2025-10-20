package com.tiger.api.core.review

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

interface ReviewService {

    @PostMapping("/reviews", consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun createReview(@RequestBody review: Review): Review

    @GetMapping("/review", produces = [APPLICATION_JSON_VALUE])
    fun getReviews(
        @RequestParam("productId", required = true) productId: Int,
    ): List<Review>

    @DeleteMapping("/review")
    fun deleteReview(@RequestParam("productId") productId: Int)
}
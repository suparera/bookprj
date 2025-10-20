package com.tiger.api.core.recommendation

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
interface RecommendationService {

    @PostMapping("recommendations", consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun createRecommendation(@RequestBody recommendation: Recommendation): Recommendation

    @GetMapping("/recommendation", produces = [APPLICATION_JSON_VALUE])
    fun getRecommendations(
        @RequestParam("productId", required = true) productId: Int,
    ): List<Recommendation>

    @DeleteMapping("/recommendation", produces = [APPLICATION_JSON_VALUE])
    fun deleteRecommendations(@RequestParam productId: Int)
}
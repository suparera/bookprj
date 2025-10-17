package com.tiger.api.core.recommendation

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

interface RecommendationService {

    @GetMapping("/recommendation", produces = [APPLICATION_JSON_VALUE])
    fun getRecommendations(
        @RequestParam("productId", required = true) productId: Int,
    ): List<Recommendation>
}
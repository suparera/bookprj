package se.magnus.microservices.core.recommendation.services

import com.tiger.api.core.recommendation.Recommendation
import se.magnus.microservices.core.recommendation.persistence.RecommendationEntity

class RecommendationMapper {
    fun entityToApi(e: RecommendationEntity): Recommendation =
        Recommendation(
            productId = e.productId?:-1,
            recommendationId = e.recommendationId?:-1,
            author = e.author?:"",
            content = e.content?:"",
            rate = e.rating?:0,
            serviceAddress = ""
        )

    fun apiToEntity(api: Recommendation): RecommendationEntity =
        RecommendationEntity(
            productId = api.productId,
            recommendationId = api.recommendationId,
            author = api.author,
            content = api.content,
            rating = api.rate
        )
}
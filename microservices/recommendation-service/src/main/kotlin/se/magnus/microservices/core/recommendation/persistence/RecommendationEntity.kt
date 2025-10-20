package se.magnus.microservices.core.recommendation.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "recommendations")
@CompoundIndex(name = "prod-rec-id", unique = true, def="{'productId':1,'recommendationId':1}")
class RecommendationEntity(
    @Id var id: String? = null,
    @Version var version: Int = 0,
    var productId: Int? = null,
    var recommendationId: Int? = null,
    var author: String? = null,
    var rating: Int? = null,
    var content: String? = null,
)

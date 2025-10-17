package se.magnus.microservices.core.review.persistence

import jakarta.persistence.*

@Entity
@Table(name = "reviews", indexes = [Index(name = "reviews_unique_idx", unique = true, columnList = "productId,reviewId")])
class ReviewEntity (
    @Id @GeneratedValue var id: Int = 0,
    @Version var version: Int = 0,
    var productId: Int = 0,
    var reviewId: Int = 0,
    var author: String = "",
    var subject: String = "",
    var content: String = "",
)
package se.magnus.microservices.core.review.services

import com.tiger.api.core.review.Review
import org.springframework.stereotype.Component
import se.magnus.microservices.core.review.persistence.ReviewEntity

@Component
class ReviewMapper {

    fun apiToEntity(api: Review): ReviewEntity =
        ReviewEntity(
            productId = api.productId,
            author = api.author,
            subject = api.subject,
            content = api.content,
        )

    fun entityToApi(entity: ReviewEntity): Review =
        Review(
            reviewId = entity.reviewId,
            productId = entity.productId,
            author = entity.author,
            subject = entity.subject,
            content = entity.content,
            serviceAddress = ""
        )
}
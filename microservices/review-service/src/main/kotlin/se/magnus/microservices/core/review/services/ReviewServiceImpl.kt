package se.magnus.microservices.core.review.services

import com.tiger.api.core.review.Review
import com.tiger.api.core.review.ReviewService
import com.tiger.util.http.ServiceUtil
import org.slf4j.LoggerFactory
import se.magnus.microservices.core.review.persistence.ReviewRepository

class ReviewServiceImpl(
    private val repository: ReviewRepository,
    private val mapper: ReviewMapper,
    private val serviceUtil: ServiceUtil
): ReviewService {

    companion object {
        val log = LoggerFactory.getLogger(ReviewServiceImpl::class.java)
    }

    override fun createReview(review: Review): Review {
        val entity = mapper.apiToEntity(review)
        val newEntity = repository.save(entity)

        log.debug("Review created: {}/{}, id:{}", newEntity.productId, newEntity.reviewId, newEntity.id)
        return mapper.entityToApi(newEntity)
    }

    override fun getReviews(productId: Int): List<Review> =
        repository.findByProductId(productId).map(mapper::entityToApi)

    override fun deleteReview(productId: Int) {
        repository.deleteByProductId(productId)
    }
}
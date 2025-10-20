package se.magnus.microservices.core.review.persistence

import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional

interface ReviewRepository : CrudRepository<ReviewEntity, Int> {
    @Transactional(readOnly=true)
    fun findByProductId(id: Int): List<ReviewEntity>

    fun deleteByProductId(productId: Int)
}
package se.magnus.microservices.core.recommendation.services

import com.tiger.api.core.recommendation.Recommendation
import com.tiger.api.core.recommendation.RecommendationService
import com.tiger.api.exceptions.InvalidInputException
import com.tiger.util.http.ServiceUtil
import org.slf4j.LoggerFactory
import se.magnus.microservices.core.recommendation.persistence.RecommendationRepository

class RecommendationServiceImpl(
    private val mapper: RecommendationMapper,
    private val repository: RecommendationRepository,
    private val serviceUtil: ServiceUtil,
): RecommendationService {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    override fun createRecommendation(recommendation: Recommendation): Recommendation {
        val entity = mapper.apiToEntity(recommendation)
        val newEntity = repository.save(entity)
        log.info("New recommendation: {}", newEntity)
        return mapper.entityToApi(newEntity)
    }

    override fun getRecommendations(productId: Int): List<Recommendation> {
        require(productId > 0) { throw InvalidInputException("Invalid productId: $productId") }

        val recs = repository.findByProductId(productId)
            .map(mapper::entityToApi)
            .onEach { it.serviceAddress = serviceUtil.serviceAddress }
        return recs
    }

    override fun deleteRecommendations(productId: Int) =
        repository.deleteRecommendations(productId)
}
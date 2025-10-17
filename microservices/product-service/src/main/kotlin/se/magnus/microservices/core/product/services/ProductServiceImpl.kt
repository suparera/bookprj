package se.magnus.microservices.core.product.services

import com.tiger.api.core.product.Product
import com.tiger.api.core.product.ProductService
import com.tiger.api.exceptions.InvalidInputException
import com.tiger.api.exceptions.NotFoundException
import com.tiger.util.http.ServiceUtil
import org.slf4j.LoggerFactory
import org.springframework.dao.DuplicateKeyException
import org.springframework.web.bind.annotation.RestController
import se.magnus.microservices.core.product.persistence.ProductRepository

@RestController
class ProductServiceImpl(
    private val serviceUtil: ServiceUtil,
    private val repository: ProductRepository,
    private val mapper: ProductMapper
): ProductService {

    companion object {
        val log = LoggerFactory.getLogger(ProductServiceImpl::class.java)
    }

    override fun getProduct(productId: Int): Product {
        require(productId >= 1) { "Invalid productId: $productId" }

        val entity = repository.findByProductId(productId)
            ?: throw NotFoundException("No product found for productId: $productId")

        val response = mapper.entityToApi(entity).apply {
            serviceAddress = serviceUtil.serviceAddress
        }

        log.debug("getProduct: found productId: {}", response.productId)
        return response
    }

    override fun createProduct(product: Product): Product {
        try {
            val entity = mapper.apiToEntity(product)
            val newEntity = repository.save(entity)
            log.debug("createProduct: created new product: {}", product.productId)
            return mapper.entityToApi(newEntity)
        } catch (_: DuplicateKeyException) {
            throw InvalidInputException("Duplicate key exist for productId: ${product.productId}")
        }
    }

    override fun deleteProduct(productId: Int) {
        repository.findByProductId(productId)
            ?.also { repository.delete(it) }
            ?: throw NotFoundException("No products found for productId: $productId")
    }
}
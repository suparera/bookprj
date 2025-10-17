package se.magnus.microservices.core.product.services

import com.tiger.api.core.product.Product
import org.springframework.stereotype.Component
import se.magnus.microservices.core.product.persistence.ProductEntity

@Component
class ProductMapper {

    fun apiToEntity(api: Product): ProductEntity =
        ProductEntity(
            productId = api.productId,
            name = api.name,
            weight = api.weight
        )

    fun entityToApi(entity: ProductEntity): Product =
        Product(
            productId = entity.productId,
            name = entity.name,
            weight = entity.weight,
            serviceAddress = ""
        )
}
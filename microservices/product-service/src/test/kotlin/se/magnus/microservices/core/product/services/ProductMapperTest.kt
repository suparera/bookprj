package se.magnus.microservices.core.product.services

import com.tiger.api.core.product.Product
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import se.magnus.microservices.core.product.persistence.ProductEntity

class ProductMapperTest {

    private val mapper: ProductMapper = ProductMapper()

    @Test
    fun apiToEntity() {
        val api = Product(
            productId = 2,
            name = "product1",
            weight = 10,
            serviceAddress = "localhost:7071",
        )

        val entity = mapper.apiToEntity(api)
        assertNotNull(entity)
        assertEquals(api.productId, entity.productId)
        assertEquals(api.name, entity.name)
        assertEquals(api.weight, entity.weight)
        assertNull(entity.id)
        assertNull(entity.version)
    }

    @Test
    fun entityToApi() {
        val entity = ProductEntity(
            productId = 3,
            name = "product2",
            weight = 15
        )
        entity.id = "507f1f77bcf86cd799439011"
        entity.version = 1

        val api = mapper.entityToApi(entity)
        assertNotNull(api)
        assertEquals(entity.productId, api.productId)
        assertEquals(entity.name, api.name)
        assertEquals(entity.weight, api.weight)
        assertEquals("", api.serviceAddress) // serviceAddress is set to empty string by mapper
    }

}
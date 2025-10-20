package se.magnus.microservices.core.product

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.dao.DuplicateKeyException
import org.springframework.dao.OptimisticLockingFailureException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort.Direction.ASC
import se.magnus.microservices.core.product.persistence.ProductEntity
import se.magnus.microservices.core.product.persistence.ProductRepository
import se.magnus.microservices.core.product.services.MongoDbTestBase

@DataMongoTest
class PersistenceTests : MongoDbTestBase() {

    @Autowired
    private lateinit var repository: ProductRepository

    private lateinit var savedEntity: ProductEntity

    @BeforeEach
    fun setupDb() {
        repository.deleteAll()

        val entity = ProductEntity(1, "n", 1)
        savedEntity = repository.save(entity)

        assertEqualsProduct(entity, savedEntity)
    }

    @Test
    fun create() {
        val newEntity = ProductEntity(2, "n", 2)
        repository.save(newEntity)

        val foundEntity = repository.findById(newEntity.id!!).get()
        assertEqualsProduct(newEntity, foundEntity)

        assertEquals(2, repository.count())
    }

    @Test
    fun update() {
        savedEntity.name = "n2"
        repository.save(savedEntity)

        val foundEntity = repository.findById(savedEntity.id!!).get()
        assertEquals(1, foundEntity.version)
        assertEquals("n2", foundEntity.name)
    }

    @Test
    fun delete() {
        repository.delete(savedEntity)
        assertFalse(repository.existsById(savedEntity.id!!))
    }

    @Test
    fun getByProductId() {
        val entity = repository.findByProductId(savedEntity.productId)

        assertNotNull(entity)
        assertEqualsProduct(savedEntity, entity!!)
    }

    // TODO fix this error
    @Test
    fun duplicateError() {
        assertThrows(DuplicateKeyException::class.java) {
            val existEntity = repository.findByProductId(savedEntity.productId)
            val entity = ProductEntity(savedEntity.productId, "n", 1)
            repository.save(entity)
            print("test")
        }
    }

    @Test
    fun optimisticLockError() {
        // Store the saved entity in two separate entity objects
        val entity1 = repository.findById(savedEntity.id!!).get()
        val entity2 = repository.findById(savedEntity.id!!).get()

        // Update the entity using the first entity object
        entity1.name = "n1"
        repository.save(entity1)

        // Update the entity using the second entity object.
        // This should fail since the second entity now holds an old version number, i.e. an Optimistic Lock Error
        assertThrows(OptimisticLockingFailureException::class.java) {
            entity2.name = "n2"
            repository.save(entity2)
        }

        // Get the updated entity from the database and verify its new state
        val updatedEntity = repository.findById(savedEntity.id!!).get()
        assertEquals(1, updatedEntity.version)
        assertEquals("n1", updatedEntity.name)
    }

    @Test
    fun paging() {
        repository.deleteAll()

        val newProducts = (1001..1010).map { i ->
            ProductEntity(i, "name $i", i)
        }
        repository.saveAll(newProducts)

        var nextPage: Pageable = PageRequest.of(0, 4, ASC, "productId")
        nextPage = testNextPage(nextPage, "[1001, 1002, 1003, 1004]", true)
        nextPage = testNextPage(nextPage, "[1005, 1006, 1007, 1008]", true)
        nextPage = testNextPage(nextPage, "[1009, 1010]", false)
    }

    private fun testNextPage(nextPage: Pageable, expectedProductIds: String, expectsNextPage: Boolean): Pageable {
        val productPage: Page<ProductEntity> = repository.findAll(nextPage)
        assertEquals(
            expectedProductIds,
            productPage.content.map { it.productId }.toString()
        )
        assertEquals(expectsNextPage, productPage.hasNext())
        return productPage.nextPageable()
    }

    private fun assertEqualsProduct(expectedEntity: ProductEntity, actualEntity: ProductEntity) {
        assertEquals(expectedEntity.id, actualEntity.id)
        assertEquals(expectedEntity.version, actualEntity.version)
        assertEquals(expectedEntity.productId, actualEntity.productId)
        assertEquals(expectedEntity.name, actualEntity.name)
        assertEquals(expectedEntity.weight, actualEntity.weight)
    }
}
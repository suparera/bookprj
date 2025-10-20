package se.magnus.microservices.core.product

import com.tiger.api.core.product.Product
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import se.magnus.microservices.core.product.persistence.ProductRepository
import se.magnus.microservices.core.product.services.MongoDbTestBase
import kotlin.test.assertTrue


@SpringBootTest(webEnvironment = RANDOM_PORT)
class ProductServiceApplicationTests: MongoDbTestBase() {

	@Autowired
	private lateinit var repository: ProductRepository

	@Autowired
	private lateinit var client: WebTestClient

	@BeforeEach
	fun setup() = repository.deleteAll()

	@Test
	fun contextLoads() {
	}

	@Test
	fun getProductById() {
		val productId = 1
		postAndVerifyProduct(productId, HttpStatus.OK)
		assertTrue { repository.findByProductId(productId) != null }
	}

	private fun postAndVerifyProduct(productId: Int, expectedStatus: HttpStatus): WebTestClient.BodyContentSpec {
		val product = Product(productId, "Name_"+productId, 10, "SA")
		return client.post()
			.uri("/product")
			.body(BodyInserters.fromValue(product))
			.accept(APPLICATION_JSON)
			.exchange()
			.expectStatus().isEqualTo(expectedStatus)
			.expectHeader().contentType(APPLICATION_JSON)
			.expectBody()
	}
}

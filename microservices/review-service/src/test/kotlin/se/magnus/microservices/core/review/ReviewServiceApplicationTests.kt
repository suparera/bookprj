package se.magnus.microservices.core.review

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
class ReviewServiceApplicationTests {

	companion object {
		@Container
		@ServiceConnection
		val mysql: MySQLContainer<*> = MySQLContainer("mysql:8.0")
	}

	@Test
	fun contextLoads() {
	}

}

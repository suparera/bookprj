package se.magnus.microservices.core.product.services

import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.MongoDBContainer

abstract class MongoDbTestBase {

    companion object {
        @ServiceConnection
        private val database: MongoDBContainer = MongoDBContainer("mongo:8.0.5")

        init {
            database.start()
        }
    }
}
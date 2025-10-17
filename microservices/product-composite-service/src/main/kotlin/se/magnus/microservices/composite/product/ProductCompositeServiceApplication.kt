package se.magnus.microservices.composite.product

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.tiger")
class ProductCompositeServiceApplication

fun main(args: Array<String>) {
	runApplication<ProductCompositeServiceApplication>(*args)
}

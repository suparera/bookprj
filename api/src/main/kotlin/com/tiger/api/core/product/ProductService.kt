package com.tiger.api.core.product

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

interface ProductService {

    @GetMapping("/product/{productId}", produces = [APPLICATION_JSON_VALUE])
    fun getProduct(@PathVariable productId: Int): Product

    @PostMapping("/product", consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun createProduct(@RequestBody product: Product): Product

    @DeleteMapping("/product/{productId}")
    fun deleteProduct(@PathVariable productId: Int)
}
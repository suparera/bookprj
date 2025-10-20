# Kotlin my note

## Kotlin tip

### onEach
```
        val recs = repository.findByProductId(productId)
            .map(mapper::entityToApi)
            .onEach { it.serviceAddress = serviceUtil.serviceAddress }
```
also other ways to change in list
```
  // Option 2: Using map to transform each element
  repository.findByProductId(productId)
      .map(mapper::entityToApi)
      .map { it.apply { serviceAddress = serviceUtil.serviceAddress } }

  // Option 3: Using also with forEach
  repository.findByProductId(productId)
      .map(mapper::entityToApi)
      .also { list -> list.forEach { it.serviceAddress = serviceUtil.serviceAddress } }
```
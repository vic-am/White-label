package com.victoramaral.whitelabel.domain.usecase

import com.victoramaral.whitelabel.data.ProductRepository
import com.victoramaral.whitelabel.domain.model.Product

class GetProductsUseCaseImpl(
    private val productRepository: ProductRepository
) : GetProductsUseCase {

    override suspend fun invoke(): List<Product> {
        return productRepository.getProducts()
    }
}
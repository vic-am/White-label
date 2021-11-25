package com.victoramaral.whitelabel.domain.usecase

import com.victoramaral.whitelabel.domain.model.Product

interface GetProductsUseCase {

    suspend operator fun invoke(): List<Product>

}
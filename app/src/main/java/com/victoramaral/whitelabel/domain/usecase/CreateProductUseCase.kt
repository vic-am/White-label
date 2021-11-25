package com.victoramaral.whitelabel.domain.usecase

import android.net.Uri
import com.victoramaral.whitelabel.domain.model.Product

interface CreateProductUseCase {

    suspend operator fun invoke(
        description: String,
        price: Double,
        imageUri: Uri
    ): Product

}
package com.victoramaral.whitelabel.domain.usecase

import android.net.Uri
import com.victoramaral.whitelabel.data.ProductRepository

class UploadProductImageUseCaseImpl(
    private val productRepository: ProductRepository
) : UploadProductImageUseCase {

    override suspend fun invoke(imageUri: Uri): String {
        return productRepository.uploadProductImage(imageUri)
    }
}
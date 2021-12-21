package com.victoramaral.whitelabel.data

import android.net.Uri
import com.victoramaral.whitelabel.domain.model.Product

interface ProductDataSource {

    suspend fun getProducts(): List<Product>

    suspend fun uploadProductImage(imageUri: Uri): String

    suspend fun createProduct(product: Product): Product

}
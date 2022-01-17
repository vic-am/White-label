package com.victoramaral.whitelabel.ui.addproduct

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victoramaral.whitelabel.R
import com.victoramaral.whitelabel.domain.usecase.CreateProductUseCase
import com.victoramaral.whitelabel.util.fromCurrency
import kotlinx.coroutines.launch

class AddProductViewModel(
    private val createProductUseCase: CreateProductUseCase
) : ViewModel() {

    private var isFormValid = true

    private val _imageUriErrorResId = MutableLiveData<Int>()
    val imageUriErrorResId: LiveData<Int> = _imageUriErrorResId

    private val _descriptionFieldErrorResId = MutableLiveData<Int?>()
    val descriptionFieldErrorResId: LiveData<Int?> = _descriptionFieldErrorResId

    private val _priceFieldErrorResId = MutableLiveData<Int?>()
    val priceFieldErrorResId: LiveData<Int?> = _priceFieldErrorResId

    fun createProduct(description: String, price: String, imageURI: Uri?) =
        viewModelScope.launch {
            _imageUriErrorResId.value = getDrawableBackgroundIfFieldIsEmpty(imageURI)
            _descriptionFieldErrorResId.value = getErrorStringIfFieldIsEmpty(description)
            _priceFieldErrorResId.value = getErrorStringIfFieldIsEmpty(price)

            if (isFormValid) {
                try {
                    val product =
                        createProductUseCase(description, price.fromCurrency(), imageURI!!)
                } catch (e: Exception) {
                    Log.d("CreateProduct", e.toString())
                }
            }

        }

    private fun getErrorStringIfFieldIsEmpty(value: String): Int? {
        return if (value.isEmpty()) {
            isFormValid = false
            R.string.add_product_field_error
        } else null

    }

    private fun getDrawableBackgroundIfFieldIsEmpty(value: Uri?): Int {
        return if (value == null) {
            isFormValid = false
            R.drawable.background_product_image_error
        } else R.drawable.background_product_image

    }
}
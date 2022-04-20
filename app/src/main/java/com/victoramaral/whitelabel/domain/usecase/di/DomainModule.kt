package com.victoramaral.whitelabel.domain.usecase.di

import com.victoramaral.whitelabel.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    fun bindCreateProductUseCase(
        useCaseImpl: CreateProductUseCaseImpl
    ): CreateProductUseCase

    @Binds
    fun bindGetProductUseCase(
        useCaseImpl: GetProductsUseCaseImpl
    ): GetProductsUseCase

    @Binds
    fun bindUploadProductsImageUseCase(
        useCaseImpl: UploadProductImageUseCaseImpl
    ): UploadProductImageUseCase


}
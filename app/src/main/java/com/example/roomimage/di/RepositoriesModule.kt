package com.example.roomimage.di

import com.example.db.dao.ProductDao
import com.example.db.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    @Singleton
    fun providesProductRepository(productDao: ProductDao): ProductRepository = ProductRepository(productDao)
}
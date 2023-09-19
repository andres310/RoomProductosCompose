package com.example.roomimage.di

import com.example.db.dao.ProductDao
import com.example.db.datasource.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    @Singleton
    fun providesProductDao(database: AppDatabase): ProductDao = database.productDao()
}
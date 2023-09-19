package com.example.roomimage.di

import android.content.Context
import com.example.db.datasource.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providerAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getDatabase(context)
}
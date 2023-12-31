package com.example.roomimage.app

import android.app.Application
import com.example.db.datasource.AppDatabase
import com.example.db.repository.ProductRepository
import dagger.hilt.android.HiltAndroidApp

// Forma con inyeccion de dependencia manual
/*class ProductApplication: Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { ProductRepository(database.productDao()) }
}*/

// Forma con inyeccion de dependencia con libreria Hilt

@HiltAndroidApp
class ProductApplication : Application() {

}
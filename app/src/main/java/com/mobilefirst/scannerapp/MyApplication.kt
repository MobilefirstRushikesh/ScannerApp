package com.mobilefirst.scannerapp

import android.app.Application
import androidx.room.Room
import com.mobilefirst.scannerapp.data.local.AppDatabase
import com.mobilefirst.scannerapp.di.AppComponent
import com.mobilefirst.scannerapp.di.AppModule
import com.mobilefirst.scannerapp.di.DaggerAppComponent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    companion object {
        lateinit var database: AppDatabase
            private set
    }
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "aadhaar_db"
        ).build()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule())
            .build()

    }
}

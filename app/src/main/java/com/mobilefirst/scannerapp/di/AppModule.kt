package com.mobilefirst.scannerapp.di

import android.app.Application
import androidx.room.Room
import com.mobilefirst.scannerapp.data.local.AadhaarDataDao
import com.mobilefirst.scannerapp.data.local.AadhaarDataRepository
import com.mobilefirst.scannerapp.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideAadhaarDataDao(appDatabase: AppDatabase): AadhaarDataDao {
        return appDatabase.aadhaarQrCodeDataDao()
    }

    @Provides
    fun provideAadhaarDataRepository(aadhaarDataDao: AadhaarDataDao): AadhaarDataRepository {
        return AadhaarDataRepository(aadhaarDataDao)
    }

    @Provides
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            AppDatabase::class.java,
            "aadhaar_db"
        ).build()
    }
}


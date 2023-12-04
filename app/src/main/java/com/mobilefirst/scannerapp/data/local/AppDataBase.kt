package com.mobilefirst.scannerapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobilefirst.scannerapp.model.AadhaarData

@Database(entities = [AadhaarData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun aadhaarQrCodeDataDao(): AadhaarDataDao
}

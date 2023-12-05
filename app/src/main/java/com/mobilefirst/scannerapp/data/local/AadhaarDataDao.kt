package com.mobilefirst.scannerapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobilefirst.scannerapp.model.AadhaarData

@Dao
interface AadhaarDataDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAadhaarQRCodeData(aadhaarData: AadhaarData)

    @Query("SELECT * FROM aadhaar_db")
    suspend fun getAllAadhaarQRCodeData(): List<AadhaarData>
}



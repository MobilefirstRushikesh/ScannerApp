package com.mobilefirst.scannerapp.data.local

import com.mobilefirst.scannerapp.model.AadhaarData

class AadhaarDataRepository(private val aadhaarDataDao: AadhaarDataDao) {

    suspend fun insertAadhaarData(aadhaarData: AadhaarData) {
        aadhaarDataDao.insertAadhaarQRCodeData(aadhaarData)
    }

    suspend fun getAadhaarData():List<AadhaarData> {
       return aadhaarDataDao.getAllAadhaarQRCodeData()
    }
}

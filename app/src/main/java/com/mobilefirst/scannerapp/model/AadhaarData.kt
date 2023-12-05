package com.mobilefirst.scannerapp.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "aadhaar_db",indices = [Index(value = ["uid"], unique = true)])
data class AadhaarData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val uid: String,
    val name: String,
    val gender: String,
    val yob: String,
    val co: String,
    val loc: String,
    val vtc: String,
    val dist: String,
    val state: String,
    val pc: String
)

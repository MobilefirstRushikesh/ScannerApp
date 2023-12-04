package com.mobilefirst.scannerapp.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilefirst.scannerapp.model.AadhaarData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AadhaarDataViewModel@Inject constructor(private val repository: AadhaarDataRepository) : ViewModel() {


    // Inserting the aadhaar data
    private val _insertionResult = MutableLiveData<Boolean>()
    val insertionResult: LiveData<Boolean> get() = _insertionResult

    fun insertAadhaarData(aadhaarData: AadhaarData) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.insertAadhaarData(aadhaarData)
                _insertionResult.postValue(true)
            } catch (e: Exception) {
                _insertionResult.postValue(false)
            }
        }
    }

    // getting all the aadhaar data
    private val _savedData = MutableLiveData<List<AadhaarData>>()
    val savedData: LiveData<List<AadhaarData>> get() = _savedData

    fun getAadhaarData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                _savedData.postValue(repository.getAadhaarData())
            } catch (e: Exception) {
                _savedData.postValue(null)
            }
        }
    }

}

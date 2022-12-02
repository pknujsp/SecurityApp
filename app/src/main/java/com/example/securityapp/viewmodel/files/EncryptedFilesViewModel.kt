package com.example.securityapp.viewmodel.files

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.model.file.data.FileDto
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EncryptedFilesViewModel : ViewModel() {
    val encryptedFiles = MutableLiveData(mutableListOf<FileDto>())

    fun getEncryptedFiles() {
        viewModelScope.launch {

            withContext(Main) {
            }
        }
    }
}
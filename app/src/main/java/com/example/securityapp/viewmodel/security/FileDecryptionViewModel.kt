package com.example.securityapp.viewmodel.security

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.securityapp.model.file.data.FileDto

class FileDecryptionViewModel : ViewModel(){
    val decryptedFile = MutableLiveData<FileDto>()
}
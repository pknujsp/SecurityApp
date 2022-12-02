package com.example.securityapp.viewmodel.security

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.securityapp.model.file.data.FileDto

class FileEncryptionViewModel : ViewModel() {
    val encryptedFile = MutableLiveData<FileDto>()
    var selectedFileForEncryption: Uri? = null
}
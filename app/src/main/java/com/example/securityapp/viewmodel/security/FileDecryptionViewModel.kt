package com.example.securityapp.viewmodel.security

import android.content.Context
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.securityapp.model.file.data.FileDto
import com.example.securityapp.security.calc.FileDecryptor
import com.example.securityapp.security.calc.FileEncryptor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.time.LocalDateTime

class FileDecryptionViewModel : ViewModel() {
    val decryptedFile = MutableLiveData<FileDto>()
    var encryptedFile: FileDto? = null

    fun decryptFile(context: Context, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = FileDecryptor.decryptFile(context, encryptedFile!!, password)
            withContext(Dispatchers.Main) {
                decryptedFile.value = encryptedFile!!.run {
                    FileDto(false, name, result!!.toUri()).also {
                        it.file = result
                    }
                }
            }
        }
    }
}
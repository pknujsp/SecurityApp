package com.example.securityapp.viewmodel.security

import android.content.Context
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.securityapp.model.file.data.FileDto
import com.example.securityapp.security.calc.FileDecryptor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.crypto.BadPaddingException

class FileDecryptionViewModel : ViewModel() {
    val decryptedFile = MutableLiveData<Result<FileDto>>()
    var encryptedFile: FileDto? = null

    fun decryptFile(context: Context, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val decryptionResult = FileDecryptor.decryptFile(context, encryptedFile!!, password)

                withContext(Main) {
                    decryptedFile.value = Result.success(encryptedFile!!.run {
                        FileDto(false, name, decryptionResult.toUri()).also {
                            it.file = decryptionResult
                        }
                    })
                }
            } catch (e: Exception) {
                withContext(Main) {
                    decryptedFile.value = Result.failure(e)
                }
            }

        }
    }
}

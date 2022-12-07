package com.example.securityapp.viewmodel.security

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.securityapp.model.file.data.FileDto
import com.example.securityapp.security.calc.FileEncryptor
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class FileEncryptionViewModel : ViewModel() {
    val encryptedFile = MutableLiveData<Boolean>()
    var selectedFileForEncryption: Uri? = null
    val selectedFile = MutableLiveData<FileDto>()
    var job: Job? = null

    override fun onCleared() {
        super.onCleared()
    }

    fun loadFileInfo(context: Context) {
        selectedFileForEncryption?.run {
            context.contentResolver.query(this, null, null, null, null)
        }?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            val fileDto = FileDto(
                false, cursor.getString(nameIndex), selectedFileForEncryption!!
            )

            cursor.close()
            selectedFile.value = fileDto
        }
    }

    fun encryptFile(context: Context, password: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            val result = FileEncryptor.encryptFile(context, selectedFile.value!!, password)
            withContext(Main) {
                encryptedFile.value = result
            }
        }
    }
}
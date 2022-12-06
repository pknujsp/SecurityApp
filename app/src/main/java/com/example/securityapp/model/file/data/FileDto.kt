package com.example.securityapp.model.file.data

import android.net.Uri
import java.io.File


data class FileDto(
    val isEncrypted: Boolean,
    val name: String,
    val uri: Uri,
    val encryptedDateTime: String,
    val decryptedDateTime: String
) {
    var file: File? = null

    fun getExtension(): String = name.substring(name.lastIndexOf(".") + 1, name.length)
    fun getRealName(): String = name.substring(0, name.lastIndexOf("."))


}

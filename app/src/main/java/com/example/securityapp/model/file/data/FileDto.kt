package com.example.securityapp.model.file.data

import android.net.Uri

data class FileDto(
    val isEncrypted: Boolean,
    val name: String,
    val uri: Uri,
    val encryptedDateTime: String
)

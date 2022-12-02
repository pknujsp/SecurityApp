package com.example.securityapp.model.file.repository

import android.content.Context

interface FilesRepository {
    suspend fun getEncryptedFiles(context: Context)
}
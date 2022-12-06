package com.example.securityapp.model.file.repository

import com.example.securityapp.model.file.data.FileDto

interface FilesRepository {
    suspend fun getEncryptedFiles(): List<FileDto>
}
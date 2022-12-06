package com.example.securityapp.model.file.repository

import android.os.Environment
import androidx.core.net.toUri
import com.example.securityapp.model.file.data.FileDto
import java.io.File

object FilesRepositoryImpl : FilesRepository {
    override suspend fun getEncryptedFiles(): List<FileDto> {
        val path = "${Environment.getExternalStorageDirectory()}/encrypted_files"
        val directory = File(path)
        val files = directory.listFiles()

        val fileDtoList = mutableListOf<FileDto>()
        var dto: FileDto? = null
        for (file in files) {
            if (file.extension == "jsp") {
                dto = FileDto(true, file.name, file.toUri(), "", "")
                dto.file = file
                fileDtoList.add(dto)
            }
        }

        return fileDtoList.toList()
    }

}
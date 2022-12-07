package com.example.securityapp.model.file.repository

import android.os.Environment
import androidx.core.net.toUri
import com.example.securityapp.model.file.data.FileDto
import java.io.File

object FilesRepositoryImpl : FilesRepository {
    override suspend fun getEncryptedFiles(): List<FileDto> {
        val path = "${Environment.getExternalStorageDirectory()}/encrypted_files"
        val directory = File(path)

        val fileDtoList = mutableListOf<FileDto>()
        for (file in directory.listFiles()) {
            if (file.extension == "encd") {
                fileDtoList.add(FileDto(true, file.name, file.toUri()).apply {
                    this.file = file
                })
            }
        }

        return fileDtoList.toList()
    }

}
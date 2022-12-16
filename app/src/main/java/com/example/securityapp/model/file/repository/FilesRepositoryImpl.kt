package com.example.securityapp.model.file.repository

import android.os.Build
import android.os.Environment
import androidx.core.net.toUri
import com.example.securityapp.model.file.data.FileDto
import java.io.File

object FilesRepositoryImpl : FilesRepository {
    override suspend fun getEncryptedFiles(): List<FileDto> {
        val path = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)}/encrypted_files"
        else
            "${Environment.getExternalStorageDirectory()}/encrypted_files"
        val directory = File(path)


        val fileFormat = "encd"
        val files = directory.listFiles()

        val fileDtoList = mutableListOf<FileDto>()
        for (file in files) {
            if (file.extension == fileFormat) {
                fileDtoList.add(FileDto(true, file.name, file.toUri()).apply {
                    this.file = file
                })
            }
        }

        return fileDtoList.toList()
    }

}
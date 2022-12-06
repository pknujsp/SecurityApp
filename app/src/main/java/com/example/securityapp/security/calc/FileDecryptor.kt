package com.example.securityapp.security.calc

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.net.toUri
import com.example.securityapp.model.file.data.FileDto
import java.io.*
import java.security.MessageDigest
import java.util.*
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class FileDecryptor {
    companion object {
        private val iv = arrayOf<Byte>(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16)

        suspend fun decryptFile(context: Context, fileDto: FileDto, password: String): Uri? {
            var decryptedFile: File? = null
            try {
                // 원본 파일의 확장자 분석
                val originalFileExtension = fileDto.name.run {
                    substring(lastIndexOf("_") + 1, lastIndexOf("."))
                }
                // 복호화 후 생성할 파일명
                var decryptedFileName = "decrypted_${fileDto.getRealName()}"
                decryptedFileName = decryptedFileName.run {
                    var txt = replace("encrypted_", "")
                    txt = txt.replace("_${originalFileExtension}", "")
                    txt.plus(".${originalFileExtension}")
                }

                // 저장할 디렉토리
                val saveDirectory = "${Environment.getExternalStorageDirectory()}/encrypted_files"
                val directory = File(saveDirectory)

                directory.apply {
                    if (!exists())
                        mkdirs()
                }

                // 파일 생성
                decryptedFile = File(directory, decryptedFileName)
                decryptedFile.apply {
                    if (exists())
                        delete()
                }
                var key: ByteArray? = ("t784$password").encodeToByteArray()

                // 비밀번호 키를 SHA-1으로 암호화
                val sha = MessageDigest.getInstance("SHA-1")
                key = sha.digest(key)
                key = Arrays.copyOf(key, 16)

                // AES로 암호화된 파일을 복호화
                val secretKeySpec = SecretKeySpec(key, "AES")
                val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, IvParameterSpec(iv.toByteArray()))

                // 복호화할 파일 스트림 연결
                context.contentResolver.openInputStream(fileDto.uri)?.use { fileInputStream ->
                    CipherInputStream(fileInputStream, cipher).use { cipherInputStream ->
                        val fileOutputStream = decryptedFile.outputStream()
                        var b = 0
                        var d = ByteArray(8)
                        b = cipherInputStream.read(d)

                        while (b != -1) {
                            fileOutputStream.write(d, 0, b)
                            b = cipherInputStream.read(d)
                        }
                        fileOutputStream.flush()
                        fileOutputStream.close()
                        cipherInputStream.close()
                    }
                }

                return decryptedFile.toUri()
            } catch (e: Exception) {
                decryptedFile?.apply {
                    if (exists())
                        delete()
                }
                return null
            }

        }
    }
}
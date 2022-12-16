package com.example.securityapp.security.calc

import android.content.Context
import android.os.Build
import android.os.Environment
import com.example.securityapp.model.file.data.FileDto
import java.io.*
import java.nio.file.Files.delete
import java.nio.file.Files.exists
import java.security.MessageDigest
import java.util.*
import javax.crypto.Cipher
import javax.crypto.CipherOutputStream
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


class FileEncryptor {
    companion object {
        private val iv = arrayOf<Byte>(0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16)

        suspend fun encryptFile(context: Context, fileDto: FileDto, password: String): Boolean {
            try {
                // 만들 파일명
                val encryptedFileName = "encrypted_${fileDto.getRealName()}_${fileDto.getExtension()}.encd"

                // 저장할 디렉토리
                val saveDirectory = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                    "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)}/encrypted_files"
                else
                    "${Environment.getExternalStorageDirectory()}/encrypted_files"

                val directory = File(saveDirectory)

                directory.apply {
                    if (!exists())
                        mkdirs()
                }

                // 파일 생성
                val encryptedFile = File(directory, encryptedFileName)
                encryptedFile.apply {
                    if (exists())
                        delete()
                }
                // 파일 암호화 키 생성
                var key: ByteArray = ("t784$password").encodeToByteArray()
                // 비밀번호 키를 SHA-1으로 암호화
                val sha = MessageDigest.getInstance("SHA-1")
                key = sha.digest(key)
                key = Arrays.copyOf(key, 16)

                // 파일을 AES로 암호화
                val secretKeySpec = SecretKeySpec(key, "AES")
                // 운영모드 CBC
                val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, IvParameterSpec(iv.toByteArray()))

                context.contentResolver.openInputStream(fileDto.uri)?.use { originalInputStream ->
                    BufferedReader(InputStreamReader(originalInputStream)).use { bufferedReader ->
                        val cipherOutputStream = CipherOutputStream(encryptedFile.outputStream(), cipher)
                        val bufferedOutputStream = BufferedWriter(OutputStreamWriter(cipherOutputStream))
                        var line = bufferedReader.readLine()

                        while (line != null) {
                            bufferedOutputStream.write(line)
                            line = bufferedReader.readLine()
                        }

                        bufferedReader.close()
                        bufferedOutputStream.flush()
                        bufferedOutputStream.close()

                        cipherOutputStream.flush()
                        cipherOutputStream.close()
                    }
                    originalInputStream.close()
                }

            } catch (e: Exception) {
                return false
            }

            return true
        }

    }
}
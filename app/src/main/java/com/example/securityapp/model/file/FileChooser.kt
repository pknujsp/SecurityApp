package com.example.securityapp.model.file

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class FileChooser(private val activityResultRegistry: ActivityResultRegistry) : DefaultLifecycleObserver {
    private var fileOnChooseListener: FileOnChooseListener? = null
    private val fileChooserResultListener =
        ActivityResultCallback<ActivityResult> { result ->
            var uri: Uri? = null
            result.data?.apply {
                uri = this.data
            }

            fileOnChooseListener?.onResult(uri)
        }

    private lateinit var fileChooserActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        fileChooserActivityResultLauncher = activityResultRegistry
            .register(
                "fileChooser", owner, ActivityResultContracts.StartActivityForResult(),
                fileChooserResultListener
            )

    }

    fun openFileChooser(fileOnChooseListener: FileOnChooseListener) {
        this.fileOnChooseListener = fileOnChooseListener
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).run {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("application/*", "text/*"))
            Intent.createChooser(this, "암호화할 파일 선택")
        }
        fileChooserActivityResultLauncher.launch(intent)
    }

    fun interface FileOnChooseListener {
        fun onResult(uri: Uri?)
    }
}
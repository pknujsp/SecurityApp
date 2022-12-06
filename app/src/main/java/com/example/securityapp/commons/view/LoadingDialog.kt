package com.example.securityapp.commons.view

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.example.securityapp.databinding.ViewLoadingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoadingDialog {
    companion object {
        private var showingDialog: AlertDialog? = null

        fun show(activity: Activity) {
            showingDialog?.run {
                dismiss()
                null
            }

            showingDialog =
                MaterialAlertDialogBuilder(activity)
                    .setView(
                        ViewLoadingBinding.inflate(activity.layoutInflater, null, false).root
                    ).create()
            showingDialog?.show()
        }

        fun dismiss() {
            showingDialog?.run {
                dismiss()
                null
            }
        }
    }
}
package com.example.securityapp.presentation.security.encryption

import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.securityapp.databinding.FragmentFileEncryptionBinding
import com.example.securityapp.model.file.FileChooser
import com.example.securityapp.viewmodel.security.FileEncryptionViewModel
import java.io.File


class FileEncryptionFragment : Fragment() {
    private var _binding: FragmentFileEncryptionBinding? = null

    private val fileEncryptionViewModel by viewModels<FileEncryptionViewModel>()
    private val binding get() = _binding!!
    private var fileChooser: FileChooser? = null

    companion object {
        const val TAG = "FileEncryptionFragment"
    }

    private val fileOnChooseListener = FileChooser.FileOnChooseListener { uri ->
        if (uri == null) {
            Toast.makeText(requireContext(), "선택한 파일이 없습니다", Toast.LENGTH_SHORT).show()
        } else {
            fileEncryptionViewModel.selectedFileForEncryption = uri
            setFileInfo()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fileChooser = FileChooser(requireActivity().activityResultRegistry).apply {
            lifecycle.addObserver(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFileEncryptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.encryptBtn.setOnClickListener {

        }

        binding.fileChooserBtn.setOnClickListener {
            fileChooser?.openFileChooser(fileOnChooseListener)
        }
    }

    private fun setFileInfo() {
        fileEncryptionViewModel.selectedFileForEncryption?.run {
            requireContext().contentResolver.query(this, null, null, null, null)
        }?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            binding.fileName.text = cursor.getString(nameIndex)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
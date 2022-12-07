package com.example.securityapp.presentation.security.encryption

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.securityapp.R
import com.example.securityapp.commons.view.LoadingDialog
import com.example.securityapp.databinding.FragmentFileEncryptionBinding
import com.example.securityapp.model.file.FileChooser
import com.example.securityapp.presentation.main.MainFragment
import com.example.securityapp.viewmodel.security.FileEncryptionViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


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
            fileEncryptionViewModel.loadFileInfo(requireContext().applicationContext)
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
            fileEncryptionViewModel.selectedFileForEncryption?.apply {
                MaterialAlertDialogBuilder(requireActivity())
                    .setTitle("파일 암호화")
                    .setMessage("${binding.fileName.text}을 암호화 하시겠습니까?")
                    .setPositiveButton("네") { dialog, _ ->
                        dialog.dismiss()
                        LoadingDialog.show(requireActivity())
                        fileEncryptionViewModel.encryptFile(requireContext().applicationContext, "123")
                    }
                    .setNegativeButton(R.string.cancel) { dialog, _ ->
                        dialog.dismiss()
                    }.create().show()
            }
        }

        binding.fileChooserBtn.setOnClickListener {
            fileChooser?.openFileChooser(fileOnChooseListener)
        }

        fileEncryptionViewModel.selectedFile.observe(viewLifecycleOwner) {
            binding.fileName.text = it.name
        }

        fileEncryptionViewModel.encryptedFile.observe(viewLifecycleOwner) {
            LoadingDialog.dismiss()
            Toast.makeText(requireContext().applicationContext, "암호화 성공", Toast.LENGTH_SHORT).show()

            parentFragmentManager.popBackStack()
            parentFragmentManager.beginTransaction().hide(parentFragmentManager.findFragmentByTag(MainFragment.TAG)!!).add(
                R.id.fragment_container_view, EncryptedFileListFragment(),
                EncryptedFileListFragment.TAG
            ).addToBackStack(EncryptedFileListFragment.TAG).commit()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
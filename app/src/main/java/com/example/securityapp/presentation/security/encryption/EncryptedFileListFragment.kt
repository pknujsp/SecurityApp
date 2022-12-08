package com.example.securityapp.presentation.security.encryption

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.securityapp.R
import com.example.securityapp.commons.interfaces.ListItemOnClickListener
import com.example.securityapp.commons.view.LoadingDialog
import com.example.securityapp.databinding.FragmentEncryptedFileListBinding
import com.example.securityapp.databinding.ViewDialogPasswordBinding
import com.example.securityapp.model.file.data.FileDto
import com.example.securityapp.presentation.security.decryption.FileDecryptionFragment
import com.example.securityapp.presentation.security.encryption.adapter.EncryptedFilesAdapter
import com.example.securityapp.viewmodel.files.EncryptedFilesViewModel
import com.example.securityapp.viewmodel.security.FileDecryptionViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class EncryptedFileListFragment : Fragment() {
    private var _binding: FragmentEncryptedFileListBinding? = null
    private val binding get() = _binding!!
    private val encryptedFilesViewModel by viewModels<EncryptedFilesViewModel>()
    private val fileDecryptionViewModel by viewModels<FileDecryptionViewModel>()

    companion object {
        const val TAG = "EncryptedFileListFragment"
    }

    private val fileOnClickListener = ListItemOnClickListener<FileDto> { file, _ ->

        ViewDialogPasswordBinding.inflate(layoutInflater).apply {
            val msg = "${file.name}을 복호화 하시겠습니까?"
            this.msg.text = msg

            MaterialAlertDialogBuilder(requireActivity())
                .setTitle("파일 복호화")
                .setView(root)
                .setPositiveButton("네") { dialog, _ ->
                    if (this.textInputPassword.text.isNullOrEmpty()) {
                        Toast.makeText(requireContext().applicationContext, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }
                    LoadingDialog.show(requireActivity())
                    Toast.makeText(requireContext(), "파일 복호화 중입니다", Toast.LENGTH_SHORT).show()

                    fileDecryptionViewModel.encryptedFile = file
                    fileDecryptionViewModel.decryptFile(requireContext().applicationContext, this.textInputPassword.text.toString())
                }
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                }.create().show()
        }
    }

    private val encryptedFilesAdapter = EncryptedFilesAdapter(fileOnClickListener)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEncryptedFileListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.fileList.adapter = encryptedFilesAdapter

        //파일 목록 가져오기
        encryptedFilesViewModel.encryptedFiles.observe(viewLifecycleOwner) { files ->
            encryptedFilesAdapter.files.clear()
            encryptedFilesAdapter.files.addAll(files)
            encryptedFilesAdapter.notifyDataSetChanged()
        }
        encryptedFilesViewModel.getEncryptedFiles()

        //복호화 성공한 파일
        fileDecryptionViewModel.decryptedFile.observe(viewLifecycleOwner) {
            LoadingDialog.dismiss()
            Toast.makeText(requireContext().applicationContext, "복호화 성공", Toast.LENGTH_SHORT).show()

            //복호화 프래그먼트로 이동
            parentFragmentManager.beginTransaction()
                .hide(this@EncryptedFileListFragment)
                .add(R.id.fragment_container_view, FileDecryptionFragment(), FileDecryptionFragment.TAG)
                .addToBackStack(FileDecryptionFragment.TAG)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
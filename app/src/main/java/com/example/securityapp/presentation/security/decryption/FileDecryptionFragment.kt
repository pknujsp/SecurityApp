package com.example.securityapp.presentation.security.decryption

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.securityapp.databinding.FragmentFileDecryptionBinding
import com.example.securityapp.presentation.security.encryption.EncryptedFileListFragment
import com.example.securityapp.viewmodel.security.FileDecryptionViewModel


class FileDecryptionFragment : Fragment() {
    private var _binding: FragmentFileDecryptionBinding? = null
    private val binding get() = _binding!!
    private val fileDecryptionViewModel by viewModels<FileDecryptionViewModel>({
        parentFragmentManager.findFragmentByTag(EncryptedFileListFragment.TAG)!!
    })

    companion object {
        const val TAG = "FileDecryptionFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFileDecryptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.showFileBtn.setOnClickListener {

        }

        fileDecryptionViewModel.decryptedFile.observe(viewLifecycleOwner) {
            // 복호화한 파일
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
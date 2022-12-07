package com.example.securityapp.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.securityapp.R
import com.example.securityapp.databinding.FragmentMainBinding
import com.example.securityapp.presentation.security.encryption.EncryptedFileListFragment
import com.example.securityapp.presentation.security.encryption.FileEncryptionFragment


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "MainFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.encryptFileBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().hide(this@MainFragment).add(
                R.id.fragment_container_view, FileEncryptionFragment(),
                FileEncryptionFragment.TAG
            ).addToBackStack(FileEncryptionFragment.TAG).commit()
        }
        binding.showEncryptedFileListBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().hide(this@MainFragment).add(
                R.id.fragment_container_view, EncryptedFileListFragment(),
                EncryptedFileListFragment.TAG
            )
                .addToBackStack(EncryptedFileListFragment.TAG).commit()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
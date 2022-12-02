package com.example.securityapp.presentation.main.host

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.securityapp.databinding.FragmentMainHostBinding
import com.example.securityapp.presentation.main.MainFragment


class MainHostFragment : Fragment() {
    private var _binding: FragmentMainHostBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val TAG = "MainHostFragment"
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (!childFragmentManager.popBackStackImmediate())
                requireActivity().finish()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainHostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.beginTransaction().add(
            binding.fragmentContainerView.id, MainFragment(), MainFragment.TAG
        ).commitNow()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
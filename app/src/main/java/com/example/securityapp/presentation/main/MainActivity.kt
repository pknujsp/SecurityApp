package com.example.securityapp.presentation.main

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import androidx.appcompat.app.AppCompatActivity
import com.example.securityapp.databinding.ActivityMainBinding
import com.example.securityapp.presentation.main.host.MainHostFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainerView.id, MainHostFragment(), MainHostFragment.TAG)
            .commitNow()

    }


}
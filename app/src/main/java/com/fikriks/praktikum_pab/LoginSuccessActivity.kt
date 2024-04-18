package com.fikriks.praktikum_pab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fikriks.praktikum_pab.databinding.ActivityLoginSuccessBinding

class LoginSuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginSuccessBinding.inflate(layoutInflater)
        val view = binding.root

        val intent = intent
        val username = intent.getStringExtra("USERNAME")
        val password = intent.getStringExtra("PASSWORD")

        binding.txtResultUsername.text = username
        binding.txtResultPassword.text = password

        setContentView(view)
    }
}
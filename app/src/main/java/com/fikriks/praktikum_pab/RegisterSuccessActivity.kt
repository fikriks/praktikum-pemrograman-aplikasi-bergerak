package com.fikriks.praktikum_pab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fikriks.praktikum_pab.databinding.ActivityRegisterSuccessBinding

class RegisterSuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterSuccessBinding.inflate(layoutInflater)
        val view = binding.root

        val intent = intent
        val username = intent.getStringExtra("USERNAME")
        val email = intent.getStringExtra("EMAIL")
        val password = intent.getStringExtra("PASSWORD")

        binding.txtResultUsername.text = username
        binding.txtResultEmail.text = email
        binding.txtResultPassword.text = password

        setContentView(view)
    }
}
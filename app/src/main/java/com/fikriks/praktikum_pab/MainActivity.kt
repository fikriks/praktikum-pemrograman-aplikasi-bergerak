package com.fikriks.praktikum_pab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.fikriks.praktikum_pab.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        binding.loginFooterButton.setOnClickListener{
            val loginFragment: Fragment = LoginFragment()
            loadFragment(loginFragment)
        }

        binding.registerFooterButton.setOnClickListener{
            val registerFragment: Fragment = RegisterFragment()
            loadFragment(registerFragment)
        }

        setContentView(view)
    }

    private fun loadFragment(fragment: Fragment) {
        val fm: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(binding.fragmentContainer.id, fragment)
        fragmentTransaction.commit()
    }
}
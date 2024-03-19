package com.fikriks.praktikum_pab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.fikriks.praktikum_pab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.hitung.setOnClickListener{
            val name = binding.nama.text.toString()
            binding.outputNama.text = name

            val gajiStatus: Int = if (binding.menikah.isChecked) {
                500000
            } else {
                0
            }

            val golongan = binding.rgGolongan
            val gol = golongan.checkedRadioButtonId
            val gajiGolongan: Int = when (gol) {
                R.id.rbGolongan1 -> 1000000
                R.id.rbGolongan2 -> 2000000
                else -> 0
            }

            val totalGaji = gajiStatus + gajiGolongan
            binding.outputGaji.text = totalGaji.toString()
            binding.outputStatus.text = if(binding.menikah.isChecked) { "Sudah Menikah" } else { "Belum Menikah" }
            binding.outputGolongan.text = if(binding.rbGolongan1.isChecked) { "Golongan 1" } else { "Golongan 2" }
            binding.outputHitung.visibility = View.VISIBLE
        }
    }
}
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

        binding.btnTambah.setOnClickListener{
            val angka1 = binding.editText1.text.toString().toInt()
            val angka2 = binding.editText2.text.toString().toInt()
            val hasil = angka1 + angka2
            binding.hasil.visibility = View.VISIBLE
            binding.hasil.text = "Hasil Penjumlahan : $hasil"
        }

        binding.btnBagi.setOnClickListener{
            val angka1 = binding.editText1.text.toString().toInt()
            val angka2 = binding.editText2.text.toString().toInt()
            val hasil = angka1 / angka2
            binding.hasil.visibility = View.VISIBLE
            binding.hasil.text = "Hasil Pembagian : $hasil"
        }

        binding.btnKali.setOnClickListener{
            val angka1 = binding.editText1.text.toString().toInt()
            val angka2 = binding.editText2.text.toString().toInt()
            val hasil = angka1 * angka2
            binding.hasil.visibility = View.VISIBLE
            binding.hasil.text = "Hasil Perkalian : $hasil"
        }

        binding.btnKurang.setOnClickListener{
            val angka1 = binding.editText1.text.toString().toInt()
            val angka2 = binding.editText2.text.toString().toInt()
            val hasil = angka1 - angka2
            binding.hasil.visibility = View.VISIBLE
            binding.hasil.text = "Hasil Pengurangan : $hasil"
        }
    }
}
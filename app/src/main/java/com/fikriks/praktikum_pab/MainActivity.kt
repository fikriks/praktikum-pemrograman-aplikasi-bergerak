package com.fikriks.praktikum_pab

import android.R
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.fikriks.praktikum_pab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TextWatcher {
    private lateinit var binding: ActivityMainBinding
    private val lifeCycle = "androidlifecycle"

    lateinit var ac: AutoCompleteTextView
    val stringdatafak = arrayOf("Fakultas Ekonomi", "Fakultas Kehutanan", "Fakultas Ilmu Komputer", "Fakultas Hukum", "Fakultas Keguruan dan Ilmu Pendidikan")
    lateinit var lv: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        binding.dataautocomplete.addTextChangedListener(this)
        binding.dataautocomplete.setAdapter(ArrayAdapter(this, R.layout.simple_dropdown_item_1line, stringdatafak))
        setContentView(view)
    }

    fun pilihfakultas(v: View?) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Data Fakultas Universitas Kuningan")
        builder.setItems(stringdatafak, DialogInterface.OnClickListener { dialog, item ->
            binding.datalistview.setText(stringdatafak[item])
            dialog.dismiss()
        }).show()
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun afterTextChanged(s: Editable?) {}

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "Posisi lagi start nih  nih", LENGTH_LONG).show()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "Posisi lagi stop  nih", LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "Posisi lagi Resume  nih", LENGTH_LONG).show()
        binding.txt1.text = "POSISI LAGI RESUME"
        Log.i(lifeCycle, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "Posisi lagi pause  nih", LENGTH_LONG).show()
        val linearLayout = binding.linearParent
        Log.i(lifeCycle, "onPause() called");
        // Ubah warna latar belakang LinearLayout
        linearLayout.setBackgroundColor(resources.getColor(R.color.holo_purple))

        binding.txt1.setText("POSISI LAGI PAUSE");
        binding.txt1.setTextColor(getColor(R.color.black));
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Posisi udah hancur  nih", LENGTH_LONG).show()
    }
}
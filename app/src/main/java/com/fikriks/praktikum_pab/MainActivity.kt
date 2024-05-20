package com.fikriks.praktikum_pab

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fikriks.praktikum_pab.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private lateinit var tvLight: TextView
    private lateinit var cameraManager: CameraManager
    private var cameraId: String? = null
    private lateinit var flashRunnable: Runnable
    private var flashOn: Boolean = false
    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        tvLight = binding.tvTemperature
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        binding.relativeLayout.setBackgroundColor(Color.BLACK);
        tvLight.text = "Lampu Menyala"

        try {
            cameraId = cameraManager.cameraIdList[0]
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (lightSensor == null) {
            Toast.makeText(this, "Sensor cahaya tidak tersedia", Toast.LENGTH_SHORT).show()
        }

        setContentView(view)
    }

    override fun onResume() {
        super.onResume()
        lightSensor?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // We do not need to handle accuracy changes for this example
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            var light = event.values[0]
            if(light.toInt() == 0){
                binding.relativeLayout.setBackgroundColor(Color.WHITE);
                tvLight.text = "Lampu Sekitar Mati"
                startFlash()
            } else {
                binding.relativeLayout.setBackgroundColor(Color.BLACK);
                tvLight.text = "Lampu Sekitar Menyala"
                stopFlash()
            }
        }
    }

    private fun startFlash() {
        try {
            // Nyalakan lampu kilat
            cameraManager.setTorchMode(cameraId!!, true)
        } catch (e: CameraAccessException) {
            // Tangani jika terjadi akses kamera gagal
            e.printStackTrace()
        }
    }

    private fun stopFlash() {
        try {
            // Matikan lampu kilat
            cameraManager.setTorchMode(cameraId!!, false)
        } catch (e: CameraAccessException) {
            // Tangani jika terjadi akses kamera gagal
            e.printStackTrace()
        }
    }
}
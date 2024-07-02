package com.example.absen.presentation.screen

import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import android.Manifest
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Scaffold
import com.example.absen.ViewModel.AbsenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbsenScreen(activity: ComponentActivity, absenViewModel: AbsenViewModel = viewModel()) {
    val context = activity
    var isAbsenSuccess by remember { mutableStateOf(false) }
    var absenData by remember { mutableStateOf(listOf<Map<String, Any>>()) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isLoading = true
        absenViewModel.checkIfAbsenToday { hasAbsenToday ->
            isAbsenSuccess = hasAbsenToday
            if (hasAbsenToday) {
                absenViewModel.fetchAbsenData(onSuccess = { data ->
                    absenData = data
                    isLoading = false
                }, onFailure = { e ->
                    isLoading = false
                    Toast.makeText(context, "Failed to fetch data: ${e.message}", Toast.LENGTH_SHORT).show()
                })
            } else {
                isLoading = false
            }
        }
    }

    val barCodeLauncher = rememberLauncherForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
        } else {
            absenViewModel.uploadScanResult(result.contents, onSuccess = {
                Toast.makeText(context, "Absen berhasil", Toast.LENGTH_SHORT).show()
                isAbsenSuccess = true
                absenViewModel.fetchAbsenData(onSuccess = { data ->
                    absenData = data
                }, onFailure = { e ->
                    Toast.makeText(context, "Failed to fetch data: ${e.message}", Toast.LENGTH_SHORT).show()
                })
            }, onFailure = { e ->
                Toast.makeText(context, "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
            })
        }
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            showCamera(barCodeLauncher)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Absensi") }
            )
        },
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    if (!isAbsenSuccess) {
                        Text(
                            text = "Silahkan klik tombol scan",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Button(onClick = { checkCameraPermission(context, requestPermissionLauncher, barCodeLauncher) }) {
                            Text(text = "Scan")
                        }
                    }

                    if (isAbsenSuccess) {
                        Text(
                            text = "Riwayat Absensi",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .align(Alignment.Start)
                        )
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 16.dp)
                        ) {
                            items(absenData) { entry ->
                                AbsenEntryItem(entry)
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun AbsenEntryItem(entry: Map<String, Any>) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Pukul: ${entry["jam"]}")
        Text(text = "Tanggal: ${entry["created_at"]}")
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

private fun showCamera(barCodeLauncher: androidx.activity.result.ActivityResultLauncher<ScanOptions>) {
    val options = ScanOptions()
    options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
    options.setPrompt("Scan a QR code")
    options.setCameraId(0)
    options.setBeepEnabled(false)
    options.setOrientationLocked(false)

    barCodeLauncher.launch(options)
}

private fun checkCameraPermission(
    context: Context,
    requestPermissionLauncher: androidx.activity.result.ActivityResultLauncher<String>,
    barCodeLauncher: androidx.activity.result.ActivityResultLauncher<ScanOptions>
) {
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        showCamera(barCodeLauncher)
    } else if ((context as ComponentActivity).shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
        Toast.makeText(context, "Camera Required", Toast.LENGTH_SHORT).show()
    } else {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }
}

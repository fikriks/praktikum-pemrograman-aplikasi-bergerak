package com.example.absen.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.absen.ViewModel.JurnalViewModel
import com.example.absen.model.JurnalEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JurnalScreen(viewModel: JurnalViewModel = viewModel()) {
    var description by remember { mutableStateOf("") }
    val context = LocalContext.current
    var isSubmitted by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.checkJurnalForToday { submittedToday ->
            isSubmitted = submittedToday
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Jurnal") }
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
                    Text(
                        text = "Deskripsikan apa yang kamu pelajari hari ini",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        label = { Text("Deskripsi") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                // Handle submission if needed
                            }
                        ),
                        maxLines = 5,
                        enabled = !isSubmitted // Disable when submitted
                    )

                    Button(
                        onClick = {
                            viewModel.submitJurnalEntry(
                                description = description,
                                onSuccess = {
                                    Toast.makeText(context, "Data berhasil terkirim", Toast.LENGTH_SHORT).show()
                                    description = ""
                                    isSubmitted = true // Disable form and button after submission
                                },
                                onFailure = { error ->
                                    Toast.makeText(context, error.message ?: "Gagal mengirim data", Toast.LENGTH_SHORT).show()
                                }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        enabled = !isSubmitted // Disable when submitted
                    ) {
                        Text("Submit")
                    }

                    // Display Jurnal Entries
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 16.dp)
                    ) {
                        items(viewModel.jurnalEntries) { entry ->
                            JurnalEntryItem(entry)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun JurnalEntryItem(entry: JurnalEntry) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = entry.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = "Ditambahkan Pada Tanggal: ${entry.created_at}",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun JurnalScreenPreview() {
    JurnalScreen()
}

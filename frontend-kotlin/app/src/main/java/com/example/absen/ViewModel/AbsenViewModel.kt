package com.example.testt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AbsenViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun uploadScanResult(result: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val user = auth.currentUser

        firestore.collection("absen-masuk")
            .whereEqualTo("user_id", user?.uid)
            .whereEqualTo("created_at", getCurrentFormattedDate())
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    firestore.collection("barcode-absen-masuk")
                        .whereEqualTo("kode", result)
                        .get()
                        .addOnSuccessListener { result ->
                            if (result.isEmpty) {
                                onFailure(Exception("Qode QR tidak valid"))
                            } else {
                                viewModelScope.launch {
                                    val absenData = hashMapOf(
                                        "user_id" to user?.uid,
                                        "jam" to getCurrentFormattedClock(),
                                        "created_at" to getCurrentFormattedDate(),
                                        "created_at_timestamp" to Timestamp.now()
                                    )

                                    firestore.collection("absen-masuk")
                                        .add(absenData)
                                        .addOnSuccessListener {
                                            onSuccess()
                                        }
                                        .addOnFailureListener { e ->
                                            onFailure(e)
                                        }
                                }
                            }
                        }
                } else {
                    onFailure(Exception("Anda telah absensi hari ini"))
                }
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }

    fun fetchAbsenData(
        onSuccess: (List<Map<String, Any>>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val user = auth.currentUser
        if (user != null) {
            firestore.collection("absen-masuk")
                .whereEqualTo("user_id", user.uid)
                .get()
                .addOnSuccessListener { documents ->
                    val absenList = mutableListOf<Map<String, Any>>()
                    for (document in documents) {
                        absenList.add(document.data)
                    }
                    onSuccess(absenList)
                }
                .addOnFailureListener { e ->
                    onFailure(e)
                }
        } else {
            onFailure(Exception("User not logged in"))
        }
    }

    fun checkIfAbsenToday(
        onResult: (Boolean) -> Unit
    ) {
        val user = auth.currentUser
        if (user != null) {
            val currentDate = getCurrentFormattedDate()
            firestore.collection("absen-masuk")
                .whereEqualTo("user_id", user.uid)
                .whereEqualTo("created_at", currentDate)
                .get()
                .addOnSuccessListener { documents ->
                    onResult(documents.isEmpty.not())
                }
                .addOnFailureListener {
                    onResult(false)
                }
        } else {
            onResult(false)
        }
    }

    private fun getCurrentFormattedDate(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private fun getCurrentFormattedClock(): String {
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }
}

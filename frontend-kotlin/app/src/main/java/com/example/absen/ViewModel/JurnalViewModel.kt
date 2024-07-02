package com.example.absen.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.absen.model.JurnalEntry
import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import java.text.SimpleDateFormat
import java.util.Locale

class JurnalViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    val jurnalEntries = mutableStateListOf<JurnalEntry>()

    init {
        fetchJurnalEntries()
    }

    fun checkJurnalForToday(onResult: (Boolean) -> Unit) {
        val user = auth.currentUser
        if (user != null) {
            val currentDate = getCurrentFormattedDate()
            firestore.collection("jurnal")
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

    fun submitJurnalEntry(description: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val user = auth.currentUser
        if (user != null) {
            val currentDate = getCurrentFormattedDate()
            firestore.collection("jurnal")
                .whereEqualTo("user_id", user.uid)
                .whereEqualTo("created_at", currentDate)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        val entry = JurnalEntry(description = description, user_id = user.uid, created_at = currentDate, created_at_timestamp = Timestamp.now())
                        firestore.collection("jurnal")
                            .add(entry)
                            .addOnSuccessListener {
                                onSuccess()
                                fetchJurnalEntries() // Fetch entries again to include the new one
                            }
                            .addOnFailureListener {
                                onFailure(it)
                            }
                    } else {
                        onFailure(Exception("Anda telah menambahkan jurnal hari ini"))
                    }
                }
                .addOnFailureListener {
                    onFailure(it)
                }
        } else {
            onFailure(Exception("User not logged in"))
        }
    }

    private fun fetchJurnalEntries() {
        val user = auth.currentUser
        if (user != null) {
            firestore.collection("jurnal")
                .whereEqualTo("user_id", user.uid)
                .get()
                .addOnSuccessListener { result ->
                    jurnalEntries.clear()
                    for (document in result) {
                        val entry = document.toObject(JurnalEntry::class.java)
                        jurnalEntries.add(entry)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("Exception :", exception.message.toString())
                }
        }
    }

    private fun getCurrentFormattedDate(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }
}
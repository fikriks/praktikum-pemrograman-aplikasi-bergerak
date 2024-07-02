package com.example.absen.ViewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthViewModel : ViewModel() {

    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Fungsi untuk melakukan autentikasi dengan email dan password
    fun signInWithEmailAndPassword(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess.invoke() // Panggil onSuccess jika autentikasi berhasil
                } else {
                    onFailure.invoke("Authentication failed: ${task.exception?.message}") // Panggil onFailure jika autentikasi gagal
                }
            }
    }

    // Fungsi untuk mengambil data pengguna dari Firestore
    fun fetchUserInfo(onSuccess: (Map<String, Any>?) -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("users")
            .whereEqualTo("user_id", auth.currentUser?.uid)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val document = documents.documents[0]
                    onSuccess(document.data)
                } else {
                    onSuccess(null)
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    // Fungsi untuk logout
    fun signOut() {
        auth.signOut()
    }
}

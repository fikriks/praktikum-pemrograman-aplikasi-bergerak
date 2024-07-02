package com.example.absen.model

import com.google.firebase.Timestamp

data class JurnalEntry(
    val description: String = "",
    val user_id: String = "",
    val created_at: String = "",
    val created_at_timestamp: Timestamp = Timestamp.now()
)

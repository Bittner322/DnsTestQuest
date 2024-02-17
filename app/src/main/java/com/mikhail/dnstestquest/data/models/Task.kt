package com.mikhail.dnstestquest.data.models

import com.google.firebase.Timestamp

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val status: TaskStatus,
    val created: Timestamp
)

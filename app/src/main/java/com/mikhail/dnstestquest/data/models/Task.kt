package com.mikhail.dnstestquest.data.models

import java.time.LocalDateTime

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val status: TaskStatus,
    val created: LocalDateTime
)

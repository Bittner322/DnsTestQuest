package com.mikhail.dnstestquest.data.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Task(
    @DocumentId
    val id: String = "",
    val title: String,
    val description: String,
    val status: TaskStatus,
    val created: Timestamp
) {
    companion object {
        val default = Task(
            id = "",
            title = "",
            description = "",
            status = TaskStatus.NEW,
            created = Timestamp.now()
        )
    }
}

package com.mikhail.dnstestquest.data.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Task(
    @DocumentId
    val id: String = "",
    val title: String,
    val description: String,
    val statusState: MutableState<TaskStatus>,
    val created: Timestamp
) {
    companion object {
        val default = Task(
            id = "",
            title = "",
            description = "",
            statusState = mutableStateOf(TaskStatus.NEW),
            created = Timestamp.now()
        )
    }
}

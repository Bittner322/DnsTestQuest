package com.mikhail.dnstestquest.presentation.ui.screens.create_task

data class CreateTaskScreenUiState(
    val taskTitle: String,
    val taskDescription: String,
    val isError: Boolean
) {
    companion object {
        val default = CreateTaskScreenUiState(
            taskTitle = "",
            taskDescription = "",
            isError = true
        )
    }
}

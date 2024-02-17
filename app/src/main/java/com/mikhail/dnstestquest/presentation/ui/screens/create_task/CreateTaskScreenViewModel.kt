package com.mikhail.dnstestquest.presentation.ui.screens.create_task

import androidx.lifecycle.ViewModel
import com.mikhail.dnstestquest.data.repositories.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CreateTaskScreenViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
): ViewModel() {
    private val _taskFlow = MutableStateFlow(CreateTaskScreenUiState.default)
    val taskFlow = _taskFlow.asStateFlow()

    fun onTaskTitleChange(title: String) {
        _taskFlow.update {
            it.copy(taskTitle = title)
        }
        if (_taskFlow.value.taskTitle.isNotEmpty() &&
            _taskFlow.value.taskDescription.isNotEmpty()) {
            _taskFlow.update {
                it.copy(isError = false)
            }
        } else {
            _taskFlow.update {
                it.copy(isError = true)
            }
        }
    }

    fun onTaskDescriptionChange(description: String) {
        _taskFlow.update {
            it.copy(taskDescription = description)
        }
        if (_taskFlow.value.taskTitle.isNotEmpty() &&
            _taskFlow.value.taskDescription.isNotEmpty()) {
            _taskFlow.update {
                it.copy(isError = false)
            }
        } else {
            _taskFlow.update {
                it.copy(isError = true)
            }
        }
    }

    fun onAddTask() {

    }
}
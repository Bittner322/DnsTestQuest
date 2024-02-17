package com.mikhail.dnstestquest.presentation.ui.screens.create_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.mikhail.dnstestquest.data.models.Task
import com.mikhail.dnstestquest.data.models.TaskStatus
import com.mikhail.dnstestquest.data.repositories.FirestoreRepository
import com.mikhail.dnstestquest.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CreateTaskScreenViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val userRepository: UserRepository
): ViewModel() {
    private val _uiAction = Channel<CreateTaskScreenAction>()
    val uiAction = _uiAction.receiveAsFlow()

    private val _uiState = MutableStateFlow(CreateTaskScreenUiState.default)
    val uiState = _uiState.asStateFlow()

    fun onTaskTitleChange(title: String) {
        _uiState.update {
            it.copy(taskTitle = title)
        }
        if (_uiState.value.taskTitle.isNotEmpty() &&
            _uiState.value.taskDescription.isNotEmpty()) {
            _uiState.update {
                it.copy(isError = false)
            }
        } else {
            _uiState.update {
                it.copy(isError = true)
            }
        }
    }

    fun onTaskDescriptionChange(description: String) {
        _uiState.update {
            it.copy(taskDescription = description)
        }
        if (_uiState.value.taskTitle.isNotEmpty() &&
            _uiState.value.taskDescription.isNotEmpty()) {
            _uiState.update {
                it.copy(isError = false)
            }
        } else {
            _uiState.update {
                it.copy(isError = true)
            }
        }
    }

    fun onAddTask(
        taskTitle: String,
        taskDescription: String,
    ) {
        viewModelScope.launch {
            firestoreRepository.addUserTask(
                userId = userRepository.getUserId(),
                task = Task(
                    title = taskTitle,
                    description = taskDescription,
                    status = TaskStatus.NEW,
                    created = Timestamp(Date.from(Instant.now()))
                )
            )
            _uiAction.send(CreateTaskScreenAction.NavToHome)
        }
    }
}

sealed class CreateTaskScreenAction {
    data object NavToHome: CreateTaskScreenAction()
}
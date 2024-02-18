package com.mikhail.dnstestquest.presentation.ui.screens.task_detalisation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikhail.dnstestquest.data.models.Task
import com.mikhail.dnstestquest.data.repositories.FirestoreRepository
import com.mikhail.dnstestquest.data.repositories.UserRepository
import com.mikhail.dnstestquest.di.annotations.TaskId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetalizationScreenViewModel @Inject constructor(
    @TaskId
    private val taskId: String,
    private val firestoreRepository: FirestoreRepository,
    private val userRepository: UserRepository
): ViewModel() {
    private val _uiActions = Channel<TaskDetalizationScreenActions>()
    val uiActions = _uiActions.receiveAsFlow()

    private val _taskFlow = MutableStateFlow(Task.default)
    val taskFlow = _taskFlow.asStateFlow()

    fun onBackClick() {
        _uiActions.trySend(TaskDetalizationScreenActions.NavToHomeScreen)
    }

    init {
        getTaskById()
    }

    private fun getTaskById() {
        viewModelScope.launch {
            _taskFlow.value = firestoreRepository.getUserTaskById(
                userId = userRepository.getUserId(),
                taskId = taskId
            )
        }
    }
}

sealed class TaskDetalizationScreenActions {
    data object NavToHomeScreen: TaskDetalizationScreenActions()
    data object ShowGettingTaskFailureMessage: TaskDetalizationScreenActions()
}
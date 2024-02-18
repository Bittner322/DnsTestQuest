package com.mikhail.dnstestquest.presentation.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikhail.dnstestquest.data.models.Task
import com.mikhail.dnstestquest.data.models.TaskStatus
import com.mikhail.dnstestquest.data.repositories.FirestoreRepository
import com.mikhail.dnstestquest.data.repositories.RemoveTaskResult
import com.mikhail.dnstestquest.data.repositories.UpdateTaskResult
import com.mikhail.dnstestquest.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val userRepository: UserRepository
): ViewModel() {
    private val _uiAction = Channel<HomeScreenAction>()
    val uiAction = _uiAction.receiveAsFlow()

    private val _tasksFlow = MutableStateFlow<MutableList<Task>>(mutableListOf())
    val tasksFlow = _tasksFlow.asStateFlow()

    fun onScreenComposed() {
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch {
            _tasksFlow.value = firestoreRepository.getUserTasks(
                userId = userRepository.getUserId()
            ).toMutableList()
        }
    }

    fun onRemoveTaskClick(task: Task) {
        viewModelScope.launch {
            val removeResult = firestoreRepository.removeUserTask(
                userId = userRepository.getUserId(),
                taskId = task.id
            )

            when (removeResult) {
                RemoveTaskResult.RemoveFailure -> {
                    _uiAction.trySend(HomeScreenAction.ShowRemovingFailureMessage)
                }
                RemoveTaskResult.RemoveSuccess -> {
                    _tasksFlow.update {
                        (it - task).toMutableList()
                    }
                }
            }
        }
    }

    fun onAddTaskClick() {
        _uiAction.trySend(HomeScreenAction.NavToCreateTaskScreen)
    }

    fun onTaskStatusChangeClick(
        task: Task,
        taskStatus: TaskStatus,
        index: Int
    ) {
        viewModelScope.launch {
            val taskStatusChangeResult = firestoreRepository.updateUserTaskStatus(
                userId = userRepository.getUserId(),
                taskId = task.id,
                newStatus = taskStatus
            )

            when (taskStatusChangeResult) {
                UpdateTaskResult.UpdateFailure -> {
                    _uiAction.trySend(HomeScreenAction.ShowTaskUpdatingFailureMessage)
                }
                UpdateTaskResult.UpdateSuccess -> {
                    _tasksFlow.value[index] = _tasksFlow.value[index].copy(status = taskStatus)
                }
            }
        }
    }

    fun onTaskClick(task: Task) {
        _uiAction.trySend(HomeScreenAction.NavToDetalizationScreen(taskId = task.id))
    }
}

sealed class HomeScreenAction {
    data object NavToCreateTaskScreen: HomeScreenAction()
    data object NavToLoginScreen: HomeScreenAction()
    data class NavToDetalizationScreen(val taskId: String): HomeScreenAction()
    data object ShowRemovingFailureMessage: HomeScreenAction()
    data object ShowTaskUpdatingFailureMessage: HomeScreenAction()
}
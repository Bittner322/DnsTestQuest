package com.mikhail.dnstestquest.presentation.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.mikhail.dnstestquest.data.models.Task
import com.mikhail.dnstestquest.data.models.TaskStatus
import com.mikhail.dnstestquest.data.repositories.AddTaskResult
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
class HomeScreenViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val userRepository: UserRepository
): ViewModel() {
    private val _uiAction = Channel<HomeScreenAction>()
    val uiAction = _uiAction.receiveAsFlow()

    private val _tasksFlow = MutableStateFlow(emptyList<Task>())
    val tasksFlow = _tasksFlow.asStateFlow()

    private val _tasksCountFlow = MutableStateFlow(0)
    val tasksCountFlow = _tasksCountFlow.asStateFlow()

    init {
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch {
            _tasksFlow.value = firestoreRepository.getUserTasks(
                userId = userRepository.getUserId()
            )
        }
    }

    fun onAddTaskClick() {
        val newTask = Task(
            id = "1",
            title = "blablaTITLE",
            description = "blablaDESCRIPTION",
            status = TaskStatus.NEW,
            created = Timestamp(Date.from(Instant.now()))
        )
        //_uiAction.trySend(HomeScreenAction.NavToCreateTaskScreen)
        viewModelScope.launch {
            val result = firestoreRepository.addUserTask(
                userId = userRepository.getUserId(),
                task = Task(
                    id = "1",
                    title = "blablaTITLE",
                    description = "blablaDESCRIPTION",
                    status = TaskStatus.NEW,
                    created = Timestamp(Date.from(Instant.now()))
                )
            )

            when (result) {
                AddTaskResult.AddFailure -> TODO()
                is AddTaskResult.AddSuccess -> {
                    _tasksFlow.update {
                        it + Task(
                            id = result.taskId,
                            title = newTask.title,
                            description = newTask.description,
                            status = newTask.status,
                            created = newTask.created
                        )
                    }
                }
            }
        }
    }
}

sealed class HomeScreenAction {
    data object NavToCreateTaskScreen: HomeScreenAction()
    data object NavToLoginScreen: HomeScreenAction()
}
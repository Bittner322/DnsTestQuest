package com.mikhail.dnstestquest.presentation.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikhail.dnstestquest.data.models.Task
import com.mikhail.dnstestquest.data.repositories.FirestoreRepository
import com.mikhail.dnstestquest.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
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

    fun onScreenComposed() {
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
        _uiAction.trySend(HomeScreenAction.NavToCreateTaskScreen)
    }
}

sealed class HomeScreenAction {
    data object NavToCreateTaskScreen: HomeScreenAction()
    data object NavToLoginScreen: HomeScreenAction()
}
package com.mikhail.dnstestquest.presentation.ui.screens.sign_in

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInScreenViewModel @Inject constructor(): ViewModel() {
    private val _uiAction = Channel<SignInAction>()
    val uiAction = _uiAction.receiveAsFlow()

    private val _uiState = MutableStateFlow(SignInScreenUiState.default)
    val uiState = _uiState.asStateFlow()

    fun onNameChange(login: String) {
        _uiState.update {
            it.copy(login = login)
        }
        if (_uiState.value.login.isNotEmpty() &&
            _uiState.value.password.isNotEmpty()) {
            _uiState.update {
                it.copy(isError = false)
            }
        } else {
            _uiState.update {
                it.copy(isError = true)
            }
        }
    }

    fun onSurnameChange(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
        if (_uiState.value.login.isNotEmpty() &&
            _uiState.value.password.isNotEmpty()) {
            _uiState.update {
                it.copy(isError = false)
            }
        } else {
            _uiState.update {
                it.copy(isError = true)
            }
        }
    }

    fun onSignInClick() {
        // TODO
    }
}

sealed class SignInAction {
    data object NavToMainScreen: SignInAction()
}
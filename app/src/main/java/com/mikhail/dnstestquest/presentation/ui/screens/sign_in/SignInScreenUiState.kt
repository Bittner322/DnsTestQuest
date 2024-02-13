package com.mikhail.dnstestquest.presentation.ui.screens.sign_in

data class SignInScreenUiState(
    val login: String,
    val password: String,
    val isError: Boolean
) {
    companion object {
        val default = SignInScreenUiState(
            login = "",
            password = "",
            isError = true
        )
    }
}
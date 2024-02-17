package com.mikhail.dnstestquest.presentation.ui.main_activity

import androidx.lifecycle.ViewModel
import com.mikhail.dnstestquest.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    fun checkIsUserLogged(): Boolean {
        return userRepository.isUserLogged()
    }
}
package com.mikhail.dnstestquest.presentation.ui.screens.home

import androidx.lifecycle.ViewModel
import com.mikhail.dnstestquest.data.repositories.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    firestoreRepository: FirestoreRepository
): ViewModel() {

}
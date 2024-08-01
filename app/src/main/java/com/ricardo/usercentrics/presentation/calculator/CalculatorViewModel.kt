package com.ricardo.usercentrics.presentation.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usercentrics.sdk.Usercentrics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor() : ViewModel() {

    private val viewModelState = MutableStateFlow<CalculatorUiState>(CalculatorUiState.Initializing)

    val uiState = viewModelState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value
        )

    init {
        initUsercentrics()
    }

    fun initUsercentrics() {
        viewModelState.update { CalculatorUiState.Initializing }
        Usercentrics.isReady(
            onSuccess = {
                viewModelState.update { CalculatorUiState.Idle("0") }
            },
            onFailure = {
                viewModelState.update { CalculatorUiState.Error }
            }
        )
    }
}
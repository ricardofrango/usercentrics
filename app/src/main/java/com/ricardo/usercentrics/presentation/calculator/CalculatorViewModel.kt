package com.ricardo.usercentrics.presentation.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricardo.usercentrics.domain.CalculateTotalCostsUseCase
import com.ricardo.usercentrics.domain.ServicesCostsUseCase
import com.ricardo.usercentrics.domain.UsercentricsSdkReadyUseCase
import com.usercentrics.sdk.UsercentricsConsentUserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val usercentricsSdkReadyUseCase: UsercentricsSdkReadyUseCase,
    private val servicesCostsUseCase: ServicesCostsUseCase,
    private val calculateTotalCostsUseCase: CalculateTotalCostsUseCase
) : ViewModel() {

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

    private fun initUsercentrics() {
        viewModelScope.launch {
            viewModelState.update { CalculatorUiState.Initializing }

            val isUsercentricsSdkReady = usercentricsSdkReadyUseCase()

            if (isUsercentricsSdkReady) {
                viewModelState.update { CalculatorUiState.Idle() }
            } else {
                viewModelState.update { CalculatorUiState.Error }
            }
        }
    }

    fun calculate(userResponse: UsercentricsConsentUserResponse?) {
        userResponse?.let {
            val services = servicesCostsUseCase(userResponse)
            val totalCost = calculateTotalCostsUseCase(services)

            viewModelState.update { CalculatorUiState.Idle(services, totalCost) }

        } ?: viewModelState.update { CalculatorUiState.Error }
    }
}
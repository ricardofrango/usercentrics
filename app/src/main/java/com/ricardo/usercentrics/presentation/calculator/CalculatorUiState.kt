package com.ricardo.usercentrics.presentation.calculator

import com.ricardo.usercentrics.domain.model.Service

sealed class CalculatorUiState {

    data object Initializing: CalculatorUiState()
    data object Calculating: CalculatorUiState()
    data class Idle(val services: List<Service> = listOf(), val totalCost: Int = 0): CalculatorUiState()
    data object Error: CalculatorUiState()
}
package com.ricardo.usercentrics.presentation.calculator

sealed class CalculatorUiState {

    data object Initializing: CalculatorUiState()
    data class Idle(val count: String): CalculatorUiState()
    data object Error: CalculatorUiState()
}
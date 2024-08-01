package com.ricardo.usercentrics.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ricardo.usercentrics.ui.theme.UsercentricsTheme

const val calculatorRoute = "calculator"

@Preview
@Composable
fun CalculatorScreenPreview() {
    UsercentricsTheme {
        CalculatorScreen()
    }
}

@Composable
fun CalculatorScreen() {

}

fun NavGraphBuilder.calculatorScreen() = composable(
    route = calculatorRoute,
) {
    CalculatorScreen()
}

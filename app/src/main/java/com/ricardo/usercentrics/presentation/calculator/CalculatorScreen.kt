package com.ricardo.usercentrics.presentation.calculator

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ricardo.usercentrics.R
import com.ricardo.usercentrics.ui.theme.UsercentricsTheme

const val calculatorRoute = "calculator"

@Preview
@Composable
private fun CalculatorScreenPreview(
    @PreviewParameter(CalculatorUiStateProvider::class) uiState: CalculatorUiState
) {
    UsercentricsTheme {
        RealCalculatorScreen(
            uiState = uiState,
            onShowConsentClick = {}
        )
    }
}

@Composable
private fun CalculatorScreen(navigate: (String) -> Unit) {
    val viewModel = hiltViewModel<CalculatorViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    val onShowConsentClick = {
        navigate("")
    }

    RealCalculatorScreen(
        uiState = uiState,
        onShowConsentClick = onShowConsentClick
    )
}

@Composable
private fun RealCalculatorScreen(
    uiState: CalculatorUiState,
    onShowConsentClick: () -> Unit,
) {
    when (uiState) {
        CalculatorUiState.Initializing -> CalculatorLoading()
        is CalculatorUiState.Idle -> CalculatorReady(
            count = uiState.count,
            onShowConsentClick = onShowConsentClick,
        )

        CalculatorUiState.Error -> CalculatorError()
    }
}

@Composable
private fun CalculatorError(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(id = R.string.error_initiating_sdk))
    }
}

@Composable
private fun CalculatorReady(
    modifier: Modifier = Modifier,
    count: String,
    onShowConsentClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(24.dp),
    ) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .wrapContentHeight(align = Alignment.Bottom),
            text = count,
            fontSize = 200.sp,
            textAlign = TextAlign.Center,
        )
        Text(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .wrapContentHeight(align = Alignment.Top),
            text = stringResource(id = R.string.consent_score),
            textAlign = TextAlign.Center,
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onShowConsentClick
        ) {
            Text(text = stringResource(id = R.string.show_consent_banner))
        }
    }
}

@Composable
private fun CalculatorLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

private class CalculatorUiStateProvider : PreviewParameterProvider<CalculatorUiState> {
    override val values = sequenceOf(
        CalculatorUiState.Initializing,
        CalculatorUiState.Idle("10"),
        CalculatorUiState.Error,
    )
}

fun NavGraphBuilder.calculatorScreen(navigate: (String) -> Unit) = composable(
    route = calculatorRoute,
) {
    CalculatorScreen(navigate)
}

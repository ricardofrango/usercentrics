package com.ricardo.usercentrics.presentation.calculator

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ricardo.usercentrics.R
import com.ricardo.usercentrics.composable.UsercentricsPreview
import com.ricardo.usercentrics.domain.model.Service
import com.ricardo.usercentrics.ui.theme.UsercentricsTheme
import com.usercentrics.sdk.UsercentricsBanner

const val calculatorRoute = "calculator"

@UsercentricsPreview
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
private fun CalculatorScreen() {
    val viewModel = hiltViewModel<CalculatorViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val onShowConsentClick = {
        val banner = UsercentricsBanner(context)
        banner.showSecondLayer { userResponse ->
            viewModel.calculate(userResponse)
        }
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
            count = uiState.totalCost,
            services = uiState.services,
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
    count: Int,
    services: List<Service>,
    onShowConsentClick: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            CalculatorReadyLandscape(
                modifier = modifier,
                count = count,
                services = services,
                onShowConsentClick = onShowConsentClick
            )
        }

        else -> {
            CalculatorReadyPortrait(
                modifier = modifier,
                count = count,
                services = services,
                onShowConsentClick = onShowConsentClick
            )
        }
    }
}

@Composable
private fun CalculatorReadyLandscape(
    modifier: Modifier = Modifier,
    count: Int,
    services: List<Service>,
    onShowConsentClick: () -> Unit,
) {
    Column(
        modifier = modifier.padding(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(align = Alignment.Bottom),
                    text = count.toString(),
                    fontSize = 150.sp,
                    textAlign = TextAlign.Center,
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(align = Alignment.Top),
                    text = stringResource(id = R.string.consent_score),
                    textAlign = TextAlign.Center,
                )
            }
            ServicesList(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxSize()
                    .weight(1f),
                services = services
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onShowConsentClick
        ) {
            Text(text = stringResource(id = R.string.show_consent_banner))
        }
    }
}

@Composable
private fun CalculatorReadyPortrait(
    modifier: Modifier = Modifier,
    count: Int,
    services: List<Service>,
    onShowConsentClick: () -> Unit,
) {
    Column(
        modifier = modifier.padding(12.dp),
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.Bottom),
            text = count.toString(),
            fontSize = 150.sp,
            textAlign = TextAlign.Center,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(align = Alignment.Top),
            text = stringResource(id = R.string.consent_score),
            textAlign = TextAlign.Center,
        )
        ServicesList(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxSize()
                .weight(1f),
            services = services
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
fun ServicesList(modifier: Modifier = Modifier, services: List<Service>) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(
            items = services,
            key = { index, _ -> index }
        ) { index, item ->
            ShowService(
                service = item
            )
            if (index < services.lastIndex) HorizontalDivider(
                modifier = Modifier.padding(top = 12.dp), thickness = 1.dp
            )
        }
    }
}

@Composable
private fun ShowService(
    modifier: Modifier = Modifier, service: Service
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), text = service.name
        )
        Text(text = service.cost.toString())
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
        CalculatorUiState.Idle(
            services = listOf(
                Service(
                    name = "Service One",
                    cost = 10
                ), Service(
                    name = "Service Two",
                    cost = 10
                )
            ),
            totalCost = 20
        ),
        CalculatorUiState.Error,
    )
}

fun NavGraphBuilder.calculatorScreen() = composable(
    route = calculatorRoute,
) {
    CalculatorScreen()
}

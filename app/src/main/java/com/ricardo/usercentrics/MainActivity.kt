package com.ricardo.usercentrics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ricardo.usercentrics.presentation.calculator.calculatorRoute
import com.ricardo.usercentrics.presentation.calculator.calculatorScreen
import com.ricardo.usercentrics.ui.theme.UsercentricsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }
}

@Composable
private fun Content() {
    val navController = rememberNavController()
    UsercentricsTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = calculatorRoute
            ) {
                calculatorScreen()
            }
        }
    }
}
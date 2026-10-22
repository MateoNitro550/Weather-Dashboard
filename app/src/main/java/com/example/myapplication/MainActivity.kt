package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WeatherDashboard(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class WeatherData(val temperature: Int, val condition: String)

@Composable
fun WeatherDashboard(modifier: Modifier = Modifier) {
    val weatherFlow = remember {
        MutableStateFlow(
            WeatherData(72, "Clear")
        )
    }

    val weatherData by weatherFlow.collectAsState()

    var alertLevel by remember {
        mutableStateOf("Normal")
    }

    LaunchedEffect(Unit) {
        val conditions = listOf(
            "Clear",
            "Cloudy",
            "Rain",
            "Thunderstorm",
            "Windy",
            "Humid"
        )

        while (true) {
            delay(3000)
            weatherFlow.value = WeatherData((5..120).random(), conditions.random())
        }
    }

    LaunchedEffect(weatherData) {
        alertLevel = when {
            weatherData.temperature >= 100 -> "Heat Wave"
            weatherData.temperature <= 32 -> "Freeze Warning"
            else -> "Normal"
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${weatherData.temperature}°F",
                    style = MaterialTheme.typography.displaySmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = weatherData.condition,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Alert: $alertLevel",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (alertLevel == "Normal") {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        MaterialTheme.colorScheme.error
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherDashboardPreview() {
    MaterialTheme {
        WeatherDashboard()
    }
}

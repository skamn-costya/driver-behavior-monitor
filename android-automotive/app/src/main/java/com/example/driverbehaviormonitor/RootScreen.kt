package com.example.driverbehaviormonitor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.material3.Button
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RootScreen(state: VehicleState) {
    var currentScreen by remember { mutableStateOf("dashboard") }

    Column {
        Row(
            modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp),horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { currentScreen = "dashboard" }) { Text("Dashboard") }
            Button(onClick = { currentScreen = "info" }) { Text("Info") }
        }
        when (currentScreen) {
            "dashboard" -> DashboardScreen(state)
            "info" -> InfoScreen(state)
        }
    }
}

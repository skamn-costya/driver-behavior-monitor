package com.example.driverbehaviormonitor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardScreen(state: VehicleState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            GaugeNeedle(state.rpm.toFloat(), 7000f, "RPM", Color.Red)
            GaugeNeedle(state.speed.toFloat(), 240f, "km/h", Color.Green)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            GaugeBar(state.fuelLevel.toFloat(), 100f, "Fuel", Color.Yellow)
            GaugeBar(state.engineTemp.toFloat(), 120f, "°C", Color.Cyan)
        }

        Text("Odometer: ${state.odometer} km", fontSize = 22.sp, color = Color.White)
        Text("Gear: ${state.gear}", fontSize = 28.sp, color = Color.LightGray)

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("◀", fontSize = 40.sp, color = if (state.leftBlinkerOn) Color.Green else Color.Gray)
            Text("Seatbelt", fontSize = 24.sp, color = if (state.seatbeltOn) Color.Green else Color.Red)
            Text("▶", fontSize = 40.sp, color = if (state.rightBlinkerOn) Color.Green else Color.Gray)
        }
    }
}

@Preview
@Composable
fun PreviewDashboardScreen() {
    val mockState = VehicleState(
        speed = 0,
        rpm = 0,
        fuelLevel = 100,
        engineTemp = 90,
        odometer = 0,
        gear = "P",
        seatbeltOn = true,
        leftBlinkerOn = false,
        rightBlinkerOn = false
    )
    DashboardScreen(state = mockState)
}
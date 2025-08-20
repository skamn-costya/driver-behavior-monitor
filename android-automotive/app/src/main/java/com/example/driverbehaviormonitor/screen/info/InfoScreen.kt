package com.example.driverbehaviormonitor.screen.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.driverbehaviormonitor.VehicleState

@Composable
fun InfoScreen(state: VehicleState) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Speed: ${state.speed} km/h", fontSize = 20.sp, color = Color.White)
        Text("RPM: ${state.rpm}", fontSize = 20.sp, color = Color.White)
        Text("Fuel: ${state.fuelLevel}%", fontSize = 20.sp, color = Color.White)
        Text("Engine Temp: ${state.engineTemp}°C", fontSize = 20.sp, color = Color.White)
        Text("Odometer: ${state.odometer} km", fontSize = 20.sp, color = Color.White)
        Text("Gear: ${state.gear}", fontSize = 20.sp, color = Color.White)
        Text("Seatbelt: ${if (state.seatbeltOn) "ON" else "OFF"}", fontSize = 20.sp, color = Color.White)
        Text("Left Blinker: ${if (state.leftBlinkerOn) "ON" else "OFF"}", fontSize = 20.sp, color = Color.White)
        Text("Right Blinker: ${if (state.rightBlinkerOn) "ON" else "OFF"}", fontSize = 20.sp, color = Color.White)
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1408dp,height=792dp,dpi=160",
    name = "AAOS 1024x768 Preview"
)
@Composable
fun PreviewInfoScreen() {
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
    InfoScreen(state = mockState)
}

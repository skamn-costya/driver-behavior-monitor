package com.example.driverbehaviormonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var state by remember { mutableStateOf(VehicleState()) }

            // корутина, которая эмулирует данные
            LaunchedEffect(Unit) {
                var tick = 0
                while (true) {
                    tick++
                    state = state.copy(
                        speed = (state.speed + Random.nextInt(-5, 10)).coerceIn(0, 240),
                        rpm = (state.rpm + Random.nextInt(-200, 400)).coerceIn(600, 7000),
                        fuelLevel = (state.fuelLevel - 0.01f).toInt().coerceAtLeast(0),
                        engineTemp = (90 + Random.nextInt(-2, 3)),
                        odometer = state.odometer + Random.nextInt(0, 2),
                        gear = listOf("P", "D", "N", "R").random(),
                        seatbeltOn = Random.nextBoolean(),
                        leftBlinkerOn = (tick % 2 == 0),
                        rightBlinkerOn = (tick % 3 == 0)
                    )
                    delay(700) // теперь это внутри корутины
                }
            }

            RootScreen(state)
        }

    }
}

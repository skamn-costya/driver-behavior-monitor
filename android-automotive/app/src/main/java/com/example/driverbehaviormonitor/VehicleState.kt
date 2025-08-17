package com.example.driverbehaviormonitor

data class VehicleState(
    val speed: Int = 0,
    val rpm: Int = 0,
    val fuelLevel: Int = 100,
    val engineTemp: Int = 90,
    val odometer: Int = 0,
    val gear: String = "P",
    val seatbeltOn: Boolean = true,
    val leftBlinkerOn: Boolean = false,
    val rightBlinkerOn: Boolean = false
)

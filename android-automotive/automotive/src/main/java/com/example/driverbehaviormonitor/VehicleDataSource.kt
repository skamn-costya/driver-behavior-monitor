package com.example.driverbehaviormonitor

interface VehicleDataSource {
    fun startListening(callback: (VehicleData) -> Unit)
    fun stopListening()
}

data class VehicleData(
    val speed: Float? = null,
    val rpm: Float? = null,
    val fuelLevel: Float? = null,
    val gear: Int? = null,
    val engineTemp: Float? = null
)

package com.example.driverbehaviormonitor

import android.os.Handler
import android.os.Looper
import kotlin.random.Random

class FakeVehicleDataSource : VehicleDataSource {

    private var listener: ((VehicleData) -> Unit)? = null
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            val data = VehicleData(
                speed = Random.nextFloat() * 120f,
                rpm = 600 + Random.nextFloat() * 5000,
                fuelLevel = Random.nextFloat() * 100,
                gear = Random.nextInt(1, 6),
                engineTemp = 60 + Random.nextFloat() * 40
            )
            listener?.invoke(data)
            handler.postDelayed(this, 1000)
        }
    }

    override fun startListening(callback: (VehicleData) -> Unit) {
        listener = callback
        handler.post(runnable)
    }

    override fun stopListening() {
        handler.removeCallbacks(runnable)
    }
}

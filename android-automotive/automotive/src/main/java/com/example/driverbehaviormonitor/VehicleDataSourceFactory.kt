package com.example.driverbehaviormonitor

import android.content.Context
import android.util.Log

object VehicleDataSourceFactory {
    fun create(context: Context): VehicleDataSource {
        return try {
            CarVehicleDataSource(context).also {
                Log.i("VehicleDataSourceFactory", "Using real CarVehicleDataSource")
            }
        } catch (e: Exception) {
            Log.w("VehicleDataSourceFactory", "Falling back to FakeVehicleDataSource", e)
            FakeVehicleDataSource()
        }
    }
}

package com.example.driverbehaviormonitor

import android.car.Car
import android.car.hardware.property.CarPropertyManager
import android.car.hardware.CarPropertyValue
import android.car.VehiclePropertyIds
import android.content.Context
import android.util.Log

class CarVehicleDataSource(private val context: Context) : VehicleDataSource {

    private var car: Car? = null
    private var carPropertyManager: CarPropertyManager? = null
    private var listener: ((VehicleData) -> Unit)? = null

    override fun startListening(callback: (VehicleData) -> Unit) {
        listener = callback
        try {
            car = Car.createCar(context).also {
                it.connect()
                carPropertyManager = it.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
            }

            carPropertyManager?.registerCallback(
                object : CarPropertyManager.CarPropertyEventCallback {
                    override fun onChangeEvent(value: CarPropertyValue<*>) {
                        val data = when (value.propertyId) {
                            VehiclePropertyIds.PERF_VEHICLE_SPEED ->
                                VehicleData(speed = value.value as? Float)
                            VehiclePropertyIds.ENGINE_RPM ->
                                VehicleData(rpm = value.value as? Float)
                            VehiclePropertyIds.FUEL_LEVEL ->
                                VehicleData(fuelLevel = value.value as? Float)
                            VehiclePropertyIds.GEAR_SELECTION ->
                                VehicleData(gear = value.value as? Int)
                            VehiclePropertyIds.ENGINE_COOLANT_TEMP ->
                                VehicleData(engineTemp = value.value as? Float)
                            else -> null
                        }
                        data?.let { listener?.invoke(it) }
                    }
                    override fun onErrorEvent(propId: Int, zone: Int) {
                        Log.e("CarVehicleDataSource", "Error: propId=$propId zone=$zone")
                    }
                },
                VehiclePropertyIds.PERF_VEHICLE_SPEED, 0.5f
            )

        } catch (e: Exception) {
            Log.e("CarVehicleDataSource", "Failed to connect to vehicle HAL", e)
        }
    }

    override fun stopListening() {
        car?.disconnect()
    }
}

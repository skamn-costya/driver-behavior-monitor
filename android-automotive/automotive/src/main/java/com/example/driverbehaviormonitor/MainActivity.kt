package com.example.driverbehaviormonitor

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.driverbehaviormonitor.FakeVehicleDataSource
//import com.example.driverbehaviormonitor

class MainActivity : AppCompatActivity() {

    private lateinit var dataSource: VehicleDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataSource = VehicleDataSourceFactory.create(this)

        dataSource.startListening { data ->
            runOnUiThread {
                // обновляем UI
                findViewById<TextView>(R.id.txtSpeed).text = "Speed: ${data.speed ?: "-"} km/h"
                findViewById<TextView>(R.id.txtRpm).text = "RPM: ${data.rpm ?: "-"}"
                findViewById<TextView>(R.id.txtFuel).text = "Fuel: ${data.fuelLevel ?: "-"}%"
                findViewById<TextView>(R.id.txtGear).text = "Gear: ${data.gear ?: "-"}"
                findViewById<TextView>(R.id.txtTemp).text = "Temp: ${data.engineTemp ?: "-"} °C"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dataSource.stopListening()
    }
}

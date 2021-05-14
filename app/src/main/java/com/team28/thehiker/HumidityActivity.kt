package com.team28.thehiker

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class HumidityActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var txt_RH: TextView
    private lateinit var sensor_RH: Sensor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_humidity)

        txt_RH = findViewById<TextView>(R.id.humidity)
        val my_sensor_manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensor_RH = my_sensor_manager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
        my_sensor_manager.registerListener(this, sensor_RH, SensorManager.SENSOR_DELAY_NORMAL)



    }
    @Override
    public override fun onSensorChanged(event: SensorEvent?) {

        if (event != null) {
            txt_RH.text = event.values[0].toString()
        }
    }

    @Override
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }



}
package com.team28.thehiker

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

class TemperatureWrapper(private var sensorManager: SensorManager){

    private val temperatureSensor : Sensor?

    init {
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
    }

    fun isTemperatureSensorAvailable() : Boolean{

        return temperatureSensor != null
    }
}
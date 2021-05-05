package com.team28.thehiker

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

class TemperatureWrapper(private var sensorManager: SensorManager){

    fun isTemperatureSensorAvailable() : Boolean{
        return false
    }

}
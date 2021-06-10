package com.team28.thehiker.features.humidity

import android.hardware.Sensor
import android.hardware.SensorManager

open class HumidityWrapper(private var sensorManager: SensorManager) {
    private val humiditySensor : Sensor?

    init {
        humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
    }

    open fun isHumiditySensorAvailable() : Boolean{
        return humiditySensor != null
    }
}
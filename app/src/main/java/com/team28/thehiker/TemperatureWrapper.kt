package com.team28.thehiker

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

open class TemperatureWrapper(private var sensorManager: SensorManager): SensorEventListener{

    private val temperatureSensor : Sensor?
    private var temperature : Double? = null


    init {
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        if (temperatureSensor != null) {
            sensorManager.registerListener(this, temperatureSensor, 1000);
        }
    }

    fun kill(){
        if(temperatureSensor != null){
            sensorManager.unregisterListener(this)
        }
    }

    open fun isTemperatureSensorAvailable() : Boolean{
        return temperatureSensor != null
    }

    open fun getTemperature() : Double?{
        return temperature
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event != null){
            temperature = event.values[0].toDouble()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
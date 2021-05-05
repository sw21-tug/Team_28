package com.team28.thehiker

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import kotlinx.coroutines.Delay


class PedometerActivity  : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager : SensorManager
    private lateinit var stepSensor : Sensor
    private var sensorPresent : Boolean = false
    private var stepsTaken : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedometer)
        supportActionBar?.hide()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        checkStepSensorAvailable()

        if(sensorPresent)
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL)

    }

    fun checkStepSensorAvailable(){
        if( sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)!=null) {
            stepSensor=sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
            sensorPresent = true
        }
    }

    fun updateStepCounter (steps : Int){
        val steps_text = findViewById<TextView>(R.id.txtViewSteps)
        steps_text.text = steps.toString()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!= null){
            updateStepCounter(++stepsTaken)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

}
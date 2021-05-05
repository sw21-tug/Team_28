package com.team28.thehiker

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService


class PedometerActivity  : AppCompatActivity(), SensorEventListener {
   private lateinit var sensorManager : SensorManager
    private lateinit var stepSensor : Sensor
    private var sensorPresent : Boolean = false
    private lateinit var stepsTaken : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedometer)
        supportActionBar?.hide()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
       if( sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)!=null){
           stepSensor=sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
           sensorPresent = true
       }
        stepsTaken  = findViewById<TextView>(R.id.txtViewSteps).text as String
    }

    fun updateStepCounter (steps : Int){
        val steps_text = findViewById<TextView>(R.id.txtViewSteps)
        steps_text.text= steps.toString()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        stepsTaken = findViewById<TextView>(R.id.txtViewSteps).text as String
        var stepCounter : Int = stepsTaken.toInt()
        if (event!= null){
            updateStepCounter(stepCounter++)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }


}
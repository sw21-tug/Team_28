package com.team28.thehiker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import com.team28.thehiker.SharedPreferenceHandler.SharedPreferenceHandler
import kotlinx.coroutines.Delay
import java.time.LocalDate
import java.time.LocalDateTime


class PedometerActivity  : AppCompatActivity(), SensorEventListener {

    lateinit var sharedPreferenceHandler : SharedPreferenceHandler

    private lateinit var sensorManager : SensorManager
    private lateinit var stepSensor : Sensor
    private var sensorPresent : Boolean = false
    var stepsTaken : Int = 0

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

    private fun setAlarm(timeInMillieSeconds : Long)
    {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, ResetAlarm::class.java)
        val resetStepsIntent = PendingIntent.getBroadcast(this, 0, intent,0)
        alarmManager.setRepeating(
            AlarmManager.RTC,
            timeInMillieSeconds,
            AlarmManager.INTERVAL_DAY,
            resetStepsIntent
        )
    }

    public fun resetSteps()
    {
        this.stepsTaken = 0
    }

    fun getSavedStepCount() : Int {
        TODO("Not yet implemented")
    }

    fun setSavedStepCount() {
        TODO("Not yet implemented")
    }

    fun getLastStepCountUpdate() : LocalDate {
        TODO("Not yet implemented")
    }

    fun setLastStepCountUpdate(dateTime : LocalDate) {
        TODO("Not yet implemented")
    }

    private class  ResetAlarm : BroadcastReceiver() {
        override fun onReceive(
            context: Context,
            intent: Intent
        ) {

        }
    }

}
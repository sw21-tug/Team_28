package com.team28.thehiker

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.team28.thehiker.Constants.Constants
import com.team28.thehiker.SharedPreferenceHandler.SharedPreferenceHandler
import java.util.*


class PedometerActivity  : AppCompatActivity(), SensorEventListener {

    lateinit var sharedPreferenceHandler: SharedPreferenceHandler

    private lateinit var sensorManager: SensorManager
    private lateinit var stepSensor: Sensor
    var sensorPresent: Boolean = false
    var stepsTaken: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedometer)
        supportActionBar?.hide()

        sharedPreferenceHandler = SharedPreferenceHandler()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        checkStepSensorAvailable()

        if (sensorPresent)
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL)

        checkIfNewDay()

        getSavedStepCount()
        updatePedometerView()
    }

    fun checkIfNewDay() {
        val last = getLastStepCountUpdate()
        val now = Calendar.getInstance()

        val lastEventDayOfYear = last.get(Calendar.DAY_OF_YEAR)
        val lastEventYear = last.get(Calendar.YEAR)

        val nowEventDayOfYear = now.get(Calendar.DAY_OF_YEAR)
        val nowEventYear = now.get(Calendar.YEAR)

        if (nowEventDayOfYear > lastEventDayOfYear && nowEventYear >= lastEventYear) {
            stepsTaken = 0
            setSavedStepCount()
        }
    }

    fun checkStepSensorAvailable() {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
            sensorPresent = true
        }
    }

    fun updatePedometerView() {
        val stepsText = findViewById<TextView>(R.id.txtViewSteps)
        stepsText.text = stepsTaken.toString()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            ++stepsTaken
            updatePedometerView()

            setLastStepCountUpdate(Calendar.getInstance())
            setSavedStepCount()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }


    fun getSavedStepCount() {
        stepsTaken = sharedPreferenceHandler.getSavedStepCount(this)
    }

    fun setSavedStepCount() {
        sharedPreferenceHandler.setSavedStepCount(this, stepsTaken)
    }

    fun getLastStepCountUpdate(): Calendar {
        val stringToParse = sharedPreferenceHandler.getLastStepCountUpdate(this)

        if (stringToParse == null) {
            val default = Calendar.getInstance()
            default.set(Calendar.YEAR, 1970)
            return default
        }

        if (stringToParse == Constants.SharedPreferenceConstants.LAST_STEPCOUNT_DEFAULT) {
            val default = Calendar.getInstance()
            default.set(Calendar.YEAR, 1970)
            return default
        }
        val split = stringToParse.split("_")
        val day = split[1].toInt()
        val year = split[0].toInt()

        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_YEAR, day)
        cal.set(Calendar.YEAR, year)

        return cal
    }

    fun setLastStepCountUpdate(calendar: Calendar) {
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val year = calendar.get(Calendar.YEAR)

        val saveAs = year.toString() + "_" + dayOfYear.toString()

        sharedPreferenceHandler.setLastStepCountUpdate(this, saveAs)
    }

    override fun onResume() {
        super.onResume()

        checkIfNewDay()
        updatePedometerView()
    }
}
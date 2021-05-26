package com.team28.thehiker

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.team28.thehiker.Constants.Constants
import com.team28.thehiker.SharedPreferenceHandler.SharedPreferenceHandler
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class PedometerActivity  : AppCompatActivity(), SensorEventListener {

    lateinit var sharedPreferenceHandler: SharedPreferenceHandler

    private lateinit var sensorManager: SensorManager
    private lateinit var stepSensor: Sensor
    private var sensorPresent: Boolean = false
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

    fun openHistory(view: View)
    {
        if(view.id == R.id.button_history)
            startActivity(Intent(this, StepCountHistoryActivity::class.java))
    }

    inline fun <reified T> genericType() = object: TypeToken<T>() {}.type

    fun checkIfNewDay() {
        val last = getLastStepCountUpdate()
        val now = Calendar.getInstance()

        val lastEventDayOfYear = last.get(Calendar.DAY_OF_YEAR)
        val lastEventYear = last.get(Calendar.YEAR)

        val nowEventDayOfYear = now.get(Calendar.DAY_OF_YEAR)
        val nowEventYear = now.get(Calendar.YEAR)

        if (nowEventDayOfYear > lastEventDayOfYear && nowEventYear >= lastEventYear) {
            val gson = Gson()
            var step_history_json = sharedPreferenceHandler.getStepCountHistory(this)

            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, -1)
            val formatter: DateFormat = SimpleDateFormat("dd/MM/yyyy")
            val date: String = formatter.format(calendar.time)
            var step_history = mutableListOf<String>()

            if(step_history_json != "")
            {
                val type = genericType<MutableList<String>>()
                step_history = gson.fromJson(step_history_json, type)

                while (step_history.size > 19)
                    step_history.removeAt(0)
            }

            step_history.add(date + "_" + stepsTaken)
            step_history_json = gson.toJson(step_history)
            sharedPreferenceHandler.setStepCountHistory(this, step_history_json)

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
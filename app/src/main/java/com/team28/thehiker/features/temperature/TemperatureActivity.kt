package com.team28.thehiker.features.temperature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.team28.thehiker.R

class TemperatureActivity : AppCompatActivity() {

    companion object{
        val TEMP_KEY = "TEMPERATURE"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperature)

        val temperature = intent.getDoubleExtra(TEMP_KEY, Double.MIN_VALUE)
        updateTemperature(temperature)
    }

    fun updateTemperature(temperature: Double) {
        val temperatureTextView : TextView = findViewById(R.id.temperature)

        val stringBuilder = StringBuilder()
        stringBuilder.append(String.format("%.1f",temperature).replace(",","."))
        stringBuilder.append(" ")
        stringBuilder.append("°C")

        temperatureTextView.text = stringBuilder.toString()
    }

}
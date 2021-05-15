package com.team28.thehiker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class TemperatureActivity : AppCompatActivity() {

    companion object{
        val TEMP_KEY = "TEMPERATURE"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperature)
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
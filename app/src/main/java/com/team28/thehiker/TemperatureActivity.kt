package com.team28.thehiker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TemperatureActivity : AppCompatActivity() {

    companion object{
        public val TEMP_KEY = "TEMPERATURE"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperature)
    }

}
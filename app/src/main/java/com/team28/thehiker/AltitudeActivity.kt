package com.team28.thehiker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class AltitudeActivity : AppCompatActivity() {
    private lateinit var locationService : HikerLocationService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_altitude)

    }

    fun updateAltitude(altitude : Double){
        val altitude_text = findViewById<TextView>(R.id.altitude)
        altitude_text.text = String.format("%.2f m",altitude)


    }

    fun getLocationService() : HikerLocationService = locationService
}
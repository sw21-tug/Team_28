package com.team28.thehiker

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SpeedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speed)
    }

    fun updateSpeed(speed : Double){
        val speedText = findViewById<TextView>(R.id.speed)

        val stringBuilder = StringBuilder()
        stringBuilder.append(String.format("%.2f",speed).replace(",","."))
        stringBuilder.append(" km/h")
        speedText.text = stringBuilder.toString()
    }
}

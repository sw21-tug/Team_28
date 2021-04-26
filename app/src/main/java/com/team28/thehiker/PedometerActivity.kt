package com.team28.thehiker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class PedometerActivity  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedometer)
        supportActionBar?.hide()
    }
}
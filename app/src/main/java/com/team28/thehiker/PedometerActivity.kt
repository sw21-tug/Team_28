package com.team28.thehiker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlin.Boolean

class PedometerActivity  : AppCompatActivity() {

    var faultback_active : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedometer)
        supportActionBar?.hide()
    }
}
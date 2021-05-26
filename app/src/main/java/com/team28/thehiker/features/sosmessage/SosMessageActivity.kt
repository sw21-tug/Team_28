package com.team28.thehiker.features.sosmessage

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.team28.thehiker.R
import com.team28.thehiker.location.HikerLocationService

class SosMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sos_message)
        setSupportActionBar(findViewById(R.id.toolbar))

    }

    fun injectWrapper(mockSmsWrapper: SMSWrapper) {

    }

    fun getLocationService() : HikerLocationService? = null
}
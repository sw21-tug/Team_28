package com.team28.thehiker.features.sosmessage

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.telephony.SmsManager
import android.util.Log
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.team28.thehiker.R
import com.team28.thehiker.location.HikerLocationService

class SosMessageActivity : AppCompatActivity(), ServiceConnection {

    private val DELAY_MS_3_MINS = 3000000L
    //TODO: change in other subtask
    private val numbers = listOf("+43664664664", "+497887788")
    private lateinit var wrapper : SMSWrapper
    private lateinit var locationService : HikerLocationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sos_message)
        setSupportActionBar(findViewById(R.id.toolbar))

        bindService(Intent(this, HikerLocationService::class.java), this, Context.BIND_AUTO_CREATE)

        val manager : SmsManager = SmsManager.getDefault()
        wrapper = SMSWrapper(manager, DELAY_MS_3_MINS, numbers, this)

        val button = findViewById<Button>(R.id.stop_sos_btn)
        button.setOnClickListener {
            wrapper.stop()
            finish()
        }
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        Log.d("SosMessageActivity", "Service Connected")
        locationService = (service as HikerLocationService.HikerLocationBinder).getService()
        locationService.addLocationCallback(wrapper)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        Log.d("SosMessageActivity", "Service Disconnected")
    }

    fun injectWrapper(wrapper:SMSWrapper){
        this.wrapper = wrapper
        locationService.addLocationCallback(wrapper)
    }

    fun getLocationService() : HikerLocationService = locationService
}
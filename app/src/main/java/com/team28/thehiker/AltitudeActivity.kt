package com.team28.thehiker

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.TextView

class AltitudeActivity : AppCompatActivity(), ServiceConnection, HikerLocationCallback {
    private lateinit var locationService : HikerLocationService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_altitude)
        bindService(Intent(this, HikerLocationService::class.java), this, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(this)
    }

    fun updateAltitude(altitude : Double){
        val altitudeText = findViewById<TextView>(R.id.altitude)

        val stringBuilder = StringBuilder()
        stringBuilder.append(String.format("%.2f",altitude).replace(",","."))
        stringBuilder.append(" ")
        stringBuilder.append(resources.getString(R.string.meters))
        altitudeText.text = stringBuilder.toString()
    }

    fun getLocationService() : HikerLocationService = locationService
    override fun onServiceDisconnected(name: ComponentName?) {
        Log.d("AltitudeActivity", "Service Disconnected")
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        Log.d("AltitudeActivity", "Service Connected")
        locationService = (service as HikerLocationService.HikerLocationBinder).getService()
        locationService.addLocationCallback(this)
    }

    override fun notifyLocationUpdate(location: Location) {
        Log.d("AltitudeActivity", location.toString())
        if(location.hasAltitude()){
            updateAltitude(location.altitude)
        }
    }
}
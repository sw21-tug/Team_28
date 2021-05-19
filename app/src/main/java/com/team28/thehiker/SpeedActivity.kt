package com.team28.thehiker

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class SpeedActivity : AppCompatActivity(), ServiceConnection, HikerLocationCallback {

    private val previousLocation : Location? = null

    private lateinit var locationService : HikerLocationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speed)
        bindService(Intent(this, HikerLocationService::class.java), this, Context.BIND_AUTO_CREATE)
    }

    fun updateSpeed(speed : Float){
        val speedText = findViewById<TextView>(R.id.speed)

        val stringBuilder = StringBuilder()
        stringBuilder.append(String.format("%.2f",speed).replace(",","."))
        stringBuilder.append(" km/h")
        speedText.text = stringBuilder.toString()
    }

    fun calculateSpeed(location1 : Location, location2 : Location) {
        val distance = location1.distanceTo(location2)
        val time = (location2.time - location1.time) / 1000

        updateSpeed(((distance/time) * 3.6).toFloat())
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(this)
    }

    fun getLocationService()  : HikerLocationService = locationService

    override fun onServiceDisconnected(name: ComponentName?) {
        TODO("Not yet implemented")
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        TODO("Not yet implemented")
    }

    override fun notifyLocationUpdate(location: Location) {
        TODO("Not yet implemented")
    }

     fun getPreviousLocation() : Location? {
        return previousLocation
    }

}

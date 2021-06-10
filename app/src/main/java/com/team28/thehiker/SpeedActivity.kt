package com.team28.thehiker

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.location.Location
import android.os.Bundle
import android.os.IBinder
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.team28.thehiker.location.HikerLocationCallback
import com.team28.thehiker.location.HikerLocationService

class SpeedActivity : AppCompatActivity(), ServiceConnection, HikerLocationCallback {

    private var previousLocation : Location? = null

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

        val speed = (distance/time) * 3.6.toFloat()
        if (time != 0L && speed >= 0.0f){
            updateSpeed(speed)
        }else{
            updateSpeed(0.0f)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(this)
    }

    fun getLocationService()  : HikerLocationService = locationService

    override fun onServiceDisconnected(name: ComponentName?) {
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        locationService = (service as HikerLocationService.HikerLocationBinder).getService()
        locationService.addLocationCallback(this)
    }

    override fun notifyLocationUpdate(location: Location) {
        if(previousLocation == null) {
            previousLocation = location
        } else {
            calculateSpeed(previousLocation!!, location)
            previousLocation = location
        }
    }

     fun getPreviousLocation() : Location? {
        return previousLocation
    }

}

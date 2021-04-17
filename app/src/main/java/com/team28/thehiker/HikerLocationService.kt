package com.team28.thehiker

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient

class HikerLocationService : Service() {

    private val binder = HikerLocationBinder()
    private lateinit var locationProvider: FusedLocationProviderClient

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun getLocationProvider() : FusedLocationProviderClient{
        return locationProvider
    }

    fun addLocationCallback(locationCallback: HikerLocationCallback) {

    }

    inner class HikerLocationBinder : Binder() {
        fun getService() :HikerLocationService {
            return this@HikerLocationService
        }
    }


}
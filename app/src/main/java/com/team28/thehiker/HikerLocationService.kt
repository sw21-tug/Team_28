package com.team28.thehiker

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*

class HikerLocationService : Service() {

    private val binder = HikerLocationBinder()
    private lateinit var locationProvider: FusedLocationProviderClient
    private var locationCallback = LocationCallbackHandler()
    private var callbackList = arrayListOf<HikerLocationCallback>()


    @SuppressLint("MissingPermission") // not checked because it will be checked by the MainActivity
    override fun onBind(intent: Intent): IBinder {
        locationProvider = LocationServices.getFusedLocationProviderClient(this)

        val locationRequest = LocationRequest.create()
        locationRequest.interval = 2500
        locationRequest.fastestInterval = 1000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        locationProvider.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun getLocationProvider() : FusedLocationProviderClient{
        return locationProvider
    }

    fun addLocationCallback(locationCallback: HikerLocationCallback) {
        callbackList.add(locationCallback)
    }

    inner class LocationCallbackHandler : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            callbackList.forEach { it.notifyLocationUpdate(Location("TEST")) }
        }
    }

    inner class HikerLocationBinder : Binder() {
        fun getService() :HikerLocationService {
            return this@HikerLocationService
        }
    }


}
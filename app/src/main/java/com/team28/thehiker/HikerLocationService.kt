package com.team28.thehiker

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.Looper
import com.google.android.gms.location.*
import com.team28.thehiker.HikerLocationService.LocationConstants.LOCATION_UPDATE_FASTEST_INTERVAL
import com.team28.thehiker.HikerLocationService.LocationConstants.LOCATION_UPDATE_INTERVAL

class HikerLocationService : Service() {

    object LocationConstants {
        const val LOCATION_UPDATE_INTERVAL = 2500L
        const val LOCATION_UPDATE_FASTEST_INTERVAL = 1000L
    }

    private val binder = HikerLocationBinder()
    private lateinit var locationProvider: FusedLocationProviderClient
    private var locationCallback = LocationCallbackHandler()
    private var callbackList = arrayListOf<HikerLocationCallback>()


    @SuppressLint("MissingPermission") // not checked because it will be checked by the MainActivity
    override fun onBind(intent: Intent): IBinder {
        locationProvider = LocationServices.getFusedLocationProviderClient(this)

        val locationRequest = LocationRequest.create()
        locationRequest.interval = LOCATION_UPDATE_INTERVAL
        locationRequest.fastestInterval = LOCATION_UPDATE_FASTEST_INTERVAL
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
            if (locationResult != null){
                callbackList.forEach { it.notifyLocationUpdate(locationResult.lastLocation) }
            }
        }
    }

    inner class HikerLocationBinder : Binder() {
        fun getService() :HikerLocationService {
            return this@HikerLocationService
        }
    }


}
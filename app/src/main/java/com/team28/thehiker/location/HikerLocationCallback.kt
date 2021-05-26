package com.team28.thehiker.location

import android.location.Location

interface HikerLocationCallback {
    fun notifyLocationUpdate(location: Location)
}
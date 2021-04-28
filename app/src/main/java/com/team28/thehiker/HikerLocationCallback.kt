package com.team28.thehiker

import android.location.Location

interface HikerLocationCallback {
    fun notifyLocationUpdate(location: Location)
}
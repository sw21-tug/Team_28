package com.team28.thehiker

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.location.Location
import android.location.LocationManager
import android.os.IBinder
import android.os.SystemClock
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ServiceTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class HikerLocationTest {

    @get:Rule
    var serviceRule = ServiceTestRule()

    @Test
    fun startSendingUpdatesAfterBindingService(){
        val mockCallbackInterface = Mockito.mock(HikerLocationCallback::class.java)

        // bind service and retrieve service object from binder
        val binder = serviceRule.bindService(Intent(ApplicationProvider.getApplicationContext(),HikerLocationService::class.java))
        val service = (binder as HikerLocationService.HikerLocationBinder).getService()
        service.addLocationCallback(mockCallbackInterface)

        //TODO: maybe automatically set mock app via adb
        //enable mocking
        service.getLocationProvider().setMockMode(true).addOnFailureListener { throw it }

        val mockLocation = Location(LocationManager.GPS_PROVIDER)
        mockLocation.latitude = 10.0
        mockLocation.longitude = 10.0
        mockLocation.accuracy = 1.0f
        mockLocation.time = System.currentTimeMillis()
        mockLocation.elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()

        //mock location
        service.getLocationProvider().setMockLocation(mockLocation).addOnFailureListener { throw it }

        // verify that the method notifyLocationUpdate() has been called at least once after 10 sec
        Mockito.verify(mockCallbackInterface, Mockito.after(10000).atLeastOnce()).notifyLocationUpdate()
    }
}
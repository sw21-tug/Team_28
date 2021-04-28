package com.team28.thehiker

import android.location.Location
import android.location.LocationManager
import android.os.SystemClock
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LocationAltitudeTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(AltitudeActivity::class.java)

    @Test
    fun testWithZeroAltitude(){
        //mock location
        Thread.sleep(1000) // wait for service to start
        val mockLocation = createMockLocation(0.00)
        //set location
        activityRule.scenario.onActivity { altitudeActivity ->
            val service = altitudeActivity.getLocationService()
            service.getLocationProvider().setMockMode(true).addOnFailureListener { throw it }
            service.getLocationProvider().setMockLocation(mockLocation).addOnFailureListener { throw it }
        }

        Thread.sleep(2500) // wait for location update to arrive
        Espresso.onView(ViewMatchers.withId(R.id.altitude)).check(ViewAssertions.matches(ViewMatchers.withText("0.00 m")))
    }

    @Test
    fun testWithPositiveAltitude(){
        //mock location
        Thread.sleep(1000) // wait for service to start
        val mockLocation = createMockLocation(123.45)
        //set location
        activityRule.scenario.onActivity { altitudeActivity ->
            val service = altitudeActivity.getLocationService()
            service.getLocationProvider().setMockMode(true).addOnFailureListener { throw it }
            service.getLocationProvider().setMockLocation(mockLocation).addOnFailureListener { throw it }
        }

        Thread.sleep(2500) // wait for location update to arrive
        Espresso.onView(ViewMatchers.withId(R.id.altitude)).check(ViewAssertions.matches(ViewMatchers.withText("123.45 m")))
    }

    @Test
    fun testWithNegativeAltitude(){
        //mock location
        Thread.sleep(1000) // wait for service to start
        val mockLocation = createMockLocation(-13.01)
        //set location
        activityRule.scenario.onActivity { altitudeActivity ->
            val service = altitudeActivity.getLocationService()
            service.getLocationProvider().setMockMode(true).addOnFailureListener { throw it }
            service.getLocationProvider().setMockLocation(mockLocation).addOnFailureListener { throw it }
        }

        Thread.sleep(2500) // wait for location update to arrive
        Espresso.onView(ViewMatchers.withId(R.id.altitude)).check(ViewAssertions.matches(ViewMatchers.withText("-13.01 m")))
    }

    private fun createMockLocation(altitude : Double) : Location{
        val mockLocation = Location(LocationManager.GPS_PROVIDER)
        mockLocation.latitude = 10.0
        mockLocation.longitude = 10.0
        mockLocation.accuracy = 1.0f
        mockLocation.altitude = altitude
        mockLocation.time = System.currentTimeMillis()
        mockLocation.elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()

        return mockLocation
    }

}
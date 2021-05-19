package com.team28.thehiker

import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.SystemClock
import androidx.annotation.RequiresApi
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SpeedTest {

    private val DOUBLE_COMPARE_DELTA = 0.0001

    @get:Rule
    var activityRule = ActivityScenarioRule(SpeedActivity::class.java)

    @Test
    fun testValueUpdateWithZeroUI(){
        activityRule.scenario.onActivity { it.updateSpeed(0.0f) }
        onView(withId(R.id.speed)).check(matches(withText("0.00 km/h")))
    }

    @Test
    fun testValueUpdateWithPositiveNumberUI(){
        activityRule.scenario.onActivity { it.updateSpeed(6.0f) }
        onView(withId(R.id.speed)).check(matches(withText("6.00 km/h")))
    }

    @Test
    fun testSpeedCalculation(){
        val location1 = createMockLocation(47.06114502512434, 15.452581310572223)
        Thread.sleep(1000)
        val location2 = createMockLocation(47.061190704295825, 15.452698657207094)
        activityRule.scenario.onActivity { it.calculateSpeed(location1, location2) }
        onView(withId(R.id.speed)).check(matches(withText("49.97 km/h")))
    }

    @Test
    fun testIfPreviousLocationIsSet() {
        //mock location
        Thread.sleep(1000) // wait for service to start
        val location = createMockLocation(47.06114502512434, 15.452581310572223)

        //set location
        activityRule.scenario.onActivity { speedActivity ->
            val service = speedActivity.getLocationService()
            service.getLocationProvider().setMockMode(true).addOnFailureListener { throw it }
            service.getLocationProvider().setMockLocation(location).addOnFailureListener { throw it }

            Thread.sleep(2500) // wait for location update to arrive

            assertEquals(location.longitude, speedActivity.getPreviousLocation()!!.longitude , DOUBLE_COMPARE_DELTA)
            assertEquals(location.latitude, speedActivity.getPreviousLocation()!!.latitude , DOUBLE_COMPARE_DELTA)
        }

    }

    private fun createMockLocation(longitude : Double, latitude : Double) : Location {
        val mockLocation = Location(LocationManager.GPS_PROVIDER)
        mockLocation.latitude = latitude
        mockLocation.longitude = longitude
        mockLocation.accuracy = 1.0f
        mockLocation.altitude = 1.0
        mockLocation.time = System.currentTimeMillis()
        mockLocation.elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()

        return mockLocation
    }

}
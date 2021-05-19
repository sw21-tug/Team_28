package com.team28.thehiker

import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.SystemClock
import androidx.annotation.RequiresApi
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SpeedTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(SpeedActivity::class.java)

    @Test
    fun testValueUpdateWithZero(){
        activityRule.scenario.onActivity { it.updateSpeed(0.0f) }
        onView(withId(R.id.speed)).check(matches(withText("0.00 km/h")))
    }

    @Test
    fun testValueUpdateWithPositiveNumber(){
        activityRule.scenario.onActivity { it.updateSpeed(6.0f) }
        onView(withId(R.id.speed)).check(matches(withText("6.00 km/h")))
    }

    @Test
    fun testSpeedCalculation(){
        val location1 = createMockLocation(47.06114502512434, 15.452581310572223)
        Thread.sleep(1000)
        val location2 = createMockLocation(47.061190704295825, 15.452698657207094)
        activityRule.scenario.onActivity { it.calculateSpeed(location1, location2) }
        onView(withId(R.id.speed)).check(matches(withText("36.00 km/h")))
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
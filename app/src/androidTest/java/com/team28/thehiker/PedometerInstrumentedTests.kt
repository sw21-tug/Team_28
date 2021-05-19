package com.team28.thehiker

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert.*
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.concurrent.thread

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class PedometerInstrumentedTests {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.team28.thehiker", appContext.packageName)
    }
    @get:Rule
    var activityRule: ActivityScenarioRule<PedometerActivity>
            = ActivityScenarioRule<PedometerActivity>(PedometerActivity::class.java)

    private var sensorManager: SensorManager? = null

    @Test
    fun onViewComponents(){
        onView(withId(R.id.txtViewPedometer)).check(matches(withText("Pedometer")))
        onView(withId(R.id.iconPedometer)).check(matches(isDisplayed()))
        onView(withId(R.id.txtViewSteps)).check(matches(isDisplayed()))
    }

    @Test
    fun noStepSensorCallFaultback()
    {
        activityRule.scenario.onActivity { sensorManager = it.getSystemService(Context.SENSOR_SERVICE)
                as SensorManager }
        if(sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) == null)
            activityRule.scenario.onActivity { assertTrue(it.faultback_active) }
        else
            activityRule.scenario.onActivity { assertFalse(it.faultback_active) }
    }

    @Test
    fun stepCalculationCorrect(){
        // faultback step calculation
        // check if steps are calculated correctly
    }

    @Test
    fun gpsDataAvailable(){
        // check if gps data available to calculate steps
    }

}
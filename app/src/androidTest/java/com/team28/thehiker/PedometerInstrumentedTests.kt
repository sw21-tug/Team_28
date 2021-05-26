package com.team28.thehiker

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationManager
import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import junit.framework.Assert.*
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import java.lang.reflect.Field
import java.time.*
import java.util.*
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
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.team28.thehiker", appContext.packageName)
    }

    @get:Rule
    var activityRule: ActivityScenarioRule<PedometerActivity>
            = ActivityScenarioRule(PedometerActivity::class.java)

    @Mock
    private var day : Long = 42

    @Before
    fun setUp() {
        Intents.init()
        activityRule.scenario.onActivity{sensorManager = it.getSystemService(Context.SENSOR_SERVICE)
                as SensorManager
                it.stepsTaken = 0
        }
    }

    private var sensorManager: SensorManager? = null

    @Test
    fun onViewComponents(){
        onView(withId(R.id.txtViewPedometer)).check(matches(withText("Pedometer")))
        onView(withId(R.id.iconPedometer)).check(matches(isDisplayed()))
        onView(withId(R.id.txtViewSteps)).check(matches(isDisplayed()))
    }

    @Test
    fun viewSteps(){
        activityRule.scenario.onActivity { it.stepsTaken = 7 }
        activityRule.scenario.onActivity { it.updatePedometerView() }
        onView(withId(R.id.txtViewSteps)).check(matches(withText("7")))
    }

    @Test
    fun callSensor(){
        mockFiveSteps()
        onView(withId(R.id.txtViewSteps)).check(matches(withText("5")))
    }

    @Test
    fun onCreateDontResetSteps()
    {
        mockStepsWithTimeStampNow()
        activityRule.scenario.onActivity {
            it.checkIfNewDay()
            it.updatePedometerView()
        }
        onView(withId(R.id.txtViewSteps)).check(matches(withText("5")))
    }

    @Test
    fun noStepSensorCallFaultback()
    {
        activityRule.scenario.onActivity { sensorManager = it.getSystemService(Context.SENSOR_SERVICE)
                as SensorManager }
        if(sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) == null)
            activityRule.scenario.onActivity { assertFalse(it.sensorPresent) }
        else
            activityRule.scenario.onActivity { assertTrue(it.sensorPresent) }
    }

    @Test
    fun testFaultbackThresholdNoChange(){
        var test_location = createMockLocation()
        var steps_before : Int = 0
        activityRule.scenario.onActivity {
            if (!it.sensorPresent) {
                it.locationOld = test_location
                steps_before = it.stepsTaken
                it.notifyLocationUpdate(test_location)
                Thread.sleep(2500)
                assertEquals(steps_before, it.stepsTaken)
            }
        }
    }

    @Test
    fun testFaultbackThresholdChange()
    {
        activityRule.scenario.onActivity {
            if(!it.sensorPresent)
            {
                it.notifyLocationUpdate(createMockLocation())
                Thread.sleep(2500)
                val steps_before = it.stepsTaken
                it.notifyLocationUpdate(createMockLocation2())
                Thread.sleep(2500)
                assertTrue(steps_before != it.stepsTaken)
            }
        }
    }

    @Test
    fun testStepsCalculatedCorrect(){
        activityRule.scenario.onActivity {
            assertEquals(it.calculateSteps(10.0f), 14)
        }
    }

    @Test
    fun gpsDataAvailable(){
        activityRule.scenario.onActivity { if(!it.sensorPresent) {
            assertEquals(it.getLocationService().javaClass, HikerLocationService::class.java)
            }
        }
    }


    private fun mockFiveSteps() {
        val mockEvent  : SensorEvent = createMockStepEvent(1)
        for(i in 1..5)
        {
            activityRule.scenario.onActivity { it.onSensorChanged(mockEvent)}
        }
    }


    private fun mockStepsWithTimeStamp() {
        val mockEvent  : SensorEvent = createMockStepWithTimeStamp(1, day)
        for(i in 1..5)
        {
            activityRule.scenario.onActivity { it.onSensorChanged(mockEvent)}
        }
    }

    private fun mockStepsWithTimeStampNow() {
        val mockEvent  : SensorEvent = createMockStepWithTimeStamp(1, Calendar.getInstance().timeInMillis)

        for(i in 1..5)
        {
            activityRule.scenario.onActivity { it.onSensorChanged(mockEvent)}
        }
    }

    @After
    fun cleanUp() {
        Intents.release()
    }

    private fun createMockStepEvent(step : Int) : SensorEvent{
        val mockEvent : SensorEvent = Mockito.mock(SensorEvent::class.java)
        val float_array = FloatArray(3)

        float_array[0] = step.toFloat()

        val valuesField: Field = SensorEvent::class.java.getField("values")
        valuesField.setAccessible(true)

        valuesField.set(mockEvent, float_array)

        return mockEvent
    }

    private fun createMockStepWithTimeStamp(step : Int, time_stamp: Long) : SensorEvent{
        val mockEvent : SensorEvent = Mockito.mock(SensorEvent::class.java)
        val float_array = FloatArray(3)

        float_array[0] = step.toFloat()

        val valuesField: Field = SensorEvent::class.java.getField("values")
        valuesField.setAccessible(true)

        val tsField: Field = SensorEvent::class.java.getField("timestamp")
        valuesField.setAccessible(true)

        valuesField.set(mockEvent, float_array)
        tsField.set(mockEvent,time_stamp)
        return mockEvent
    }

    private fun createMockLocation() : Location {
        val mockLocation = Location(LocationManager.GPS_PROVIDER)
        mockLocation.latitude = 10.0
        mockLocation.longitude = 10.0
        mockLocation.accuracy = 1.0f
        mockLocation.altitude = 123.12
        mockLocation.time = System.currentTimeMillis()
        mockLocation.elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()

        return mockLocation
    }

    private fun createMockLocation2() : Location {
        val mockLocation = Location(LocationManager.GPS_PROVIDER)
        mockLocation.latitude = 10.01
        mockLocation.longitude = 10.03
        mockLocation.accuracy = 1.0f
        mockLocation.altitude = 123.12
        mockLocation.time = System.currentTimeMillis()
        mockLocation.elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()

        return mockLocation
    }
}
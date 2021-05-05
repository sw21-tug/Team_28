package com.team28.thehiker

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
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
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import java.lang.reflect.Field


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

    @Mock
    private var sensorManager:SensorManager? = null


    @Before
    fun setUp() {
        Intents.init()
        activityRule.scenario.onActivity{sensorManager = it.getSystemService(Context.SENSOR_SERVICE)
                as SensorManager}
    }

    @Test
    fun onViewComponents(){
        onView(withId(R.id.txtViewPedometer)).check(matches(withText("Pedometer")))
        onView(withId(R.id.iconPedometer)).check(matches(isDisplayed()))
        onView(withId(R.id.txtViewSteps)).check(matches(isDisplayed()))
    }

    @Test
    fun AddSteps(){
        activityRule.scenario.onActivity { it.updateStepCounter(7) }
        onView(withId(R.id.txtViewSteps)).check(matches(withText("7")))
    }

    @Test
    fun callSensorTest(){
        var mockEvent = createMockStepEvent(54)
        activityRule.scenario.onActivity { it.onSensorChanged(mockEvent)}
        onView(withId(R.id.txtViewSteps)).check(matches(withText((mockEvent.values[0].toInt()).toString())))
    }

    @After
    fun cleanUp() {
        Intents.release()
    }

    private fun getMockStepSensor() : Sensor {
        val mockSensor : Sensor = Mockito.mock(Sensor::class.java)
        Mockito.`when`(mockSensor.type).thenReturn(Sensor.TYPE_STEP_DETECTOR)
        return mockSensor
    }

    private fun getMockNoStepSensor() : Sensor {
        val mockSensor : Sensor = Mockito.mock(Sensor::class.java)
        Mockito.`when`(mockSensor.type).thenReturn(null)
        return mockSensor
    }

    private fun getTypeMotionDetectSensor() : Sensor {
        val mockSensor : Sensor = Mockito.mock(Sensor::class.java)
        Mockito.`when`(mockSensor.type).thenReturn(Sensor.TYPE_MOTION_DETECT)
        return mockSensor
    }

    private fun createMockStepEvent(step : Int) : SensorEvent{
        var mockEvent : SensorEvent = Mockito.mock(SensorEvent::class.java)
        var float_array = FloatArray(3)

        float_array[0] = step.toFloat()

        val valuesField: Field = SensorEvent::class.java.getField("values")
        valuesField.setAccessible(true)

        valuesField.set(mockEvent, float_array)
        return mockEvent
    }

}

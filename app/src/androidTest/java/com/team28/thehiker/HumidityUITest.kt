package com.team28.thehiker

import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class HumidityUITest {

    @get:Rule
    var activityRule = ActivityScenarioRule(HumidityActivity::class.java)

    @Test
    fun testUpdateHumidity(){
        val sensorEvent = mockSensorEvent(50.2f)

        activityRule.scenario.onActivity { it.onSensorChanged(sensorEvent) }
        onView(withId(R.id.humidity))
                .check(ViewAssertions.matches(ViewMatchers.withText("50.2")))
    }

    fun mockSensorEvent(humidity : Float) : SensorEvent{
        val sensorEvent : SensorEvent = Mockito.mock(SensorEvent::class.java)
        val field = SensorEvent::class.java.getField("values")
        field.isAccessible = true

        val floatArray = FloatArray(1)
        floatArray[0] = humidity
        field.set(sensorEvent, floatArray)
        return sensorEvent
    }
}
package com.team28.thehiker

import android.content.pm.PackageManager
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

/*    @Mock
    private lateinit var sensorManager: SensorManager

    @Mock
    private  lateinit var packetManager : PackageManager
*/
    @Before
    fun setUp() {
        Intents.init()
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

    @After
    fun cleanUp() {
        Intents.release()
    }
}

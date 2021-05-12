package com.team28.thehiker

import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.times
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.team28.thehiker.Permissions.PermissionHandler
import org.hamcrest.CoreMatchers.not
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito


@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {

    @Mock
    var tempWrapper = Mockito.mock(TemperatureWrapper::class.java)

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @Test
    fun button_altitude() {
        onView(withId(R.id.btn_altitude))
            .check(matches(isClickable()))

        onView(withId(R.id.btn_altitude))
            .check(matches(withText("Altitude")))
    }

    @Test
    fun button_positionOnMap() {
        onView(withId(R.id.btn_position_on_map))
            .check(matches(isClickable()))

        onView(withId(R.id.btn_position_on_map))
            .check(matches(withText("Find me")))
    }

    @Test
    fun check_listview_containsItems() {
        onView(withId(R.id.scrollview_menu))
            .check(matches(hasDescendant(withId(R.id.btn_altitude))))

        onView(withId(R.id.scrollview_menu))
                .check(matches(hasDescendant(withId(R.id.btn_position_on_map))))
    }

    @Test
    fun onButtonClick_Altitude_correctActivityStarted() {
        onView(withId(R.id.btn_altitude))
            .perform(click())

        Intents.intended(hasComponent(AltitudeActivity::class.java.name), times(1))
    }

    @Test
    fun onButtonClick_FindMe_correctActivityStarted() {
        onView(withId(R.id.btn_position_on_map))
            .perform(click())

        //TODO: this can be changed to real activities when implemented
        Intents.intended(hasComponent(TestActivity::class.java.name), times(1))
    }

    @After
    fun cleanUp() {
        Intents.release()
    }

    @Test
    fun buttonTemperatureIsAvailable() {


        Mockito.`when`(tempWrapper.isTemperatureSensorAvailable()).thenReturn(true)
        activityRule.scenario.onActivity {
            it.temperatureWrapper = tempWrapper
        }

        onView(withId(R.id.btn_temperature))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btn_temperature))
            .check(matches(isClickable()))


        onView(withId(R.id.btn_temperature))
            .check(matches(withText("Temperature")))
    }

    @Test
    fun button_TemperatureIsNotAvailable() {


        Mockito.`when`(tempWrapper.isTemperatureSensorAvailable()).thenReturn(false)
        activityRule.scenario.onActivity {
            it.temperatureWrapper = tempWrapper
        }

        onView(withId(R.id.btn_temperature))
            .check(matches(not(isDisplayed())))

    }
}
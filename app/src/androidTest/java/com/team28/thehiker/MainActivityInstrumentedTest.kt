package com.team28.thehiker

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.times
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.hamcrest.CoreMatchers.not


@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {
    @Mock
    var humWrapper = Mockito.mock(HumidityWrapper::class.java)

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @Test
    fun button_humidity() {
        onView(withId(R.id.btn_humidity))
            .check(matches(isClickable()))

        onView(withId(R.id.btn_humidity))
            .check(matches(withText("Humidity")))
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
        Intents.intended(hasComponent(FindMeActivity::class.java.name), times(1))
    }


    @Test
    fun button_HumidityIsNotAvailable() {
        Mockito.`when`(humWrapper.isHumiditySensorAvailable()).thenReturn(false)
        activityRule.scenario.onActivity {
            it.humidityWrapper = humWrapper
            it.decidedButtonHumidityShown()
        }

        onView(withId(R.id.btn_humidity))
            .check(matches(not(isDisplayed())))

    }
    @Test
    fun buttonHumidityStartsCorrectActivity(){
        Mockito.`when`(humWrapper.isHumiditySensorAvailable()).thenReturn(true)
        activityRule.scenario.onActivity {
            it.humidityWrapper = humWrapper
            it.decidedButtonHumidityShown()
        }

        onView(withId(R.id.btn_humidity))
            .perform(click())

        Intents.intended(hasComponent(HumidityActivity::class.java.name), times(1))

    }



    @After
    fun cleanUp() {
        Intents.release()
    }
}
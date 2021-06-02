package com.team28.thehiker


import android.os.Bundle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.times
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.team28.thehiker.features.altitude.AltitudeActivity
import com.team28.thehiker.features.findme.FindMeActivity
import com.team28.thehiker.features.humidity.HumidityActivity
import com.team28.thehiker.features.humidity.HumidityWrapper
import com.team28.thehiker.features.pedometer.PedometerActivity
import com.team28.thehiker.features.temperature.TemperatureActivity
import com.team28.thehiker.features.temperature.TemperatureWrapper
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito



@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {
    @Mock
    var humWrapper = Mockito.mock(HumidityWrapper::class.java)


    private val TEMP_TEST_VALUE = 22.2

    @Mock
    var tempWrapper = Mockito.mock(TemperatureWrapper::class.java)

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule<MainActivity>(MainActivity::class.java)

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
    fun button_pedometer() {
        onView(withId(R.id.btn_pedometer))
            .check(matches(isClickable()))

        onView(withId(R.id.btn_pedometer))
            .check(matches(withText("Pedometer")))
    }

    @Test
    fun check_listview_containsItems() {
        onView(withId(R.id.scrollview_menu))
            .check(matches(hasDescendant(withId(R.id.btn_altitude))))

        onView(withId(R.id.scrollview_menu))
                .check(matches(hasDescendant(withId(R.id.btn_position_on_map))))

        onView(withId(R.id.scrollview_menu))
            .check(matches(hasDescendant(withId(R.id.btn_pedometer))))
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

    @Test
    fun onButtonClick_Pedometer_correctActivityStarted() {
        onView(withId(R.id.btn_pedometer))
            .perform(click())
        Intents.intended(hasComponent(PedometerActivity::class.java.name), times(1))
    }

    @Test
    fun onButtonClick_SOS_NoNumbers_AlertDialogShown() { //TODO sos button and alert dialog instead of pedometer btn
        onView(withId(R.id.btn_pedometer))
                .perform(click())
        onView(withId(R.id.btn_pedometer)).check(matches(isDisplayed()))
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
            it.decidedButtonsShown()
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
            it.decidedButtonsShown()
        }

        onView(withId(R.id.btn_temperature))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun buttonTemperatureCorrectIntent() {
        Mockito.`when`(tempWrapper.isTemperatureSensorAvailable()).thenReturn(true)
        Mockito.`when`(tempWrapper.getTemperature()).thenReturn(TEMP_TEST_VALUE)
        activityRule.scenario.onActivity {
            it.temperatureWrapper = tempWrapper
            it.decidedButtonsShown()
        }

        onView(withId(R.id.btn_temperature))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btn_temperature)).perform(click())

        Intents.intended(hasComponent(TemperatureActivity::class.java.name), times(1))
        var intentFound = false
        Intents.getIntents().forEach {
            if (it.component != null && it.component!!.className == (TemperatureActivity::class.java.name)) {
                intentFound = true
                validateTemperatureExtras(it.extras)
            }
        }
        assert(intentFound)
    }

    private fun validateTemperatureExtras(extras: Bundle?) {
        assertNotNull(extras)
        assert(extras!!.containsKey(TemperatureActivity.TEMP_KEY))
        assert(extras.getDouble(TemperatureActivity.TEMP_KEY).equals(TEMP_TEST_VALUE))
    }

}
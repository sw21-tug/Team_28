package com.team28.thehiker


import android.os.Bundle
import android.widget.ImageView
import android.widget.ScrollView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
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
import com.team28.thehiker.features.sosmessage.SosMessageActivity
import com.team28.thehiker.features.temperature.TemperatureActivity
import com.team28.thehiker.features.temperature.TemperatureWrapper
import com.team28.thehiker.sharedpreferencehandler.SharedPreferenceHandler
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock


@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {
    @Mock
    var humWrapper = mock(HumidityWrapper::class.java)


    private val TEMP_TEST_VALUE = 22.2

    @Mock
    var tempWrapper = mock(TemperatureWrapper::class.java)

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
    fun button_sos() {
        onView(withId(R.id.btn_sos))
            .check(matches(isClickable()))

        onView(withId(R.id.btn_sos))
            .check(matches(withText("SOS message")))
    }

    @Test
    fun check_listview_containsItems() {
        onView(withId(R.id.scrollview_menu))
            .check(matches(hasDescendant(withId(R.id.btn_altitude))))

        onView(withId(R.id.scrollview_menu))
            .check(matches(hasDescendant(withId(R.id.btn_position_on_map))))

        onView(withId(R.id.scrollview_menu))
            .check(matches(hasDescendant(withId(R.id.btn_pedometer))))

        onView(withId(R.id.scrollview_menu))
            .check(matches(hasDescendant(withId(R.id.btn_sos))))
    }

    @Test
    fun onButtonClick_Altitude_correctActivityStarted() {
        onView(withId(R.id.btn_altitude))
            .perform(scrollTo())
            .perform(click())

        Intents.intended(hasComponent(AltitudeActivity::class.java.name), times(1))
    }

    @Test
    fun onButtonClick_FindMe_correctActivityStarted() {
        onView(withId(R.id.btn_position_on_map))
            .perform(scrollTo())
            .perform(click())

        Intents.intended(hasComponent(FindMeActivity::class.java.name), times(1))
    }

    @Test
    fun button_HumidityIsNotAvailable() {
        Mockito.`when`(humWrapper.isHumiditySensorAvailable()).thenReturn(false)
        activityRule.scenario.onActivity {
            it.humidityWrapper = humWrapper
            it.decideButtonShown(R.id.ll_humidity)
        }

        onView(withId(R.id.btn_humidity))
            .check(matches(not(isDisplayed())))

    }
    @Test
    fun buttonHumidityStartsCorrectActivity(){
        Mockito.`when`(humWrapper.isHumiditySensorAvailable()).thenReturn(true)
        activityRule.scenario.onActivity {
            it.humidityWrapper = humWrapper
            it.decideButtonShown(R.id.ll_humidity)
        }

        onView(withId(R.id.btn_humidity))
            .perform(scrollTo())
            .perform(click())

        Intents.intended(hasComponent(HumidityActivity::class.java.name), times(1))
    }

    @Test
    fun onButtonClick_Pedometer_correctActivityStarted() {
        onView(withId(R.id.btn_pedometer))
            .perform(scrollTo())
            .perform(click())
        Intents.intended(hasComponent(PedometerActivity::class.java.name), times(1))
    }

    //This test needs a new installation to test the alert dialog, will fail otherwise
    @Test
    fun onButtonClick_SOS_correctActivityStarted() {
        onView(withId(R.id.scrollview_menu))
                .perform(swipeUp())

        onView(withId(R.id.btn_sos))
            .perform(click())

        onView(withId(R.id.phonenumber1)).perform(replaceText("00005555"))
        onView(withId(R.id.phonenumber2)).perform(replaceText("00005555"))

        onView(withText("Save")).perform(click())

        Intents.intended(hasComponent(SosMessageActivity::class.java.name), times(1))
    }

    @Test
    fun button_SpeedOfMoving() {
        onView(withId(R.id.btn_speed_of_moving))
            .check(matches(isClickable()))

        onView(withId(R.id.btn_speed_of_moving))
            .check(matches(withText("Speed")))
    }


    @After
    fun cleanUp() {
        Intents.release()
    }

    @Test
    fun buttonTemperatureIsAvailable() {
        `when`(tempWrapper.isTemperatureSensorAvailable()).thenReturn(true)
        activityRule.scenario.onActivity {
            it.temperatureWrapper = tempWrapper
            it.decideButtonShown(R.id.ll_temperature)
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
            it.decideButtonShown(R.id.ll_temperature)
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
            it.decideButtonShown(R.id.ll_humidity)
        }

        onView(withId(R.id.btn_temperature))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btn_temperature))
            .perform(scrollTo())
            .perform(click())

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

    @Test

    fun validateScrollViewHasCorrectHeight () {
        onView(withId(R.id.main_image))
            .check(matches(isDisplayed()))
        onView(withId(R.id.scrollview_menu))
            .check(matches(isDisplayed()))

        activityRule.scenario.onActivity {
            val imageView = it.findViewById<ImageView>(R.id.main_image)
            val screenHeight = it.resources.displayMetrics.heightPixels
            val scrollView = it.findViewById<ScrollView>(R.id.scrollview_menu)
            assert(screenHeight==(imageView.bottom + scrollView.height))
        }

    }



    private fun validateTemperatureExtras(extras: Bundle?) {
        assertNotNull(extras)
        assert(extras!!.containsKey(TemperatureActivity.TEMP_KEY))
        assert(extras.getDouble(TemperatureActivity.TEMP_KEY).equals(TEMP_TEST_VALUE))
    }

}
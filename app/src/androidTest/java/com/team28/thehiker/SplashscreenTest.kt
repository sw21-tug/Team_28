package com.team28.thehiker

import android.os.SystemClock
import androidx.appcompat.widget.ActivityChooserView
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.LayoutMatchers
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.times
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.sql.Time
import java.time.Clock
import java.time.LocalTime
import java.time.Period
import java.util.*
import javax.inject.Inject
import kotlin.time.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SplashscreenTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.team28.thehiker", appContext.packageName)
    }
    @get:Rule
    var activityRule: ActivityScenarioRule<SplashscreenActivity>
            = ActivityScenarioRule(SplashscreenActivity::class.java)

    @Before
    fun whenAppLaunches(){
        Intents.init()
    }

    @Test
    fun splashscreenComponentsPresent() {
        onView(withId(R.id.imageViewSplashscreen)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewAppName)).check(matches(isDisplayed()))
    }

    @Test
    fun measureDisplayTime() {
        Thread.sleep(1600)
        Intents.intended(hasComponent(MainActivity::class.java.name), times(1))
    }

    @After
    fun cleanUp(){
        Intents.release()
    }

}
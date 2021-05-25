package com.team28.thehiker

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.team28.thehiker.features.altitude.AltitudeActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AltitudeUITest {

    @get:Rule
    var activityRule = ActivityScenarioRule(AltitudeActivity::class.java)

    @Test
    fun testValueUpdateWithZero(){
        activityRule.scenario.onActivity { it.updateAltitude(0.0) }
        onView(withId(R.id.altitude)).check(matches(withText("0.00 m")))
    }

    @Test
    fun testValueUpdateWithPositiveNumber(){
        activityRule.scenario.onActivity { it.updateAltitude(122.0) }
        onView(withId(R.id.altitude)).check(matches(withText("122.00 m")))
    }

    @Test
    fun testValueUpdateWithNegativeNumber(){
        activityRule.scenario.onActivity { it.updateAltitude(-12.43) }
        onView(withId(R.id.altitude)).check(matches(withText("-12.43 m")))
    }

}
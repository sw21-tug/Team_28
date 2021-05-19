package com.team28.thehiker

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SpeedUITest {

    @get:Rule
    var activityRule = ActivityScenarioRule(SpeedActivity::class.java)

    @Test
    fun testValueUpdateWithZero(){
        activityRule.scenario.onActivity { it.updateSpeed(0.0) }
        onView(withId(R.id.speed)).check(matches(withText("0.00 km/h")))
    }

    @Test
    fun testValueUpdateWithPositiveNumber(){
        activityRule.scenario.onActivity { it.updateSpeed(6.0) }
        onView(withId(R.id.speed)).check(matches(withText("6.00 km/h")))
    }
}
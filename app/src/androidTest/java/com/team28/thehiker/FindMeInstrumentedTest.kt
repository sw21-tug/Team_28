package com.team28.thehiker

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class FindMeInstrumentedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

    @Test
    fun return_from_findMe() {
        onView(withId(R.id.btn_position_on_map)).perform(click())
        onView(withId(R.id.mapView)).check(matches(isDisplayed()))
        Espresso.pressBack()
        onView(withId(R.id.btn_altitude)).check(matches(isClickable()))
    }

    @Test
    fun all_views_visible() {
        onView(withId(R.id.btn_position_on_map)).perform(click())
        onView(withId(R.id.mapView)).check(matches(isDisplayed()))
        onView(withId(R.id.my_location_string)).check(matches(isDisplayed()))
        onView(withId(R.id.map_pic)).check(matches(isDisplayed()))
    }
}
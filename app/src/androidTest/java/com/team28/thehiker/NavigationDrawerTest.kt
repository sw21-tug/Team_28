package com.team28.thehiker

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationDrawerTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @Test
    fun open_and_close_navigation_drawer()
    {
        onView(withId(R.id.english)).check(matches(not(isDisplayed())))
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
        onView(withId(R.id.english)).check(matches(isDisplayed()))
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.close())
        onView(withId(R.id.english)).check(matches(not(isDisplayed())))
    }

    @After
    fun cleanUp() {
        Intents.release()
    }
}
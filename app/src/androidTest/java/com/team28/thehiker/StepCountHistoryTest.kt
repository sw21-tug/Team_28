package com.team28.thehiker

import android.view.View
import android.widget.ListView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.team28.thehiker.matchers.ListViewMatcher
import junit.framework.Assert.assertTrue
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class StepCountHistoryTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @Test
    fun checkOldStepHistory() {
        //check the history of the last 20 days(?)

        onView(withId(R.id.btn_pedometer)).perform(click())
        onView(withId(R.id.button_history))
            .check(matches(isClickable()))

        onView(withId(R.id.button_history))
            .check(matches(withText("History")))
    }

    @Test
    fun checkHistoryDisplayed(){
        // make sure the history is displayed correctly
    }

    @Test
    fun checkHistorySaved(){
        // check if step count history is saved when closing the app
    }

    @Test

    fun checkItems(){

        onView(withId(R.id.btn_pedometer)).perform(click())
        onView(withId(R.id.button_history)).perform(click())

        onView (withId (R.id.step_list)).check (ViewAssertions.matches (ListViewMatcher (20)));

    }





    @After
    fun cleanUp() {
        Intents.release()
    }
}
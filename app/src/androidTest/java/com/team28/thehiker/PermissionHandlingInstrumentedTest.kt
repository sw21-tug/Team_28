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
class PermissionHandlingInstrumentedTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(AltitudeActivity::class.java)

    @Test
    fun testValueUpdateWithZero(){

        return
    }



}
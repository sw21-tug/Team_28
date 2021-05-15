package com.team28.thehiker

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class TemperatureUITest {

    @get:Rule
    var activityRule = ActivityScenarioRule(TemperatureActivity::class.java)

    @Test
    fun testValueUpdateWithZero(){
        activityRule.scenario.onActivity { it.updateTemperature(0.0) }
        Espresso.onView(ViewMatchers.withId(R.id.temperature))
            .check(ViewAssertions.matches(ViewMatchers.withText("0.0 °C")))
    }

    @Test
    fun testValueUpdateWithPositiveNumber(){
        activityRule.scenario.onActivity { it.updateTemperature(38.1) }
        Espresso.onView(ViewMatchers.withId(R.id.temperature))
            .check(ViewAssertions.matches(ViewMatchers.withText("38.1 °C")))
    }

    @Test
    fun testValueUpdateWithNegativeNumber(){
        activityRule.scenario.onActivity { it.updateTemperature(-12.43) }
        Espresso.onView(ViewMatchers.withId(R.id.temperature))
            .check(ViewAssertions.matches(ViewMatchers.withText("-12.4 °C")))
    }

}
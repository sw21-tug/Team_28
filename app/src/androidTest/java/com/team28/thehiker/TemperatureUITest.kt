package com.team28.thehiker

import android.content.Intent
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.team28.thehiker.features.temperature.TemperatureActivity
import org.junit.Assert
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

    @Test
    fun setsUIValueToProvidedExtra(){
        val intent = Intent(ApplicationProvider.getApplicationContext(), TemperatureActivity::class.java)
        intent.putExtra(TemperatureActivity.TEMP_KEY, 123.456)

        val scenario = ActivityScenario.launch<TemperatureActivity>(intent)
        lateinit var temperatureString : String
        scenario.onActivity {
            temperatureString = it.findViewById<TextView>(R.id.temperature).text as String
        }

        Assert.assertTrue("123.5 °C" == temperatureString)
    }

}
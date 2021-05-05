package com.team28.thehiker

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.team28.thehiker.SharedPreferenceHandler.SharedPreferenceHandler
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LanguageIntegrationTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun switchLanguageToRU() {
        switchLanguage(R.string.russian, "ru", "ru-RU")
    }

    @Test
    fun switchLanguageToEN() {
        switchLanguage(R.string.english, "en", "en-EN")
    }

    fun switchLanguage(languageStringID : Int, sharedPreferenceTag : String, languageTag : String) {
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
        onView(withId(R.id.language)).perform(ViewActions.click())
        onView(withText(languageStringID)).inRoot(RootMatchers.isPlatformPopup()).perform(ViewActions.click())

        lateinit var altitudeLabel : String
        lateinit var findMeLabel : String
        activityRule.scenario.onActivity {
            val sharedPreferenceHandler = SharedPreferenceHandler()
            Assert.assertEquals(sharedPreferenceHandler.getLocalizationString(it), sharedPreferenceTag)
            Assert.assertEquals(it.resources.configuration.locales[0].toLanguageTag(), languageTag)
            altitudeLabel = it.resources.getString(R.string.altitude)
            findMeLabel = it.resources.getString(R.string.find_me)
        }

        onView(withId(R.id.btn_altitude)).check(matches(withText(altitudeLabel)))
        onView(withId(R.id.btn_position_on_map)).check(matches(withText(findMeLabel)))
    }

}
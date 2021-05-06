package com.team28.thehiker

import android.app.ActivityManager
import android.content.Context
import android.util.Log
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.team28.thehiker.SharedPreferenceHandler.SharedPreferenceHandler
import com.team28.thehiker.language.LanguageSelector
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.StringBuilder
import java.util.*

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

    @Test
    fun checkLanguageSwitchPersistsAppRestart(){
        //ensure app is in russian language
        switchLanguage(R.string.russian, "ru", "ru-RU")

        //close the app
        activityRule.scenario.onActivity {
            //mocking an app reset this way since we didn't find a better way to fully kill the app during testing
            //this just updates the current configuration but not the shared preferences we want to test
            LanguageSelector.setLocaleToEnglish(it)
        }
        activityRule.scenario.close()


        // In the SplashscreenActivity the app should reload the shared preferences and update the locale config
        val scenario = ActivityScenario.launch(SplashscreenActivity::class.java)
        scenario.onActivity {
            val sharedPreferenceHandler = SharedPreferenceHandler()
            Assert.assertEquals("ru", sharedPreferenceHandler.getLocalizationString(it))
            Assert.assertEquals("ru-RU", it.resources.configuration.locales[0].toLanguageTag())
        }
    }

    private fun switchLanguage(languageStringID : Int, sharedPreferenceTag : String, languageTag : String) {
        onView(withId(R.id.drawerLayout)).perform(DrawerActions.open())
        onView(withId(R.id.language)).perform(ViewActions.click())
        onView(withText(languageStringID)).inRoot(RootMatchers.isPlatformPopup()).perform(ViewActions.click())

        //wait for the ui to update
        Thread.sleep(1000)

        lateinit var altitudeLabel : String
        lateinit var findMeLabel : String
        activityRule.scenario.onActivity {
            val sharedPreferenceHandler = SharedPreferenceHandler()
            Assert.assertEquals(sharedPreferenceTag, sharedPreferenceHandler.getLocalizationString(it))
            Assert.assertEquals(languageTag, it.resources.configuration.locales[0].toLanguageTag())
            altitudeLabel = it.resources.getString(R.string.altitude)
            findMeLabel = it.resources.getString(R.string.find_me)
        }

        onView(withId(R.id.btn_altitude)).check(matches(withText(altitudeLabel)))
        onView(withId(R.id.btn_position_on_map)).check(matches(withText(findMeLabel)))
    }

}
package com.team28.thehiker

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.team28.thehiker.language.LanguageSelector
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LanguageSelectionTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testLanguageSwitchToRussian(){

        activityRule.scenario.onActivity {
            LanguageSelector.setLocaleToRussian(it)

            Assert.assertEquals(it.getString(R.string.altitude),"высота")
            Assert.assertEquals(it.getString(R.string.find_me),"Найди меня")
            //The brand name shouldn't be translated
            Assert.assertEquals(it.getString(R.string.app_title),"The Hiker")
        }
    }

    @Test
    fun testLanguageSwitchToEnglish(){
        activityRule.scenario.onActivity {
            // before test
            LanguageSelector.setLocaleToRussian(it)
            Assert.assertEquals(it.getString(R.string.altitude),"высота")

            // actual test
            LanguageSelector.setLocaleToEnglish(it)

            Assert.assertEquals(it.getString(R.string.altitude),"Altitude")
            Assert.assertEquals(it.getString(R.string.find_me),"Find me")
            Assert.assertEquals(it.getString(R.string.app_title),"The Hiker")
        }
    }


}
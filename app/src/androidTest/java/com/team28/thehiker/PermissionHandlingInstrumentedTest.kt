package com.team28.thehiker

import android.os.Build
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PermissionHandlingInstrumentedTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

    fun grantPermission() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val allowPermission = UiDevice.getInstance(instrumentation).findObject(UiSelector().text(
                when {
                    Build.VERSION.SDK_INT == 23 -> "Allow"
                    Build.VERSION.SDK_INT <= 28 -> "ALLOW"
                    Build.VERSION.SDK_INT == 29 -> "Allow only while using the app"
                    else -> "While using the app"
                }))
        if(allowPermission.exists())
            allowPermission.click()
    }

    fun denyPermission() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val denyPermission = UiDevice.getInstance(instrumentation).findObject(UiSelector().text("Deny"))
        if(denyPermission.exists())
            denyPermission.click()
    }



    @Test
    fun a_testMainScreenWithoutPermission(){
        denyPermission()
        onView(withId(R.id.btn_altitude))
                .check(ViewAssertions.matches(ViewMatchers.withText("Altitude")))
        return
    }

    @Test
    fun b_tesMainScreenWithoutPermissionBtnCheck() {
        denyPermission()
        onView(withId(R.id.btn_altitude))
                .check(ViewAssertions.matches(ViewMatchers.withText("Altitude")))

        onView(withId(R.id.btn_altitude)).perform(ViewActions.click())
        denyPermission()
        onView(withId(R.id.btn_position_on_map)).check(ViewAssertions.matches(ViewMatchers.withText("Find me")))

        onView(withId(R.id.btn_position_on_map)).perform(ViewActions.click())
        denyPermission()
        onView(withId(R.id.btn_altitude)).check(ViewAssertions.matches(ViewMatchers.withText("Altitude")))
        return
    }

    @Test
    fun c_testLatePermission() {
        denyPermission()
        onView(withId(R.id.btn_altitude)).check(ViewAssertions.matches(ViewMatchers.withText("Altitude")))

        onView(withId(R.id.btn_altitude)).perform(ViewActions.click())
        grantPermission()
        onView(withId(R.id.btn_position_on_map)).check(ViewAssertions.matches(ViewMatchers.withText("Find me")))
        onView(withId(R.id.btn_altitude)).perform(ViewActions.click())
        onView(withId(R.id.altitude)).check(ViewAssertions.matches(ViewMatchers.withText("0.00 m")))
        return
    }
}
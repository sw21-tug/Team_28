package com.team28.thehiker

import android.os.Build
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
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

    /*    ¡¡¡¡¡¡¡¡¡¡¡ WARNING !!!!!!!!!!!
    In order to run these tests the all permission, except the LOCATION, have to be granted.
    The tests have to be run in alphabetical order

    - Install app on device
    - allow permissions except location permission
    - run a_ testcase
    - Hide the location permission request window "never ask again"
    - run b_ testcase

    (not properly working on Android 30, because showRational changed again)
    */

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
        val denyPermission = UiDevice.getInstance(instrumentation).findObject(UiSelector().text(
                when {
                   Build.VERSION.SDK_INT == 23 -> "Deny"
                   Build.VERSION.SDK_INT <= 28 -> "DENY"
                   Build.VERSION.SDK_INT == 29 -> "Deny"
                   else -> "Deny"
                }))
        if(denyPermission.exists())
            denyPermission.click()
    }

    @Test
    fun a_testMainScreenWithoutPermission(){
        denyPermission()
        onView(withId(R.id.btn_altitude))
                .check(ViewAssertions.matches(withText("Altitude")))
        return
    }

    @Test
    fun b_testMainScreenWithoutPermissionBtnCheck() {
        denyPermission()
        onView(withId(R.id.btn_altitude))
                .check(ViewAssertions.matches(withText("Altitude")))
        onView(withId(R.id.btn_altitude)).perform(ViewActions.click())
        onView(withText("Permission Alert")).check(ViewAssertions.matches(withText("Permission Alert")))
        return
    }
}
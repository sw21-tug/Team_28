package com.team28.thehiker

import android.location.Location
import android.location.LocationManager
import android.os.SystemClock
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.team28.thehiker.features.sosmessage.SMSWrapper
import com.team28.thehiker.features.sosmessage.SosMessageActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class SosActivityTest {

    /**
     * IMPORTANT: you need to set these apps as mock location provider
     *      in the developer settings for these test cases to work!!!
     *      e.g. enable Developer Settings -> enter them -> find mock location provider setting under debugging
     *
     *      ALSO IMPORTANT: don't forget to set the mock location provider to None afterwards!!!
     *      Otherwise the app won't work normally
     */

    @get:Rule
    var activityRule = ActivityScenarioRule(SosMessageActivity::class.java)

    @Captor
    private lateinit var locationCaptor: ArgumentCaptor<Location>



    @Before
    fun init(){
        //initialize mock objects and captors
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun verifyStartsSendingUpdates(){
        val mockSmsWrapper = mock(SMSWrapper::class.java)

        //mock location
        Thread.sleep(1000) // wait for service to start
        val mockLocation1 = createMockLocation(22.45)

        activityRule.scenario.onActivity { activity ->
            activity.injectWrapper(mockSmsWrapper)
            val service = activity.getLocationService()
            service!!.getLocationProvider().setMockMode(true).addOnFailureListener { throw it }
            service.getLocationProvider().setMockLocation(mockLocation1).addOnFailureListener { throw it }
        }

        Thread.sleep(2500) // wait for location update to arrive

        verify(mockSmsWrapper, atLeastOnce()).notifyLocationUpdate(capture(locationCaptor))
    }

    @Test
    fun verifyStops(){
        val mockSmsWrapper = mock(SMSWrapper::class.java)

        activityRule.scenario.onActivity { it.injectWrapper(mockSmsWrapper)}

        onView(ViewMatchers.withId(R.id.stop_sos_btn)).perform(click())
        verify(mockSmsWrapper, atLeastOnce()).stop()
    }

    private fun createMockLocation(altitude : Double) : Location {
        val mockLocation = Location(LocationManager.GPS_PROVIDER)
        mockLocation.latitude = 10.0
        mockLocation.longitude = 10.0
        mockLocation.accuracy = 1.0f
        mockLocation.altitude = altitude
        mockLocation.time = System.currentTimeMillis()
        mockLocation.elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()

        return mockLocation
    }

    //workaround as argumentCaptor.capture() returns null but kotlin does not allow for that
    //source: https://stackoverflow.com/a/46064204
    private fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()
}
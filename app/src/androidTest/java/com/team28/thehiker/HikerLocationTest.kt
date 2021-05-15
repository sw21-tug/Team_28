package com.team28.thehiker

import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.SystemClock
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ServiceTestRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@LargeTest
@RunWith(AndroidJUnit4::class)
class HikerLocationTest {

    /**
     * IMPORTANT: you need to set this app as mock location provider
     *      in the developer settings for these test cases to work!!!
     *      e.g. enable Developer Settings -> enter them -> find mock location provider setting under debugging
     *
     *      ALSO IMPORTANT: don't forget to set the mock location provider to None afterwards!!!
     *      Otherwise the app won't work normally
     */

    private val DOUBLE_COMPARE_DELTA = 0.0001
    private val LOCATION_UPDATE_WAIT_TIME = 3000L


    @get:Rule
    var serviceRule = ServiceTestRule()

    @Mock
    private lateinit var mockCallbackInterface : HikerLocationCallback

    @Captor
    private lateinit var locationCaptor: ArgumentCaptor<Location>

    private lateinit var service : HikerLocationService

    @Before
    fun init(){ //setup that is executed every time a testcase is executed

        //initialize mock objects and captors
        MockitoAnnotations.initMocks(this)

        // bind service and retrieve service object from binder
        val binder = serviceRule.bindService(Intent(ApplicationProvider.getApplicationContext(),HikerLocationService::class.java))
        service = (binder as HikerLocationService.HikerLocationBinder).getService()

        //add location callback
        service.addLocationCallback(mockCallbackInterface)

        //enable mocking
        service.getLocationProvider().setMockMode(true).addOnFailureListener { throw it }
    }

    @Test
    fun startSendingUpdatesAfterBindingService(){
        //mock location
        val mockLocation = createMockLocation()
        service.getLocationProvider().setMockLocation(mockLocation).addOnFailureListener { throw it }

        // verify that the method notifyLocationUpdate() has been called at least once after 5 sec
        verify(mockCallbackInterface, after(LOCATION_UPDATE_WAIT_TIME).atLeastOnce()).notifyLocationUpdate(capture(locationCaptor))
    }

    @Test
    fun verifyLocationCorrectlyReturned(){
        //mock location
        val mockLocation = createMockLocation()
        service.getLocationProvider().setMockLocation(mockLocation).addOnFailureListener { throw it }

        verify(mockCallbackInterface, after(LOCATION_UPDATE_WAIT_TIME).atLeastOnce()).notifyLocationUpdate(capture(locationCaptor))

        assertLocationsEqual(mockLocation, locationCaptor.value)
    }

    @Test
    fun stopSendingUpdatesAfterUnbindingService(){
        //mock location
        var mockLocation = createMockLocation()
        service.getLocationProvider().setMockLocation(mockLocation).addOnFailureListener { throw it }

        // verify that location updates are arriving in the first place
        verify(mockCallbackInterface, after(LOCATION_UPDATE_WAIT_TIME).atLeastOnce()).notifyLocationUpdate(capture(locationCaptor))
        serviceRule.unbindService()

        //mock location
        mockLocation = createMockLocation()
        service.getLocationProvider().setMockLocation(mockLocation).addOnFailureListener { throw it }

        // verify that location updates are no longer arriving
        verify(mockCallbackInterface, after(LOCATION_UPDATE_WAIT_TIME).atMost(1)).notifyLocationUpdate(capture(locationCaptor))
    }

    private fun assertLocationsEqual(mockedLocation : Location, returnedLocation : Location) {
        assertEquals(mockedLocation.latitude, returnedLocation.latitude, DOUBLE_COMPARE_DELTA)
        assertEquals(mockedLocation.longitude, returnedLocation.longitude, DOUBLE_COMPARE_DELTA)
        assertEquals(mockedLocation.accuracy, returnedLocation.accuracy)
        assertEquals(mockedLocation.altitude, returnedLocation.altitude, DOUBLE_COMPARE_DELTA)
        assertEquals(mockedLocation.time, returnedLocation.time)
        assertEquals(mockedLocation.elapsedRealtimeNanos, returnedLocation.elapsedRealtimeNanos)
    }

    private fun createMockLocation() : Location{
        val mockLocation = Location(LocationManager.GPS_PROVIDER)
        mockLocation.latitude = 10.0
        mockLocation.longitude = 10.0
        mockLocation.accuracy = 1.0f
        mockLocation.altitude = 123.12
        mockLocation.time = System.currentTimeMillis()
        mockLocation.elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()

        return mockLocation
    }

    //workaround as argumentCaptor.capture() returns null but kotlin does not allow for that
    //source: https://stackoverflow.com/a/46064204
    private fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()

}
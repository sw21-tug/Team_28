package com.team28.thehiker

import com.team28.thehiker.SharedPreferenceHandler.SharedPreferenceHandler
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import java.time.LocalDate

class PedometerActivityTest {

    @Mock
    var sharedPreferenceMock = Mockito.mock(SharedPreferenceHandler::class.java)

    @Test
    fun testSharedPref_getSavedStepCountAccessed() {
        val pedometerActivity = PedometerActivity()
        pedometerActivity.sharedPreferenceHandler = sharedPreferenceMock

        pedometerActivity.getSavedStepCount()
        Mockito.verify(sharedPreferenceMock, Mockito.times(1)).getSavedStepCount(pedometerActivity)
    }

    @Test
    fun testSharedPref_setSavedStepCountAccessed() {
        val pedometerActivity = PedometerActivity()

        pedometerActivity.sharedPreferenceHandler = sharedPreferenceMock
        pedometerActivity.stepsTaken = 15
        pedometerActivity.setSavedStepCount()

        Mockito.verify(sharedPreferenceMock, Mockito.times(1))
            .setSavedStepCount(pedometerActivity, pedometerActivity.stepsTaken)
    }

    @Test
    fun testSharedPref_getLastStepCountUpdateAccessed() {
        val pedometerActivity = PedometerActivity()
        pedometerActivity.sharedPreferenceHandler = sharedPreferenceMock

        pedometerActivity.getLastStepCountUpdate()
        Mockito.verify(sharedPreferenceMock, Mockito.times(1)).getLastStepCountUpdate(pedometerActivity)
    }

    @Test
    fun testSharedPref_setLastStepCountUpdateAccessed() {
        val pedometerActivity = PedometerActivity()

        pedometerActivity.sharedPreferenceHandler = sharedPreferenceMock
        val date = LocalDate.now()
        pedometerActivity.setLastStepCountUpdate(date)

        Mockito.verify(sharedPreferenceMock, Mockito.times(1))
            .setLastStepCountUpdate(pedometerActivity, date)
    }
}
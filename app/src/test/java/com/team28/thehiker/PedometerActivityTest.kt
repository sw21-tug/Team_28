package com.team28.thehiker

import com.team28.thehiker.features.pedometer.PedometerActivity
import com.team28.thehiker.sharedpreferencehandler.SharedPreferenceHandler
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.*

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
        val calendar = Calendar.getInstance()

        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val year = calendar.get(Calendar.YEAR)


        pedometerActivity.setLastStepCountUpdate(calendar)

        Mockito.verify(sharedPreferenceMock, Mockito.times(1))
            .setLastStepCountUpdate(pedometerActivity, year.toString() + "_" + dayOfYear.toString())
    }

    @Test
    fun testNextDayReset_NoResetIfSameDay() {
        val pedometerActivity = PedometerActivity()
        pedometerActivity.sharedPreferenceHandler = sharedPreferenceMock
        pedometerActivity.stepsTaken = 42

        val calendar = Calendar.getInstance()
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val year = calendar.get(Calendar.YEAR)

        val saveAs = year.toString() + "_" + dayOfYear.toString()

        `when`(sharedPreferenceMock.getLastStepCountUpdate(pedometerActivity))
                .thenReturn(saveAs)

        pedometerActivity.checkIfNewDay()

        Assert.assertEquals(pedometerActivity.stepsTaken, 42)
    }

    @Test
    fun testNextDayReset_ResetIfNotSameDay() {
        val pedometerActivity = PedometerActivity()
        pedometerActivity.sharedPreferenceHandler = sharedPreferenceMock
        pedometerActivity.stepsTaken = 42

        val calendar = Calendar.getInstance()
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val year = calendar.get(Calendar.YEAR)

        val saveAs = year.toString() + "_" + (dayOfYear - 1).toString()

        `when`(sharedPreferenceMock.getLastStepCountUpdate(pedometerActivity))
                .thenReturn(saveAs)

        pedometerActivity.checkIfNewDay()

        Assert.assertEquals(pedometerActivity.stepsTaken, 0)
    }
}
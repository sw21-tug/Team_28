package com.team28.thehiker

import android.hardware.Sensor
import android.hardware.SensorManager
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.*


class TemperatureWrapperTest {

    @Test
    fun testIsTemperatureSensorAvailableFalse(){
        val sensorManagerTempUnavailable : SensorManager = mock(SensorManager::class.java)
        `when`(sensorManagerTempUnavailable.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)).thenReturn(null)

        val tempWrapper = TemperatureWrapper(sensorManagerTempUnavailable)
        assertFalse(tempWrapper.isTemperatureSensorAvailable())
    }

    @Test
    fun testIsTemperatureSensorAvailableTrue(){
        val sensorManagerTempAvailable : SensorManager = mock(SensorManager::class.java)
        val mockedSensor : Sensor = mock(Sensor::class.java)
        `when`(sensorManagerTempAvailable.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)).thenReturn(mockedSensor)

        val tempWrapper = TemperatureWrapper(sensorManagerTempAvailable)
        assertTrue(tempWrapper.isTemperatureSensorAvailable())
    }



}
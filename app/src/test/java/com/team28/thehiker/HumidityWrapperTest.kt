package com.team28.thehiker


import android.hardware.Sensor
import android.hardware.SensorManager
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.*

class HumidityWrapperTest {

    @Test
    fun testIsHumiditySensorAvailableFalse(){
        val sensorManagerHumidityUnavailable : SensorManager = mock(SensorManager::class.java)
        `when`(sensorManagerHumidityUnavailable.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)).thenReturn(null)

        val humidityWrapper = HumidityWrapper(sensorManagerHumidityUnavailable)
        assertFalse(humidityWrapper.isHumiditySensorAvailable())
    }

    @Test
    fun testIsHumiditySensorAvailableTrue(){
        val sensorManagerHumidityAvailable : SensorManager = mock(SensorManager::class.java)
        val mockedSensor : Sensor = mock(Sensor::class.java)
        `when`(sensorManagerHumidityAvailable.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)).thenReturn(mockedSensor)

        val tempWrapper = HumidityWrapper(sensorManagerHumidityAvailable)
        assertTrue(tempWrapper.isHumiditySensorAvailable())
    }

}
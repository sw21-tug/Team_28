package com.team28.thehiker

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.*


class TemperatureWrapperTest {

    private val FLOAT_DELTA = 0.0001f
    private val TEMPERATURE_TEST_VARIABLE = 22.4f
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

    @Test
    fun testRegistersListener(){
        val sensorManager : SensorManager = mock(SensorManager::class.java)
        val mockedSensor : Sensor = mock(Sensor::class.java)
        `when`(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)).thenReturn(mockedSensor)

        val temperatureWrapper = TemperatureWrapper(sensorManager)

        val temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        verify(sensorManager, atLeast(1)).registerListener(temperatureWrapper, temperatureSensor, 1000)
    }

    @Test
    fun testUpdatesInternalVariable(){
        val sensorManager : SensorManager = mock(SensorManager::class.java)
        val temperatureWrapper = TemperatureWrapper(sensorManager)
        //no need to disable listener as no listener will be
        // registered since the SensorManager returns null

        val sensorEvent = mockSensorEvent(22.4f)
        temperatureWrapper.onSensorChanged(sensorEvent)

        assertTrue((TEMPERATURE_TEST_VARIABLE + FLOAT_DELTA) >= temperatureWrapper.getTemperature()!!)
        assertTrue((TEMPERATURE_TEST_VARIABLE - FLOAT_DELTA) <= temperatureWrapper.getTemperature()!!)
    }

    fun mockSensorEvent(temperature : Float) : SensorEvent{
        val sensorEvent : SensorEvent = mock(SensorEvent::class.java)
        val field = SensorEvent::class.java.getField("values")
        field.isAccessible = true

        val floatArray = FloatArray(1)
        floatArray[0] = temperature
        field.set(sensorEvent, floatArray)
        return sensorEvent
    }
}
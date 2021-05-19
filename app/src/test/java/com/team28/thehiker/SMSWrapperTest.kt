package com.team28.thehiker

import android.telephony.SmsManager
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*

class SMSWrapperTest {

    val UPDATE_INTERVAL : Long = 500

    val numbers = listOf<String>("+43664664664", "+497887788")

    @Mock
    var smsMock = mock(SmsManager::class.java)

    @Test
    fun testStartStopInterval(){
        val smsWrapper = SMSWrapper(smsMock, UPDATE_INTERVAL, numbers);

        verify(smsMock, never()).sendTextMessage(anyString(), any(), anyString(), any(), any())

        smsWrapper.start()
        Thread.sleep(3 * UPDATE_INTERVAL)
        verify(smsMock, atLeast(3)).sendTextMessage(ArgumentMatchers.eq(numbers[0]), any(), anyString(), any(), any())
        verify(smsMock, atLeast(3)).sendTextMessage(ArgumentMatchers.eq(numbers[1]), any(), anyString(), any(), any())
        verify(smsMock, atLeast(6)).sendTextMessage(anyString(), any(), anyString(), any(), any())

        smsWrapper.stop()
        Thread.sleep(2 * UPDATE_INTERVAL)
        verify(smsMock, atMost(5)).sendTextMessage(ArgumentMatchers.eq(numbers[0]), any(), anyString(), any(), any())
        verify(smsMock, atMost(5)).sendTextMessage(ArgumentMatchers.eq(numbers[1]), any(), anyString(), any(), any())
        verify(smsMock, atMost(10)).sendTextMessage(anyString(), any(), anyString(), any(), any())
    }
    
}
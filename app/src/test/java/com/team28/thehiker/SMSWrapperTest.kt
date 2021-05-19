package com.team28.thehiker

import android.telephony.SmsManager
import org.junit.Test
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
        verify(smsMock, after(3 * UPDATE_INTERVAL).atLeast(3)).sendTextMessage(numbers[0], any(), anyString(), any(), any())
        verify(smsMock, after(3 * UPDATE_INTERVAL).atLeast(3)).sendTextMessage(numbers[1], any(), anyString(), any(), any())
        verify(smsMock, after(3 * UPDATE_INTERVAL).atLeast(6)).sendTextMessage(anyString(), any(), anyString(), any(), any())

        smsWrapper.stop()
        verify(smsMock, after(2 * UPDATE_INTERVAL).atMost(5)).sendTextMessage(numbers[0], any(), anyString(), any(), any())
        verify(smsMock, after(2 * UPDATE_INTERVAL).atMost(5)).sendTextMessage(numbers[1], any(), anyString(), any(), any())
        verify(smsMock, after(2 * UPDATE_INTERVAL).atMost(10)).sendTextMessage(anyString(), any(), anyString(), any(), any())
    }
    
}
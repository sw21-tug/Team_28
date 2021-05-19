package com.team28.thehiker

import android.telephony.SmsManager
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

class SMSWrapperTest {

    val UPDATE_INTERVAL : Long = 500

    @Mock
    var smsMock = mock(SmsManager::class.java);

    @Test
    fun testStartStopIntervall(){
        val smsWrapper = SMSWrapper(smsMock, UPDATE_INTERVAL);

        verify(smsMock, never()).sendTextMessage(anyString(), anyString(), anyString(), any(), any())

        smsWrapper.start()
        verify(smsMock, times(1)).sendTextMessage(anyString(), anyString(), anyString(), any(), any())
        verify(smsMock, after(3 * UPDATE_INTERVAL).atLeast(2)).sendTextMessage(anyString(), anyString(), anyString(), any(), any())

        smsWrapper.stop()
        verify(smsMock, after(2 * UPDATE_INTERVAL).never()).sendTextMessage(anyString(), anyString(), anyString(), any(), any())

    }
    
}
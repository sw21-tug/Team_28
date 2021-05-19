package com.team28.thehiker

import android.telephony.SmsManager
import com.team28.thehiker.Constants.Constants
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

class SMSWrapperTest {

    val UPDATE_INTERVAL : Long = 500

    @Mock
    var smsMock = mock(SmsManager::class.java);

    @Test
    fun testStartStopInterval(){
        val smsWrapper = SMSWrapper(smsMock, UPDATE_INTERVAL);

        verify(smsMock, never()).sendTextMessage(anyString(), anyString(), anyString(), any(), any())

        smsWrapper.start()
        verify(smsMock, after(3 * UPDATE_INTERVAL).atLeast(3)).sendTextMessage(anyString(), any(), anyString(), any(), any())

        smsWrapper.stop()
        verify(smsMock, after(2 * UPDATE_INTERVAL).atMost(5)).sendTextMessage(anyString(), any(), anyString(), any(), any())
    }
    
}
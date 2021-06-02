package com.team28.thehiker

import android.location.Location
import android.telephony.SmsManager
import com.team28.thehiker.features.sosmessage.SMSWrapper
import com.team28.thehiker.features.sosmessage.SosMessageActivity
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

class SMSWrapperTest {
    val UPDATE_INTERVAL : Long = 500

    val numbers = listOf("+43664664664", "+497887788")

    @Mock
    var smsMock = mock(SmsManager::class.java)

    @Mock
    var activity = mock(SosMessageActivity::class.java)

    @Test
    fun testStartStopInterval(){
        val testLocation = mock(Location::class.java)
        `when`(testLocation.latitude)
            .thenReturn(51.9901615)

        `when`(testLocation.longitude)
            .thenReturn(20.7911654)

        `when`(activity.getString(R.string.sms_message_sos))
            .thenReturn("SOS!")

        `when`(activity.getString(R.string.sms_message_emergency_over))
            .thenReturn("Emergency state is over!")

        val smsWrapper = SMSWrapper(smsMock, UPDATE_INTERVAL, numbers, activity)

        verify(smsMock, never()).sendTextMessage(anyString(), any(), anyString(), any(), any())

        smsWrapper.notifyLocationUpdate(testLocation)

        val compareMessageStart = smsWrapper.getGoogleMapsLocationMessage(true)
        val compareMessageEnd = smsWrapper.getGoogleMapsLocationMessage()

        Thread.sleep(3 * UPDATE_INTERVAL)
        verify(smsMock, atLeast(3)).sendTextMessage(eq(numbers[0]), any(), eq(compareMessageStart), any(), any())
        verify(smsMock, atLeast(3)).sendTextMessage(eq(numbers[1]), any(), eq(compareMessageStart), any(), any())
        verify(smsMock, atLeast(6)).sendTextMessage(anyString(), any(), eq(compareMessageStart), any(), any())

        smsWrapper.stop()
        Thread.sleep(2 * UPDATE_INTERVAL)
        verify(smsMock, atMost(5)).sendTextMessage(eq(numbers[0]), any(), eq(compareMessageEnd), any(), any())
        verify(smsMock, atMost(5)).sendTextMessage(eq(numbers[1]), any(), eq(compareMessageEnd), any(), any())
        verify(smsMock, atMost(10)).sendTextMessage(anyString(), any(), eq(compareMessageEnd), any(), any())
    }
}
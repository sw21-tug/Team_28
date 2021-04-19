package com.team28.thehiker

import android.content.Context
import android.content.Intent
import android.location.Location
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ServiceTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class HikerLocationTest {

    @get:Rule
    var serviceRule = ServiceTestRule()

    @Test
    fun startSendingUpdatesAfterBindingService(){
        // bind service and retrieve service object from binder
        val binder = serviceRule.bindService(Intent(ApplicationProvider.getApplicationContext<Context>(),HikerLocationService::class.java))
        val service = (binder as HikerLocationService.HikerLocationBinder).getService()
        // mock interface
        val mockCallbackInterface = Mockito.mock(HikerLocationCallback::class.java)

        service.addLocationCallback(mockCallbackInterface)
        // verify that the method notifyLocationUpdate() has been called at least once after 10 sec
        Mockito.verify(mockCallbackInterface, Mockito.after(10000).atLeastOnce()).notifyLocationUpdate()

    }


}
package com.team28.thehiker


import android.Manifest
import com.team28.thehiker.constants.Constants
import com.team28.thehiker.features.sosmessage.SOSNumberChecker
import com.team28.thehiker.permissions.PermissionHandler
import com.team28.thehiker.sharedpreferencehandler.SharedPreferenceHandler
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*


class MainActivityTest {

    @Mock
    var permissionHandlerMock = mock(PermissionHandler::class.java)

    @Mock
    var sharedPreferenceMock = mock(SharedPreferenceHandler::class.java)

    @Test
    fun testPermissions_PermissionsGranted() {
        val mainActivity = MainActivity()

        `when`(permissionHandlerMock.permissionsAlreadyGranted(mainActivity))
                .thenReturn(true)

        mainActivity.permissionHandler = permissionHandlerMock
        mainActivity.checkPermissions()

        verify(permissionHandlerMock, times(1)).permissionsAlreadyGranted(mainActivity)
        verify(permissionHandlerMock, never()).askUserForPermissions(mainActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACTIVITY_RECOGNITION,
                Manifest.permission.SEND_SMS))
    }

    @Test
    fun testPermissions_PermissionsNotGranted() {
        val mainActivity = MainActivity()

        `when`(permissionHandlerMock.permissionsAlreadyGranted(mainActivity))
            .thenReturn(false)

        mainActivity.permissionHandler = permissionHandlerMock
        mainActivity.checkPermissions()

        verify(permissionHandlerMock, times(1)).permissionsAlreadyGranted(mainActivity)
        verify(permissionHandlerMock, times(1)).askUserForPermissions(mainActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACTIVITY_RECOGNITION,
                        Manifest.permission.SEND_SMS))
    }

    @Test
    fun testSharedPref_getSharedPrefAccessed() {
        val mainActivity = MainActivity()
        mainActivity.sharedPreferenceHandler = sharedPreferenceMock

        mainActivity.getSavedLocalizationString()

        verify(sharedPreferenceMock, times(1)).getLocalizationString(mainActivity)
    }

    @Test
    fun testSharedPref_setSharedPrefAccessed() {
        val mainActivity = MainActivity()

        mainActivity.sharedPreferenceHandler = sharedPreferenceMock
        mainActivity.setSavedLocalizationString(Constants.SharedPreferenceConstants.LOCALIZATION_RU)

        verify(sharedPreferenceMock, times(1)).setLocalizationString(mainActivity, Constants.SharedPreferenceConstants.LOCALIZATION_RU)
    }

    @Test
    fun testSharedPref_getSOSNumbers() {
        val mainActivity = MainActivity()
        mainActivity.sharedPreferenceHandler = sharedPreferenceMock

        mainActivity.getNumbers()

        verify(sharedPreferenceMock, times(1)).getNumbers(mainActivity)
    }

    @Test
    fun testSharedPref_setSOSNumbers() {
        val mainActivity = MainActivity()
        val numbers = listOf("test1", "test2")
        mainActivity.sharedPreferenceHandler = sharedPreferenceMock

        mainActivity.setNumbers(numbers)

        verify(sharedPreferenceMock, times(1)).setNumbers(mainActivity, numbers)
    }

    @Test
    fun testNumbersParser_correctFormat() {
        val number = "+436643338885"

        Assert.assertTrue(SOSNumberChecker().checkSOSNumber(number))
        Assert.assertTrue(SOSNumberChecker().checkSOSNumberLength(number))
    }

    @Test
    fun testNumbersParser_wrongFormat() {
        val number = "+43664-8885374652"

        Assert.assertFalse(SOSNumberChecker().checkSOSNumber(number))
        Assert.assertFalse(SOSNumberChecker().checkSOSNumberLength(number))
    }
}
package com.team28.thehiker


import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.team28.thehiker.Constants.Constants
import com.team28.thehiker.Permissions.PermissionHandler
import com.team28.thehiker.SharedPreferenceHandler.SharedPreferenceHandler
import org.junit.Assert
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.*


class MainActivityTest {

    @Mock
    var permissionHandlerMock = mock(PermissionHandler::class.java)

    @Mock
    var sharedPreferenceMock = mock(SharedPreferenceHandler::class.java)

    @Mock
    val sharedPrefs = mock(SharedPreferences::class.java)

    @Mock
    val sharedPrefsEditor = mock(SharedPreferences.Editor::class.java)

    @Mock
    val context: Activity = mock(Activity::class.java)

    @Test
    fun testPermissions_PermissionsGranted() {
        val mainActivity = MainActivity()

        `when`(permissionHandlerMock.permissionsAlreadyGranted(mainActivity))
                .thenReturn(true)

        mainActivity.checkPermissions(permissionHandlerMock)

        verify(permissionHandlerMock, times(1)).permissionsAlreadyGranted(mainActivity)
        verify(permissionHandlerMock, never()).askUserForPermissions(mainActivity)
    }

    @Test
    fun testPermissions_PermissionsNotGranted() {
        val mainActivity = MainActivity()

        `when`(permissionHandlerMock.permissionsAlreadyGranted(mainActivity))
            .thenReturn(false)

        mainActivity.checkPermissions(permissionHandlerMock)

        verify(permissionHandlerMock, times(1)).permissionsAlreadyGranted(mainActivity)
        verify(permissionHandlerMock, times(1)).askUserForPermissions(mainActivity)
    }

    @Test
    fun testSharedPref_getSharedPrefAccessed() {
        val mainActivity = MainActivity()
        `when`(context.getPreferences(anyInt())).thenReturn(sharedPrefs)

        `when`(sharedPrefs.getString(Constants.SharedPreferenceConstants.LOCALIZATION, Constants.SharedPreferenceConstants.LOCALIZATION_DEFAULT))
                .thenReturn("en")

        val locString = mainActivity.getSavedLocalizationString()

        verify(sharedPreferenceMock, times(1)).getLocalizationString(context)
        verify(sharedPrefs, times(1)).getString(Constants.SharedPreferenceConstants.LOCALIZATION, Constants.SharedPreferenceConstants.LOCALIZATION_DEFAULT)

        Assert.assertEquals(locString, Constants.SharedPreferenceConstants.LOCALIZATION_DEFAULT)
    }

    @Test
    fun testSharedPref_setSharedPrefAccessed() {
        val mainActivity = MainActivity()
        `when`(context.getPreferences(anyInt())).thenReturn(sharedPrefs)

        `when`(sharedPrefs.edit()).thenReturn(sharedPrefsEditor)

        mainActivity.setSavedLocalizationString(Constants.SharedPreferenceConstants.LOCALIZATION_RU)

        verify(sharedPreferenceMock, times(1)).setLocalizationString(context, Constants.SharedPreferenceConstants.LOCALIZATION_RU)
        verify(sharedPrefs, times(1)).edit()

        verify(sharedPrefsEditor, times(1)).putString(Constants.SharedPreferenceConstants.LOCALIZATION, Constants.SharedPreferenceConstants.LOCALIZATION_RU)
        verify(sharedPrefsEditor, times(1)).commit()
    }
}
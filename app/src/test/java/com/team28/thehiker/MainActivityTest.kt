package com.team28.thehiker

import com.team28.thehiker.PermissionHandler.PermissionHandler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(AndroidJUnit4::class)
class MainActivityTest {


    @Mock
    var permissionHandlerMock = mock(PermissionHandler::class.java)
    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun testPermissions_checkPermissionsIfNotGranted_PermissionsGranted() {
        val main = MainActivity()

        `when`(permissionHandlerMock.checkPermissionsGranted(main))
            .thenReturn(false)
        main.permissionCheck()
        verify(permissionHandlerMock, times(1)).requestPermissions(main)
    }

    @Test
    fun testPermissions_checkPermissionsIfNotGranted_PermissionsNotGranted() {
        val main = MainActivity()

        `when`(permissionHandlerMock.checkPermissionsGranted(main))
            .thenReturn(true)

        verify(permissionHandlerMock, never()).requestPermissions(main)
    }
}
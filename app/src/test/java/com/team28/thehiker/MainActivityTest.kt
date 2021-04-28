package com.team28.thehiker


import com.team28.thehiker.Permissions.PermissionHandler
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito.*


class MainActivityTest {

    @Mock
    var permissionHandlerMock = mock(PermissionHandler::class.java)

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
}
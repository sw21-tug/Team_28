package com.team28.thehiker

import com.team28.thehiker.Permissions.PermissionHandler
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class MainActivityTest {

    @Mock
    var permissionHandlerMock = PermissionHandler()

    @Test
    fun testPermissions_checkPermissionsIfNotGranted_PermissionsGranted() {
        `when`(permissionHandlerMock.checkPermissionsGranted())
                .thenReturn(false)

        Mockito.verify(permissionHandlerMock.askUserForPermissions(), Mockito.times(1))
    }

    @Test
    fun testPermissions_checkPermissionsIfNotGranted_PermissionsNotGranted() {
        `when`(permissionHandlerMock.checkPermissionsGranted())
                .thenReturn(true)

        Mockito.verify(permissionHandlerMock.askUserForPermissions(), Mockito.times(0))
    }
}
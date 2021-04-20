package com.team28.thehiker.Permissions

interface IPermissionHandler {

    fun checkPermissionsGranted() : Boolean

    fun askUserForPermissions()

}
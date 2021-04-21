package com.team28.thehiker.Permissions

import android.app.Activity

interface IPermissionHandler {
    fun permissionsAlreadyGranted(context: Activity) : Boolean

    fun askUserForPermissions(context: Activity)
}
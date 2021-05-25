package com.team28.thehiker.permissions

import android.app.Activity

interface IPermissionHandler {
    fun permissionsAlreadyGranted(context: Activity) : Boolean

    fun askUserForPermissions(context: Activity)
}
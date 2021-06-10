package com.team28.thehiker.permissions

import android.app.Activity
import java.sql.Array

interface IPermissionHandler {
    fun permissionsAlreadyGranted(context: Activity) : Boolean

    fun askUserForPermissions(context: Activity, permissions: kotlin.Array<String>)
}
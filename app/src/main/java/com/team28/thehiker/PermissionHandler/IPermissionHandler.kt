package com.team28.thehiker.PermissionHandler

import android.app.Activity
import android.content.Context

interface IPermissionHandler {

    fun checkPermissionsGranted(context: Activity) : Boolean
    fun requestPermissions(context: Activity)



}
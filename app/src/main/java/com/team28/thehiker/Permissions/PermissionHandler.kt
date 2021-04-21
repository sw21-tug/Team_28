package com.team28.thehiker.Permissions

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.team28.thehiker.Constants.Constants

class PermissionHandler : IPermissionHandler {
    override fun permissionsAlreadyGranted(context: Activity) : Boolean {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    override fun askUserForPermissions(context: Activity) {
        ActivityCompat.requestPermissions(context,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                Constants.PermissionConstants.PERMISSION_REQUEST_CODE)
    }
}
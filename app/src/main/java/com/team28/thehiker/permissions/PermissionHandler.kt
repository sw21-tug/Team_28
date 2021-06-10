package com.team28.thehiker.permissions

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.team28.thehiker.constants.Constants
import java.sql.Array

class PermissionHandler : IPermissionHandler {
    override fun permissionsAlreadyGranted(context: Activity) : Boolean {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    override fun askUserForPermissions(context: Activity, permissions: kotlin.Array<String>) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(context,
                    permissions
                    , Constants.PermissionConstants.PERMISSION_REQUEST_CODE)
        } else {
            ActivityCompat.requestPermissions(context,
                permissions,
                Constants.PermissionConstants.PERMISSION_REQUEST_CODE)
        }
    }
}
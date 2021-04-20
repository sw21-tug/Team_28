package com.team28.thehiker.PermissionHandler

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.team28.thehiker.Constants.Constants

class PermissionHandler: IPermissionHandler {
    override fun checkPermissionsGranted(context: Activity): Boolean {

        val fineLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)

        return fineLocation== PackageManager.PERMISSION_GRANTED && coarseLocation== PackageManager.PERMISSION_GRANTED
    }

    override fun requestPermissions(context: Activity) {
        ActivityCompat.requestPermissions(context,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            Constants.PermissionConstants.PERMISSION_REQUEST_CODE
        )    }


}
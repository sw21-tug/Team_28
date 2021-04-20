package com.team28.thehiker

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.team28.thehiker.Constants.Constants.PermissionConstants.PERMISSION_REQUEST_CODE
import com.team28.thehiker.PermissionHandler.PermissionHandler

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       permissionCheck()
    }
    fun permissionCheck (){
        if (!PermissionHandler().checkPermissionsGranted(this)){
            PermissionHandler().requestPermissions(this)
        }
    }
    fun mainMenuButtonClicked(view: View) {
        val intent: Intent = when (view.id) {
            R.id.btn_altitude -> {
                Intent(applicationContext, TestActivity::class.java)
            }
            R.id.btn_position_on_map -> {
                Intent(applicationContext, TestActivity::class.java)
            }
            else -> {
                Intent(applicationContext, TestActivity::class.java)
            }
        }

        startActivity(intent)
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_DENIED
                            && grantResults[1] == PackageManager.PERMISSION_DENIED)) {

                    this.finish()
                }
                return
            }

            else -> {
            }
        }
    }
}
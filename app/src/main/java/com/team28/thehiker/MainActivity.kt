package com.team28.thehiker


import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Visibility
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.VisibleForTesting
import com.team28.thehiker.Constants.Constants
import com.team28.thehiker.Permissions.PermissionHandler
import com.team28.thehiker.SharedPreferenceHandler.SharedPreferenceHandler

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferenceHandler : SharedPreferenceHandler
    lateinit var permissionHandler : PermissionHandler
    lateinit var temperatureWrapper :TemperatureWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferenceHandler = SharedPreferenceHandler()
        permissionHandler = PermissionHandler()

        val sensorManager : SensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        temperatureWrapper = TemperatureWrapper(sensorManager)

        decidedButtonsShown()

        checkPermissions()
    }

    override fun onDestroy() {
        super.onDestroy()
        temperatureWrapper.kill()
    }

    fun checkPermissions() {
        if (!permissionHandler.permissionsAlreadyGranted(this)) {
            permissionHandler.askUserForPermissions(this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            Constants.PermissionConstants.PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isEmpty() ||
                            grantResults[0] == PackageManager.PERMISSION_DENIED)) {
                    finish()
                }
                return
            }
            else -> { }
        }
    }

    fun navigateTo(view: View) {
        val intent :Intent

        when (view.id) {
            R.id.btn_altitude -> {
                intent = Intent(this, AltitudeActivity::class.java)
            }
            R.id.btn_position_on_map -> {
                intent = Intent(this, TestActivity::class.java)
            }
            R.id.btn_temperature ->{
                intent = Intent(this, TemperatureActivity::class.java)
                val temperature : Double? = temperatureWrapper.getTemperature()
                intent.putExtra(TemperatureActivity.TEMP_KEY,temperature)
            }
            else -> {
                intent = Intent(this, TestActivity::class.java)
            }
        }

        startActivity(intent)
    }

    fun getSavedLocalizationString() : String? {
        return sharedPreferenceHandler.getLocalizationString(this)
    }

    fun setSavedLocalizationString(localization: String) {
        sharedPreferenceHandler.setLocalizationString(this, localization)
    }

    fun decidedButtonsShown(){
        //decide whether to show the temperature button
        val temperatureButton : LinearLayout = findViewById(R.id.ll_temperature)
        if(temperatureWrapper.isTemperatureSensorAvailable()){
            temperatureButton.visibility = View.VISIBLE
        }else{
            temperatureButton.visibility = View.GONE
        }

        temperatureButton.invalidate()
    }
}
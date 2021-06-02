package com.team28.thehiker


import android.Manifest
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.PopupMenu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.navigation.NavigationView
import com.team28.thehiker.constants.Constants
import com.team28.thehiker.features.altitude.AltitudeActivity
import com.team28.thehiker.features.findme.FindMeActivity
import com.team28.thehiker.features.humidity.HumidityActivity
import com.team28.thehiker.features.humidity.HumidityWrapper
import com.team28.thehiker.features.pedometer.PedometerActivity
import com.team28.thehiker.features.sosmessage.SOSNumberChecker
import com.team28.thehiker.features.temperature.TemperatureActivity
import com.team28.thehiker.features.temperature.TemperatureWrapper
import com.team28.thehiker.permissions.PermissionHandler
import com.team28.thehiker.sharedpreferencehandler.SharedPreferenceHandler
import com.team28.thehiker.language.LanguageSelector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_settings.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var sharedPreferenceHandler : SharedPreferenceHandler
    lateinit var permissionHandler : PermissionHandler
    lateinit var humidityWrapper: HumidityWrapper
    lateinit var temperatureWrapper : TemperatureWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_settings)
        setSupportActionBar(toolbar)


        val toggle =
            ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setTitleTextColor(getColor(R.color.black))
            toggle.drawerArrowDrawable.color = getColor(R.color.black)
        }
        else {
            toolbar.setTitleTextColor(resources.getColor(R.color.black))
            toggle.drawerArrowDrawable.color = resources.getColor(R.color.black)
        }

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        drawer_menu.setNavigationItemSelectedListener(this)

        sharedPreferenceHandler = SharedPreferenceHandler()
        permissionHandler = PermissionHandler()

        checkPermissions()
        
        val sensorManager : SensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        humidityWrapper = HumidityWrapper(sensorManager)
        decidedButtonHumidityShown()
        temperatureWrapper = TemperatureWrapper(sensorManager)

        decidedButtonsShown()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            Constants.PermissionConstants.PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isEmpty() ||
                            grantResults[0] == PackageManager.PERMISSION_DENIED)
                ) {
                    return
                }
                return
            }
            else -> {
            }
        }
    }

    fun navigateTo(view: View) {
        val intent: Intent
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        when (view.id) {
            R.id.btn_altitude -> {
                if(permission == PackageManager.PERMISSION_GRANTED) {
                    intent = Intent(this, AltitudeActivity::class.java)
                } else {
                    permissionHandler.askUserForPermissions(this)
                    if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        showDialog()
                    }
                    return
                }
            }
            R.id.btn_position_on_map -> {
                if(permission == PackageManager.PERMISSION_GRANTED) {
                    intent = Intent(this, FindMeActivity::class.java)
                } else {
                    permissionHandler.askUserForPermissions(this)
                    if(!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        showDialog()
                    }
                    return
                }
            }
            R.id.btn_humidity -> {
                intent = Intent(this, HumidityActivity::class.java)
            }
            R.id.btn_temperature ->{
                intent = Intent(this, TemperatureActivity::class.java)
                val temperature : Double? = temperatureWrapper.getTemperature()
                intent.putExtra(TemperatureActivity.TEMP_KEY,temperature)
            }
            R.id.btn_pedometer -> {

                if(getNumbers()[0]?.isEmpty()!! || getNumbers()[1]?.isEmpty()!!) {
                    showSOSDialog()
                }
                intent = Intent(this, MainActivity::class.java) //TODO revert to pedometer activity
            }
            else -> {
                intent = Intent(this, MainActivity::class.java)
            }
        }
        startActivity(intent)
    }

    private fun showSOSDialog() {
       var builder  = AlertDialog.Builder(this)
        builder.setTitle(R.string.title_SOS_alert)
        val layout = layoutInflater.inflate(R.layout.alert_dialog_phone_numbers, null)
        builder.setView(R.layout.alert_dialog_phone_numbers)

        builder.setPositiveButton("Save", DialogInterface.OnClickListener {_ ,_->
            storePhoneNumbers(layout.findViewById(R.id.phonenumber1),
                layout.findViewById(R.id.phonenumber2))
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener {_,_->
            return@OnClickListener
        })
        builder.show()
    }

    private fun storePhoneNumbers(phoneEdit1: EditText,  phoneEdit2: EditText) {
     return
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.language) {
            val popupMenu = PopupMenu(this, findViewById(R.id.language))
            popupMenu.menuInflater.inflate(R.menu.popup_menu_language, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.popup_russian -> {
                        LanguageSelector.setLocaleToRussian(this)
                        setSavedLocalizationString("ru")
                        val i = Intent(this, SplashscreenActivity::class.java)
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(i)
                    }

                    R.id.popup_english -> {
                        LanguageSelector.setLocaleToEnglish(this)
                        setSavedLocalizationString("en")
                        val i = Intent(this, SplashscreenActivity::class.java)
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(i)
                    }
                }
                true
            })
            popupMenu.show()
        }
        return true
    }

    fun decidedButtonHumidityShown(){
        //decide whether to show the humidity button
        val humidityButton : LinearLayout = findViewById(R.id.ll_humidity)
        if(humidityWrapper.isHumiditySensorAvailable()){
            humidityButton.visibility = View.VISIBLE
        }else{
            humidityButton.visibility = View.GONE
        }

        humidityButton.invalidate()
    }

    fun getSavedLocalizationString() : String? {
        return sharedPreferenceHandler.getLocalizationString(this)
    }

    fun setSavedLocalizationString(localization: String) {
        sharedPreferenceHandler.setLocalizationString(this, localization)
    }

    fun showDialog() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
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

    fun getNumbers() : List<String?> {
        return sharedPreferenceHandler.getNumbers(this)
    }

    fun setNumbers(numbers: List<String>) {
        sharedPreferenceHandler.setNumbers(this, numbers)
    }

}


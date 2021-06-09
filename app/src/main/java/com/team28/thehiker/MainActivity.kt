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
import android.widget.ImageView
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.ScrollView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.doOnLayout
import com.google.android.material.navigation.NavigationView
import com.team28.thehiker.constants.Constants
import com.team28.thehiker.features.altitude.AltitudeActivity
import com.team28.thehiker.features.findme.FindMeActivity
import com.team28.thehiker.features.humidity.HumidityActivity
import com.team28.thehiker.features.humidity.HumidityWrapper
import com.team28.thehiker.features.pedometer.PedometerActivity
import com.team28.thehiker.features.sosmessage.SOSNumberChecker
import com.team28.thehiker.features.sosmessage.SosMessageActivity
import com.team28.thehiker.features.temperature.TemperatureActivity
import com.team28.thehiker.features.temperature.TemperatureWrapper
import com.team28.thehiker.permissions.PermissionHandler
import com.team28.thehiker.sharedpreferencehandler.SharedPreferenceHandler
import com.team28.thehiker.language.LanguageSelector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_settings.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var sharedPreferenceHandler: SharedPreferenceHandler
    lateinit var permissionHandler: PermissionHandler
    lateinit var humidityWrapper: HumidityWrapper
    lateinit var temperatureWrapper: TemperatureWrapper

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
        } else {
            toolbar.setTitleTextColor(resources.getColor(R.color.black))
            toggle.drawerArrowDrawable.color = resources.getColor(R.color.black)
        }

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        drawer_menu.setNavigationItemSelectedListener(this)

        sharedPreferenceHandler = SharedPreferenceHandler()
        permissionHandler = PermissionHandler()

        checkPermissions()

        val sensorManager: SensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        humidityWrapper = HumidityWrapper(sensorManager)
        decidedButtonHumidityShown()
        temperatureWrapper = TemperatureWrapper(sensorManager)

        decidedButtonsShown()

        findViewById<ScrollView>(R.id.scrollview_menu).doOnLayout {
            val imageView = findViewById<ImageView>(R.id.main_image)
            val screenHeight = resources.displayMetrics.heightPixels
            val params = it.layoutParams
            params.height = screenHeight - imageView.bottom
            it.layoutParams = params
        }
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
        var intent: Intent? = null
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val permissionSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
        when (view.id) {
            R.id.btn_altitude -> {
                if (permission == PackageManager.PERMISSION_GRANTED) {
                    intent = Intent(this, AltitudeActivity::class.java)
                } else {
                    permissionHandler.askUserForPermissions(this)
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        showPermissionAlertDialog("LOCATION")
                    }
                    return
                }
            }
            R.id.btn_position_on_map -> {
                if (permission == PackageManager.PERMISSION_GRANTED) {
                    intent = Intent(this, FindMeActivity::class.java)
                } else {
                    permissionHandler.askUserForPermissions(this)
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        showPermissionAlertDialog("LOCATION")
                    }
                    return
                }
            }
            R.id.btn_humidity -> {
                intent = Intent(this, HumidityActivity::class.java)
            }
            R.id.btn_temperature -> {
                intent = Intent(this, TemperatureActivity::class.java)
                val temperature: Double? = temperatureWrapper.getTemperature()
                intent.putExtra(TemperatureActivity.TEMP_KEY, temperature)
            }
            R.id.btn_pedometer -> {
                if (permission == PackageManager.PERMISSION_GRANTED) {
                    intent = Intent(this, PedometerActivity::class.java)
                } else {
                    permissionHandler.askUserForPermissions(this)
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACTIVITY_RECOGNITION)) {
                        showPermissionAlertDialog("PHYSICAL_ACTIVITY")
                    }
                    return
                }
            }
            R.id.btn_speed_of_moving -> {
                intent = Intent(this, SpeedActivity::class.java)
            }
            R.id.btn_sos -> {
                if (permission == PackageManager.PERMISSION_GRANTED &&
                        permissionSMS == PackageManager.PERMISSION_GRANTED) {

                    if (getNumbers()[0]?.isEmpty()!! || getNumbers()[1]?.isEmpty()!!) {
                        showSOSDialog()
                    } else {
                        intent = Intent(this, SosMessageActivity::class.java)
                    }


                } else {
                    permissionHandler.askUserForPermissions(this)
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        showPermissionAlertDialog("LOCATION, SEND_SMS")
                    }
                    return
                }

            }
            else -> {
                intent = Intent(this, MainActivity::class.java)
            }
        }
        startActivity(intent)
    }

    private fun showSOSDialog() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.title_SOS_alert)


        var layout: View = layoutInflater.inflate(R.layout.alert_dialog_phone_numbers, null)
        builder.setView(layout)

        builder.setPositiveButton("Save", DialogInterface.OnClickListener { dialog, which ->
            val phoneEdit1 = layout.findViewById<EditText>(R.id.phonenumber1)
            val phoneEdit2 = layout.findViewById<EditText>(R.id.phonenumber2)

            if (SOSNumberChecker().checkSOSNumberLength(phoneEdit1.text.toString()) &&
                    SOSNumberChecker().checkSOSNumberLength(phoneEdit2.text.toString())) {
                setNumbers(listOf(phoneEdit1.text.toString(), phoneEdit2.text.toString()))
                intent = Intent(this, SosMessageActivity::class.java)
                startActivity(intent)
            } else {
                var dialog = AlertDialog.Builder(this)
                dialog.setTitle("SOS Warning")
                dialog.setMessage("Please enter a valid phone number!")
                dialog.show()
            }
        })

        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            (
                    return@OnClickListener
                    )
        })

        builder.show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.language) {
            val popupMenu = PopupMenu(this, findViewById(R.id.language))
            popupMenu.menuInflater.inflate(R.menu.popup_menu_language, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
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

    fun decidedButtonHumidityShown() {
        //decide whether to show the humidity button
        val humidityButton: LinearLayout = findViewById(R.id.ll_humidity)
        if (humidityWrapper.isHumiditySensorAvailable()) {
            humidityButton.visibility = View.VISIBLE
        } else {
            humidityButton.visibility = View.GONE
        }

        humidityButton.invalidate()
    }

    fun getSavedLocalizationString(): String? {
        return sharedPreferenceHandler.getLocalizationString(this)
    }

    fun setSavedLocalizationString(localization: String) {
        sharedPreferenceHandler.setLocalizationString(this, localization)
    }

    fun showPermissionAlertDialog(permission_text: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.title_permission_alert)
        //R.string.permission_alert_query
        builder.setMessage(R.string.permission_alert_message)
        builder.setMessage(R.string.permission_alert_query)

        builder.setMessage(getString(R.string.permission_alert_message) + "\n" + permission_text + "\n\n" +
                getString(R.string.permission_alert_query))

        builder.setPositiveButton(R.string.string_yes, DialogInterface.OnClickListener { _, _ ->
            openSettings()
        })
        builder.setNegativeButton(R.string.string_no, DialogInterface.OnClickListener { _, _ ->
            return@OnClickListener
        })
        builder.show()
    }

    fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    fun decidedButtonsShown() {
        //decide whether to show the temperature button
        val temperatureButton: LinearLayout = findViewById(R.id.ll_temperature)
        if (temperatureWrapper.isTemperatureSensorAvailable()) {
            temperatureButton.visibility = View.VISIBLE
        } else {
            temperatureButton.visibility = View.GONE
        }

        temperatureButton.invalidate()
    }

    fun getNumbers(): List<String?> {
        return sharedPreferenceHandler.getNumbers(this)
    }

    fun setNumbers(numbers: List<String>) {
        sharedPreferenceHandler.setNumbers(this, numbers)
    }

}


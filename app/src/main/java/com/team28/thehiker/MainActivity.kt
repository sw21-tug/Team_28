package com.team28.thehiker


import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.navigation.NavigationView
import com.team28.thehiker.Constants.Constants
import com.team28.thehiker.Permissions.PermissionHandler
import com.team28.thehiker.SharedPreferenceHandler.SharedPreferenceHandler
import com.team28.thehiker.language.LanguageSelector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_settings.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var sharedPreferenceHandler : SharedPreferenceHandler
    lateinit var permissionHandler : PermissionHandler
    var permissionStatus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_settings)
        setSupportActionBar(toolbar)

        val toggle =
            ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        drawer_menu.setNavigationItemSelectedListener(this)

        sharedPreferenceHandler = SharedPreferenceHandler()
        permissionHandler = PermissionHandler()
        checkPermissions()
    }

    fun checkPermissions() {
        permissionStatus = permissionHandler.permissionsAlreadyGranted(this)
        if (!permissionStatus) {
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
                    //finish()
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
                            showdialog()
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
                        showdialog()
                    }
                    return
                }
            }
            else -> {
                if(permission == PackageManager.PERMISSION_GRANTED) {
                    intent = Intent(this, TestActivity::class.java)
                } else {
                    permissionHandler.askUserForPermissions(this)
                    return
                }
            }
        }
        startActivity(intent)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.language) {
            val popupMenu = PopupMenu(this, findViewById(R.id.language))
            popupMenu.menuInflater.inflate(R.menu.popup_menu_language, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.popup_russian -> {
                        LanguageSelector.setLocaleToRussian(this)
                        setSavedLocalizationString("ru")
                        recreate()
                    }

                    R.id.popup_english -> {
                        LanguageSelector.setLocaleToEnglish(this)
                        setSavedLocalizationString("en")
                        recreate()
                    }
                }
                true
            })
            popupMenu.show()
        }
        return true
    }

    fun getSavedLocalizationString() : String? {
        return sharedPreferenceHandler.getLocalizationString(this)
    }

    fun setSavedLocalizationString(localization: String) {
        sharedPreferenceHandler.setLocalizationString(this, localization)
    }

    fun showdialog() {
        val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Permission access needed")
        builder.setMessage("We need location permission for this tool to work. " +
                "To grant us permission go to Settings -> (Searchbar)'permission' -> Permission Manager." +
                "Search for 'The Hiker' and provide access to location.")
        builder.show()
    }
}


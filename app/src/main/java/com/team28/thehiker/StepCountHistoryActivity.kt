package com.team28.thehiker

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.PopupMenu
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import com.team28.thehiker.Constants.Constants
import com.team28.thehiker.Permissions.PermissionHandler
import com.team28.thehiker.SharedPreferenceHandler.SharedPreferenceHandler
import com.team28.thehiker.language.LanguageSelector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_settings.*

class StepCountHistoryActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step_history)
        //load saved values

        val listView = findViewById<ListView>(R.id.list_view_left)
        val names = arrayOf("date_1","date_2","date_3")

        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, names)

        listView.adapter=arrayAdapter
    }
}

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
import kotlinx.android.synthetic.main.step_count_history.*

class StepCountHistoryActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.step_count_history)

        val producto1 = Producto("1 de enero", "1 pasos")
        val producto2 = Producto("2 de enero", "2 pasos")
        val producto3 = Producto("3 de enero", "3 pasos")
        val producto4 = Producto("4 de enero", "4 pasos")
        val producto5 = Producto("5 de enero", "5 pasos")
        val producto6 = Producto("6 de enero", "6 pasos")
        val producto7 = Producto("7 de enero", "7 pasos")
        val producto8 = Producto("8 de enero", "8 pasos")
        val producto9 = Producto("9 de enero", "9 pasos")
        val producto10 = Producto("10 de enero", "10 pasos")
        val producto11 = Producto("11 de enero", "11 pasos")
        val producto12 = Producto("12 de enero", "12 pasos")
        val producto13 = Producto("13 de enero", "13 pasos")
        val producto14 = Producto("14 de enero", "14 pasos")
        val producto15 = Producto("15 de enero", "15 pasos")
        val producto16 = Producto("16 de enero", "16 pasos")
        val producto17 = Producto("17 de enero", "17 pasos")
        val producto18 = Producto("18 de enero", "18 pasos")
        val producto19 = Producto("19 de enero", "19 pasos")
        val producto20 = Producto("20 de enero", "20 pasos")
        val listaProducto = listOf(producto1, producto2, producto3, producto4, producto5, producto6, producto7, producto8, producto9, producto10, producto11, producto12, producto13, producto14, producto15, producto16, producto17, producto18, producto19, producto20)
        val adapter = ProductoAdapter(this, listaProducto)
        lista.adapter = adapter


    }
}
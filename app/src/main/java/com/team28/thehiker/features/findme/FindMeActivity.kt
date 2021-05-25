package com.team28.thehiker.features.findme

import android.annotation.SuppressLint
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.team28.thehiker.R
import java.util.*

/**
 * This shows how to create a simple activity with a raw MapView and add a marker to it. This
 * requires forwarding all the important lifecycle methods onto MapView.
 */
class FindMeActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mapView: MapView
    private val MAP_MIN_ZOOM = 10f
    private val MAP_MAX_ZOOM = 100f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_me)

        if(isRussian()){
            val titleTextView : TextView = findViewById(R.id.my_location_string)
            titleTextView.textSize = 35.0f
            titleTextView.invalidate()
        }

        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        val mapViewBundle = savedInstanceState?.getBundle(MAPVIEW_BUNDLE_KEY)
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY) ?: Bundle().also {
            outState.putBundle(MAPVIEW_BUNDLE_KEY, it)
        }
        mapView.onSaveInstanceState(mapViewBundle)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {
        map.mapType = GoogleMap.MAP_TYPE_TERRAIN
        map.setMinZoomPreference(MAP_MIN_ZOOM)
        map.setMaxZoomPreference(MAP_MAX_ZOOM)

        map.setMyLocationEnabled(true)
        val loc = LocationServices.getFusedLocationProviderClient(this).lastLocation

        loc.addOnSuccessListener {
            val locationResult : Location? = loc.result
            if(locationResult != null){
                map.moveCamera(CameraUpdateFactory.newLatLng(LatLng(loc.result.latitude,
                    loc.result.longitude)))
            }
        }
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    companion object {
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    }

    private fun isRussian() : Boolean{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return resources.configuration.locales[0].toLanguageTag().equals("ru-RU")
        }
        return resources.configuration.locale.language.equals(Locale("ru").language)
    }
}
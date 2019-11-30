package com.example.geotunes

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*
import android.widget.TextView
import android.Manifest.permission
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.READ_CONTACTS
import android.content.Intent
import kotlinx.android.synthetic.main.fragment_lyrics.*
import kotlinx.android.synthetic.main.fragment_map.home_button
import com.google.android.gms.maps.CameraUpdateFactory
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap








class MapActivity : Fragment(), OnMapReadyCallback {


    val PERMISSION_ID = 42
    private lateinit var googleMap: GoogleMap



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var root = inflater.inflate(com.example.geotunes.R.layout.fragment_map,container,false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        map_view.onCreate(savedInstanceState)
        map_view.onResume()

        map_view.getMapAsync(this)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        home_button.setOnClickListener{
            val i = Intent(activity, MainActivity::class.java)
            startActivity(i)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        if (checkPermissions()) {
            setMyLocationEnabled()
        }
    }

    private fun checkPermissions(): Boolean {
        if (ContextCompat.checkSelfPermission(this.context!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        } else {
            requestPermissions()
            return false
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this.activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_ID -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setMyLocationEnabled()
                } else {
                }
            }
        }
    }

    private fun setMyLocationEnabled() {
        googleMap.getUiSettings().setMyLocationButtonEnabled(true)
        googleMap.setMyLocationEnabled(true)
        googleMap.setOnMyLocationChangeListener(object : GoogleMap.OnMyLocationChangeListener {
            override fun onMyLocationChange(location: Location) {
                //TODO:
            }
        })
    }
}
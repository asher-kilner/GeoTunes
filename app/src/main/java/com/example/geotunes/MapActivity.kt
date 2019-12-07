package com.example.geotunes

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import kotlinx.android.synthetic.main.fragment_map.*
import android.content.Intent
import kotlinx.android.synthetic.main.fragment_map.home_button
import com.google.android.gms.maps.CameraUpdateFactory
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import android.app.Activity
import android.content.Context
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.*
import kotlin.random.Random
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : Fragment(), OnMapReadyCallback {

    lateinit var parent_activity : Activity
    lateinit var song : Song
    val PERMISSION_ID = 42
    private lateinit var googleMap: GoogleMap
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var currentLocation : LatLng

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        map_view.onCreate(savedInstanceState)
        map_view.onResume()
        map_view.getMapAsync(this)

        home_button.setOnClickListener{
            val i = Intent(activity, MainActivity::class.java)
            startActivity(i)
        }

        guess_button_map.setOnClickListener{
            var parentActivity = activity as LyricMap
            parentActivity.setCurrentFragment(0, true)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var root = inflater.inflate(com.example.geotunes.R.layout.fragment_map,container,false)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this.activity!!)
        getLastLocation()


        return root
    }

    private fun getLastLocation() {
        if(checkPermissions()){
            if(isLocationEnabled()){
                mFusedLocationClient.lastLocation.addOnCompleteListener(this){ task ->
                    var location : Location = task.result
                    if(location == null){
                        requestNewLocationData()
                    } else{
                        var lat = location.latitude
                        var lng = location.longitude

                        var acuracy = location.accuracy

                        currentLocation = LatLng(lat,lng)
                    }
                }
            } else{
                Toast.makeText(this.context,"Turn on Location",Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }
    }

    private fun requestNewLocationData() {
         var mLocationRequest = LocationRequest()
        mLocationRequest.priority =LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 2000
        mLocationRequest.fastestInterval = 1000
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        getSongFromParent()
    }

    private fun  getSongFromParent(){
        try {
            parent_activity = activity as LyricMap
            song = (parent_activity as LyricMap).current_song
        } catch (e: ClassCastException) {
            throw ClassCastException("$activity must implement OnHeadlineSelectedListener")
        }
    }

    override fun onMapReady(map: GoogleMap) {
        map?.let {googleMap = it}
        val BOUNDS = LatLngBounds(
            LatLng(51.617616, -3.881533), LatLng(51.620075, -3.875482)
        )
        val center = LatLng(51.618901, -3.878546)
        val myLocation =

        generateRandomMarkers(BOUNDS)

        // Constrain the camera target to the Adelaide bounds.
        googleMap.setLatLngBoundsForCameraTarget(BOUNDS)
        googleMap.setMinZoomPreference(15.0f)
        googleMap.setMaxZoomPreference(22.0f)
        val startlocation = googleMap.addMarker(MarkerOptions().position(myLocation).title("Marker in Sydney"))
        startlocation.setVisible(false)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center,16f))


        if (checkPermissions()) {
            setMyLocationEnabled()
        }
    }

    private fun generateRandomMarkers(bounds: LatLngBounds) {
        val generatedrandomlats = List(song.lyrics.size) { Random.nextDouble(bounds.southwest.latitude, bounds.northeast.latitude)}
        val generatedrandomlngs = List(song.lyrics.size) { Random.nextDouble(bounds.southwest.longitude, bounds.northeast.longitude)}
        for (i in 0..song.lyrics.size -1){
            googleMap.addMarker(MarkerOptions().position(LatLng(generatedrandomlats[i], generatedrandomlngs[i])).title(song.lyrics[i]))
        }

    }

    private fun isLocationEnabled():Boolean{
        var locationManager:LocationManager = this.activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)|| locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
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

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<String>, grantResults: IntArray) {
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
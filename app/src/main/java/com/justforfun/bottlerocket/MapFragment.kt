package com.justforfun.bottlerocket

import android.content.pm.PackageManager
import android.location.Location
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment(), OnMapReadyCallback {


    companion object {
        fun newInstance() = MapFragment()
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var viewModel: MapViewModel
    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var markers: MutableList<MarkerOptions> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.map_fragment, container, false)

        val mapFragment =   childFragmentManager.findFragmentById(R.id.google_map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MapViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.fetchStores(context!!,false)


        // TODO: Use the ViewModel

        viewModel.storesLiveData.observe(this, Observer {
            markers.clear()
            for (store in it){
                val position = LatLng(store.latitude, store.longitude)
                val markerOptions = MarkerOptions().position(position)
                markers.add(markerOptions)
                if(mMap != null){
                    mMap.clear()
                    mMap.addMarker(markerOptions)
                }
            }
        })
    }

    fun manualRefresh(){
        if (viewModel != null) {
            viewModel.fetchStores(context!!,true)
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        mMap.uiSettings.isZoomControlsEnabled = true
        if (markers.isNotEmpty()) {
            mMap.clear()
            for (marker in markers)
                mMap.addMarker(marker)
        }
        setUpMap()
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(context!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)

            return
        }
        getLocation()
    }

    private fun getLocation(){
        mMap.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(activity!!) { location ->
            // Got last known location. In some rare situations this can be null.
            // 3
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getLocation()
                }
            }
        }
    }
}

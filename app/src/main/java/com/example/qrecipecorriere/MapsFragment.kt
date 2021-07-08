package com.example.qrecipecorriere

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class MapsFragment : Fragment() {

    private val TAG = "MapsFragment"

    private lateinit var userAddress : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //perchè non trova arguments dio madonna de dio
        userAddress = arguments?.getString("user address").toString()
        Toast.makeText(requireContext(), userAddress, Toast.LENGTH_LONG).show()



        val callback = OnMapReadyCallback { googleMap ->
            val userLocation = getLocationFromAddress(this.requireContext(), userAddress)
            googleMap.addMarker(MarkerOptions().position(userLocation).title("Marker in Reciver Location"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 13f))

        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun getLocationFromAddress(context: Context, strAddress: String): LatLng {
        val coder = Geocoder(context)
        val address: List<Address>
        lateinit var p1: LatLng

        try {
            address = coder.getFromLocationName(strAddress, 5)
            if(address != null) {
                val location: Address = address[0]
                p1 = LatLng(location.latitude, location.longitude)
            }

        } catch (e: IOException) { e.printStackTrace() }

        return p1
    }
}
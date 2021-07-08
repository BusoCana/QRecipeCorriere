package com.example.qrecipecorriere

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_ingredients.*
import kotlinx.android.synthetic.main.fragment_user.*
import java.io.IOException

class UserFragment : Fragment() {

    private val TAG = "UserFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //map
        /*
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(OnMapReadyCallback { googleMap ->
            val userLocation = getLocationFromAddress(this.requireContext(), address)
            googleMap.addMarker(MarkerOptions().position(userLocation).title("Marker in Reciver Location"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 13f)) })
        */

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get user info from arguments
        val name = arguments?.getString("user name").toString()
        val surname = arguments?.getString("user surname").toString()
        val address = arguments?.getString("user address").toString()
        val cellular = arguments?.getString("user cellular").toString()
        val email = arguments?.getString("user email").toString()

        //set text view
        userNameView.text = name
        userSurnameView.text = surname
        userAddressView.text = address
        userCellularView.text = cellular
        userEmailView.text = email

        //map
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(OnMapReadyCallback { googleMap ->
            Log.v(TAG, "OnMapReadyCallback - address: $address")
            val userLocation = getLocationFromAddress(this.requireContext(), address)
            googleMap.addMarker(MarkerOptions().position(userLocation).title("Marker in Reciver Location"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 13f))
        })

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
package com.example.qrecipecorriere

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import kotlinx.android.synthetic.main.fragment_ingredients.*
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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

    }

}
package com.example.qrecipecorriere

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import kotlinx.android.synthetic.main.fragment_ingredients.*

class IngredientsFragment : Fragment() {

    private val TAG = "IngredientsFragment"

    private val m = MainActivity()

    private var arrived = false
    private var checked = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredients, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init flag
        arrived = false
        checked = 0

        //get order info from bundle arguments
        val ingredients: List<String> = arguments?.getString("ingredients").toString().split("-")

        //pass ingredients to adapter
        ingredientsListView.adapter = SecondAdapter(this.requireContext(), ingredients)

        //enable button when all check box are checked
        sendButton.isEnabled = false

        ingredientsListView.setOnItemClickListener { parent, view, position, id ->
            val checkBox = view.findViewById<CheckBox>(R.id.checkBox)

            if (checkBox.isChecked) {
                checkBox.isChecked = false
                checked--
            } else {
                checkBox.isChecked = true
                checked++
            }

            sendButton.isEnabled = (checked == ingredients.size)
        }

        //handle pack delivery
        sendButton.setOnClickListener {
            //update arrived flag
            arrived = true

            //update order state in firebase (orderState = "arrived")
            m.changeOrderStateToArrived(arguments?.getString("order id").toString())

            //return to main activity
            activity?.finish()
        }
    }

    fun getArrived(): Boolean {
        return arrived
    }

}
package com.example.qrecipecorriere

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private val list = ArrayList<Order>()
    private val order = Order("ing1:50 g-ing2:6-ing3:120 g", "Mattia", "Busin", "Crosio della Valle, via S.Apollinare 19", "3471234567", "businmattiagmail.com", "placed")
    private val order2 = Order("ing1:50 g-ing2:6-ing3:120 g", "Jacopo", "Canavesi", "Vedano", "3478239287", "canavesijacop@gmail.com", "placed")
    private val adapter = MainAdapter(this, list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ----- TEST -----
        if(order.orderState == "placed")
            list.add(order)
        if(order2.orderState == "placed")
            list.add(order2)

        list.forEach {
            Log.v(TAG, it.toString())
        }

        placedOrdersView.adapter = adapter

        //TODO: get order from firebase while order state = "placed"

        //TODO: populate list view

        //on item clicked start second activity
        placedOrdersView.setOnItemClickListener { parent, view, position, id ->
            // ----- TEST -----
            //order taken in charge by a courier
            val temp = list[position]
            temp.orderState = "in charge"
            list.removeAt(position)
            adapter.notifyDataSetChanged()
            Log.v(TAG, temp.toString())

            //TODO: update order state in firebase (orderState = "in charge")

            //create intent
            val intent = Intent(this, SecondActivity::class.java)

            //put order info in the intent
            intent.putExtra("order id", temp.id)
            intent.putExtra("order ingredients", temp.ingredients)
            intent.putExtra("order user name", temp.userName)
            intent.putExtra("order user surname", temp.userSurname)
            intent.putExtra("order user address", temp.userAddress)
            intent.putExtra("order user cellular", temp.userCellular)
            intent.putExtra("order user email", temp.userEmail)
            intent.putExtra("order place date", temp.placeDate)
            intent.putExtra("order arrive date", temp.arriveDate)
            intent.putExtra("order state", temp.orderState)

            //start second activity
            startActivity(intent)
        }
    }
}
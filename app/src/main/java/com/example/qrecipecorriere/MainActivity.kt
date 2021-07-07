package com.example.qrecipecorriere

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    //get order from firebase
    private var mUserReference: DatabaseReference? = FirebaseDatabase.getInstance("https://qrecipeprovalayout-2aed1-default-rtdb.firebaseio.com/").getReference("order")
    private var mUsersChildListener: ChildEventListener = getUsersChildEventListener()

    private val placedOrders = ArrayList<Order>()
    private val adapter = MainAdapter(this, placedOrders)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //populate list view
        placedOrdersView.adapter = adapter

        //on item clicked start second activity
        placedOrdersView.setOnItemClickListener { parent, view, position, id ->
            //TODO: update order state in firebase (orderState = "in charge")

            //create intent to second activity with the selected order and start second activity
            val intent = Intent(this, SecondActivity::class.java)

            intent.putExtra("order id", placedOrders[position].id)
            intent.putExtra("order ingredients", placedOrders[position].ingredients)
            intent.putExtra("order user name", placedOrders[position].userName)
            intent.putExtra("order user surname", placedOrders[position].userSurname)
            intent.putExtra("order user address", placedOrders[position].userAddress)
            intent.putExtra("order user cellular", placedOrders[position].userCellular)
            intent.putExtra("order user email", placedOrders[position].userEmail)
            intent.putExtra("order place date", placedOrders[position].placeDate)
            intent.putExtra("order arrive date", placedOrders[position].arriveDate)
            intent.putExtra("order state", placedOrders[position].orderState)

            Log.v(TAG, placedOrders[position].toString())

            //startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        if(mUsersChildListener == null)
            mUsersChildListener = getUsersChildEventListener()
        mUserReference!!.addChildEventListener(mUsersChildListener)
    }

    override fun onStop() {
        super.onStop()
        if(mUsersChildListener != null)
            mUserReference!!.removeEventListener(mUsersChildListener)
    }

    private fun getUsersChildEventListener(): ChildEventListener {
        val childEventListener = object: ChildEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "onCancelled: " + error.toException())
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildMoved: " + snapshot.key!!)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildChanged: " + snapshot.key!!)

                //insert into newOrder the info of the changed child and cast into a Order
                val newOrder = snapshot.getValue(Order::class.java)

                //insert into orderKey the key of the changed child
                val orderKey = snapshot.key

                //case "placed" -> "in charge"
                if(newOrder?.orderState == "in charge")
                    placedOrders.remove(placedOrders.find { o -> o.id == orderKey })

                //case "in charge" -> "placed"
                if(newOrder?.orderState == "placed")
                    placedOrders.add(newOrder)

                adapter.notifyDataSetChanged()
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d(TAG, "onChildAdded: " + snapshot.key!!)

                //insert into new Order the info of the added order and cast into a Order
                val newOrder = snapshot.getValue(Order::class.java)

                //add the new order into the list
                if(newOrder?.orderState == "placed")
                    placedOrders.add(newOrder)

                adapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                Log.d(TAG, "onChildRemoved: " + snapshot.key!!)

                //insert into orderKey the key of the removed child
                val orderKey = snapshot.key

                //find the removed child in the list and remove it
                placedOrders.remove(placedOrders.find { o -> o.id == orderKey })

                adapter.notifyDataSetChanged()
            }

        }

        return childEventListener
    }

    //TODO: in teoria non serve, controllare
    fun addOrderToList(o: Order) {
        if(o.orderState == "placed")
            placedOrders.add(o)
        adapter.notifyDataSetChanged()
        Log.v(TAG, "added not arrived order: ${o.id}, to list")
    }

}
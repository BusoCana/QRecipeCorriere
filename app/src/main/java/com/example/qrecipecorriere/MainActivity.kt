package com.example.qrecipecorriere

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    //get order from firebase
    private var mOrderReference: DatabaseReference? = FirebaseDatabase.getInstance("https://qrecipeprovalayout-2aed1-default-rtdb.firebaseio.com/").getReference("order")
    private var mOrderChildListener: ChildEventListener = getUsersChildEventListener()

    private val placedOrders = ArrayList<Order>()
    private val adapter = MainAdapter(this, placedOrders)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //populate list view
        placedOrdersView.adapter = adapter

        //on item clicked start second activity
        placedOrdersView.setOnItemClickListener { parent, view, position, id ->
            val selectedOrder: Order = placedOrders[position]



            //update order state in firebase (orderState = "in charge")
            selectedOrder.orderState = "in charge"
            placedOrders.clear()
            mOrderReference!!.child(selectedOrder.id).child("orderState").setValue("in charge")

            //create intent to second activity with the selected order and start second activity
            val intent = Intent(this, SecondActivity::class.java)

            intent.putExtra("order id", selectedOrder.id)
            intent.putExtra("order ingredients", selectedOrder.ingredients)
            intent.putExtra("order user name", selectedOrder.userName)
            intent.putExtra("order user surname", selectedOrder.userSurname)
            intent.putExtra("order user address", selectedOrder.userAddress)
            intent.putExtra("order user cellular", selectedOrder.userCellular)
            intent.putExtra("order user email", selectedOrder.userEmail)
            intent.putExtra("order place date", selectedOrder.placeDate)
            intent.putExtra("order arrive date", selectedOrder.arriveDate)
            intent.putExtra("order state", selectedOrder.orderState)

            Log.v(TAG, selectedOrder.toString())
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        if(mOrderChildListener == null)
            mOrderChildListener = getUsersChildEventListener()
        mOrderReference!!.addChildEventListener(mOrderChildListener)
    }

    override fun onStop() {
        super.onStop()
        if(mOrderChildListener != null)
            mOrderReference!!.removeEventListener(mOrderChildListener)
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

    fun changeOrderStateToPlaced(id: String) {
        mOrderReference!!.child(id).child("orderState").setValue("placed")
    }

    fun changeOrderStateToArrived(id: String) {
        mOrderReference!!.child(id).child("orderState").setValue("arrived")
        mOrderReference!!.child(id).child("arriveDate").setValue(getCurrentDateTime().toString("dd/MM/yyyy HH:mm:ss"))
    }

    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

}
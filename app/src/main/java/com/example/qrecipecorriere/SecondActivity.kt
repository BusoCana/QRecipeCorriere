package com.example.qrecipecorriere

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    private val TAG = "SecondActivity"

    private val m = MainActivity()
    private lateinit var order: Order

    private var checked = 0
    private var arrived = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        setSupportActionBar(toolbar)


        toolbar.setNavigationOnClickListener {
            //TODO: update order state in firebase (orderState = "placed")

            if(!arrived) {
                order.orderState = "placed"
                m.addOrderToList(order)
                Log.v(TAG, "Send not arrived order: ${order.id}, to main activity")
            }

            //close second activity
            this.finish()
        }

        //take order info from MainActivity intent
        val id = intent.getStringExtra("order id").toString()
        val ingredients = intent.getStringExtra("order ingredients").toString()
        val userName = intent.getStringExtra("order user name").toString()
        val userSurname = intent.getStringExtra("order user surname").toString()
        val userAddress = intent.getStringExtra("order user address").toString()
        val userCellular = intent.getStringExtra("order user cellular").toString()
        val userEmail = intent.getStringExtra("order user email").toString()
        val placeDate = intent.getStringExtra("order place date").toString()
        val arriveDate = intent.getStringExtra("order arrive date").toString()
        val orderState = intent.getStringExtra("order state").toString()

        order = Order(id, ingredients, userName, userSurname, userAddress, userCellular, userEmail, placeDate, arriveDate, orderState)

        //split different ingredients
        val ingredientsPart = order.ingredients.split("-")
        Log.v(TAG, order.toString() + "\nIngredient size: ${ingredientsPart.size}")

        // pass data to the Adapter
        ingredientsListView.adapter = SecondAdapter(this, ingredientsPart)

        //enable button when all check box are checked
        sendButton.isEnabled = false

        ingredientsListView.setOnItemClickListener { parent, view, position, id ->
            val checkBox = view.findViewById<CheckBox>(R.id.checkBox)

            Log.v(TAG, "cont before: $checked")

            if(checkBox.isChecked) {
                checkBox.isChecked = false
                checked--
            } else {
                checkBox.isChecked = true
                checked++
            }

            Log.v(TAG, "cont after: $checked")

            //enabled button if all check box are checked
            sendButton.isEnabled = (checked == ingredientsPart.size)
        }

        //handle button pressed
        sendButton.setOnClickListener {
            Toast.makeText(this, "Order arrived to user address", Toast.LENGTH_SHORT).show()
            Log.v(TAG, "Order arrived to user address")
            arrived = true

            //TODO: update order state in firebase (orderState = "arrived")
        }

    }


}

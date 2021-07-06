package com.example.qrecipecorriere

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    private lateinit var order: Order

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            //TODO: update order state in firebase (orderState = "placed")

            //handle navigation icon press
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

        // pass data to the Adapter
        ingredientsListView.adapter = SecondAdapter(this, ingredientsPart)

        //TODO : attivare il bottone quando tutte lecheckbox sono selezionate
        sendButton.isEnabled = false


        //TODO : cambiare lo stato dell'ordine
        sendButton.setOnClickListener {
        }

    }


}

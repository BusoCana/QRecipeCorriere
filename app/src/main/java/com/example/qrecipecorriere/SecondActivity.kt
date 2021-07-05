package com.example.qrecipecorriere

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    private lateinit var recipe : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            //handle navigation icon press
            this.finish()
        }


        //take intent info from MainActivity
        recipe = intent.getStringExtra("recipe ingredients").toString()

        //split different ingredients
        val parts = recipe.split("-")

        // pass data to the Adapter
        ingredientsListView.adapter = MyAdapter(this, parts)

        //TODO : attivare il bottone quando tutte lecheckbox sono selezionate
        sendButton.isEnabled=false


        //TODO : cambiare lo stato dell'ordine
        sendButton.setOnClickListener {
        }

    }


}

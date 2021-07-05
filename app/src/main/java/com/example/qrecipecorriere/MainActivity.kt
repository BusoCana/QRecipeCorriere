package com.example.qrecipecorriere

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private var recipe: MutableList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        getRecipeInfo()
    }


    //funzione che prende gli ingredienti dell'ordine selezionato e li invia con intent alla seconda
    private  fun  getRecipeInfo() {
        database = FirebaseDatabase.getInstance("https://qrecipeprovalayout-2aed1-default-rtdb.firebaseio.com/").getReference("order")

        //TODO CAMBIARE PATHSTRING CON NOME DELL'ORDINE CLICCATO
        database.child("canavesi-06072021-005043").get().addOnCompleteListener {
            //if it successfully read a data enter into the if
            if (it.isSuccessful) {
                //if exist a recipe == recipe_name enter into the if
                if (it.result?.exists() == true) {
                    //insert result value into data snapshot
                    val snapshot: DataSnapshot = it.result!!

                    //get ingredients  from firebase and put it into intent and send info to second activity
                    val intent = Intent(this, SecondActivity::class.java)
                    intent.putExtra("recipe ingredients", snapshot.child("ingredients").value.toString())

                    //start second activity
                    startActivity(intent)
                }
            }
        }
    }
}
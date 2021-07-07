package com.example.qrecipecorriere

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    private val TAG = "SecondActivity"

    private val ingredientsFragment = IngredientsFragment()
    private val userFragment = UserFragment()
    private val ingredientsBundle = Bundle()
    private val userBundle = Bundle()

    private val m = MainActivity()
    private lateinit var order: Order

    private var arrived = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        setSupportActionBar(toolbar)

        Log.v(TAG, "onCreate")

        //init order
        initOrder()

        toolbar.setNavigationOnClickListener {
            //update order state in firebase (orderState = "placed")
            if(!arrived)
                m.changeOrderStateToPlaced(order.id)

            //close second activity
            this.finish()
        }

        //bottom navigation bar listener
        bottom_nav.setOnNavigationItemSelectedListener(){
            when(it.itemId) {
                R.id.item_ingredients -> replaceFragments(ingredientsFragment)
                R.id.item_user -> replaceFragments(userFragment)
            }
            true
        }

    }

    private fun initOrder() {
        Log.v(TAG, "initOrder")

        //init order
        order = Order(intent.getStringExtra("order id").toString(),
            intent.getStringExtra("order ingredients").toString(),
            intent.getStringExtra("order user name").toString(),
            intent.getStringExtra("order user surname").toString(),
            intent.getStringExtra("order user address").toString(),
            intent.getStringExtra("order user cellular").toString(),
            intent.getStringExtra("order user email").toString(),
            intent.getStringExtra("order place date").toString(),
            intent.getStringExtra("order arrive date").toString(),
            intent.getStringExtra("order state").toString())

        //update title view
        titleIDValue.text = order.id

        //pass arguments to fragments
        ingredientsBundle.putString("order id", order.id)
        ingredientsBundle.putString("ingredients", order.ingredients)
        ingredientsFragment.arguments = ingredientsBundle

        userBundle.putString("user name", order.userName)
        userBundle.putString("user surname", order.userSurname)
        userBundle.putString("user address", order.userAddress)
        userBundle.putString("user cellular", order.userCellular)
        userBundle.putString("user email", order.userEmail)
        userFragment.arguments = userBundle

        replaceFragments(ingredientsFragment)
    }

    //bottom navigation fragment replace
    private fun replaceFragments (fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentView, fragment)
        transaction.commit()
    }


}

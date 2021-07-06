package com.example.qrecipecorriere

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MainAdapter(private val context: Context, private val data: ArrayList<Order>): BaseAdapter() {
    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var newView = convertView

        if(convertView == null)
            newView = LayoutInflater.from(context).inflate(R.layout.row_main, parent, false)
        if(newView != null) {
            //insert order into main activity list only if order state is "placed"
            if(data[position].orderState == "placed") {
                //find view in row_main.xml
                val user = newView.findViewById<TextView>(R.id.userView)
                val orderID = newView.findViewById<TextView>(R.id.orderIdView)
                val orderPlaceDay = newView.findViewById<TextView>(R.id.orderDayView)
                val orderPlaceHours = newView.findViewById<TextView>(R.id.orderHoursView)

                //add text to list view row
                val dateParts = data[position].placeDate.split(" ")
                user.text = data[position].userSurname + " " + data[position].userName
                orderID.text = data[position].id
                orderPlaceDay.text = dateParts[0]
                orderPlaceHours.text = dateParts[1]
            }
        }

        return newView
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }

}
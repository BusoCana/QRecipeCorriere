package com.example.qrecipecorriere

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SecondAdapter (private val context: Context, private val data: List<String>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var newView = convertView

        if(convertView == null)
            newView = LayoutInflater.from(context).inflate(R.layout.row, parent, false)
        if(newView != null) {
            //find the the textView in row.xml
            val ingrName = newView.findViewById<TextView>(R.id.ingredientNameView)
            val ingrNumber = newView.findViewById<TextView>(R.id.ingredientNumberView)

            //add text to List row's TextView
            val parts = data[position].split(":")
            ingrName.text = parts[0]
            ingrNumber.text = parts[1]
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
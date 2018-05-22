package com.fabianofranca.flight.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.fabianofranca.flight.R

class SpinnerAdapter(context: Context, private val groupId: Int, list: List<Int>) :
    ArrayAdapter<Int>(context, groupId, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(groupId, parent, false)

        val hint = view.findViewById<TextView>(R.id.hint_text)
        val item = view.findViewById<TextView>(R.id.item_text)
        item.text = position.toString()

        if (position == 0) {
            hint.visibility = View.VISIBLE
            item.visibility = View.GONE
        } else {
            hint.visibility = View.GONE
            item.visibility = View.VISIBLE
        }

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getView(position, convertView, parent)
    }
}
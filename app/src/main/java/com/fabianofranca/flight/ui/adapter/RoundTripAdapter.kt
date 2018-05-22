package com.fabianofranca.flight.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fabianofranca.flight.R
import com.fabianofranca.flight.business.model.RoundTrip

class RoundTripAdapter : RecyclerView.Adapter<RoundTripAdapter.ViewHolder>() {

    private val roundTrips = mutableListOf<RoundTrip>()

    fun update(roundTrips: List<RoundTrip>) {
        this.roundTrips.clear()
        this.roundTrips.addAll(roundTrips)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val container =
            LayoutInflater.from(parent.context).inflate(R.layout.round_trip_item, parent, false)

        return ViewHolder(container)
    }

    override fun getItemCount(): Int {
        return roundTrips.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(roundTrips[position])
    }

    class ViewHolder(val container: View) : RecyclerView.ViewHolder(container) {

        fun bind(roundTrip: RoundTrip) {
            container.findViewById<TextView>(R.id.airline_text).text =
                    roundTrip.inboundFlight.airline

            container.findViewById<TextView>(R.id.price_text).text = roundTrip.price.toString()
        }
    }
}
package com.fabianofranca.flight.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.fabianofranca.flight.R
import com.fabianofranca.flight.business.model.RoundTrip
import java.text.NumberFormat

class RoundTripAdapter(val context: Context) : RecyclerView.Adapter<RoundTripAdapter.ViewHolder>() {

    private val roundTrips = mutableListOf<RoundTrip>()

    fun update(roundTrips: List<RoundTrip>) {
        this.roundTrips.clear()
        this.roundTrips.addAll(roundTrips)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val container =
            LayoutInflater.from(parent.context).inflate(R.layout.round_trip_item, parent, false)

        return ViewHolder(container, context)
    }

    override fun getItemCount(): Int {
        return roundTrips.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(roundTrips[position])
    }

    class ViewHolder(val container: View, val context: Context) :
        RecyclerView.ViewHolder(container) {

        fun bind(roundTrip: RoundTrip) {

            with(roundTrip.outboundFlight) {
                container.binding(R.id.date_out_txt, date)
                container.binding(R.id.airline_out_txt, airline)
                container.binding(R.id.time_out_txt, departureTime)
                container.binding(R.id.duration_out_txt, duration)
                container.binding(R.id.arrival_out_txt, arrivalTime)
                container.binding(R.id.code_out_txt, flightNumber)
                container.binding(R.id.departure_out_txt, departure)
                container.binding(
                    R.id.type_out_txt,
                    context.getString(if (hasStop) R.string.stop else R.string.no_stop)
                )
                container.binding(R.id.destination_out_txt, destination)
            }

            with(roundTrip.inboundFlight) {
                container.binding(R.id.date_in_txt, date)
                container.binding(R.id.airline_in_txt, airline)
                container.binding(R.id.time_in_txt, departureTime)
                container.binding(R.id.duration_in_txt, duration)
                container.binding(R.id.arrival_in_txt, arrivalTime)
                container.binding(R.id.code_in_txt, flightNumber)
                container.binding(R.id.departure_in_txt, departure)
                container.binding(
                    R.id.type_in_txt,
                    context.getString(if (hasStop) R.string.stop else R.string.no_stop)
                )
                container.binding(R.id.destination_in_txt, destination)
            }

            val format = NumberFormat.getCurrencyInstance()

            container.findViewById<Button>(R.id.buy_button).text = format.format(roundTrip.price)
        }

        private fun View.binding(id: Int, value: String) {
            this.findViewById<TextView>(id).text = value
        }
    }
}
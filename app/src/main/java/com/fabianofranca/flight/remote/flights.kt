package com.fabianofranca.flight.remote

import android.annotation.SuppressLint
import com.fabianofranca.flight.remote.model.SearchData
import java.text.SimpleDateFormat
import java.util.*

interface FlightsRemote {

    fun search(
        source: String, destination: String, dateOfDeparture: Date,
        dateOfArrival: Date? = null,
        adults: Int = ApiConstants.ADULTS
    ): Request<SearchData>
}

class FlightsRemoteImpl(private val api: Api) : FlightsRemote {

    @SuppressLint("SimpleDateFormat")
    override fun search(
        source: String,
        destination: String,
        dateOfDeparture: Date,
        dateOfArrival: Date?,
        adults: Int
    ): Request<SearchData> {

        val dateFormat = SimpleDateFormat(ApiConstants.DATE_FORMAT)

        val departure = dateFormat.format(dateOfDeparture)
        val arrival: String? = dateOfArrival?.let { dateFormat.format(dateOfArrival) }

        return api.search(source, destination, departure, dateOfArrival = arrival)
    }
}
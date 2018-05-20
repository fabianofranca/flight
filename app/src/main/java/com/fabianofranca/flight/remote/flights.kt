package com.fabianofranca.flight.remote

import android.annotation.SuppressLint
import com.fabianofranca.flight.business.Constants
import com.fabianofranca.flight.business.model.RoundTrip
import com.fabianofranca.flight.infrastructure.Async
import java.text.SimpleDateFormat
import java.util.*

interface FlightsRemote {

    fun search(
        source: String, destination: String, dateOfDeparture: Date,
        dateOfArrival: Date? = null,
        adults: Int = Constants.ADULTS
    ): Async<Set<RoundTrip>>
}

class FlightsRemoteImpl(private val api: Api) : FlightsRemote {

    @SuppressLint("SimpleDateFormat")
    override fun search(
        source: String,
        destination: String,
        dateOfDeparture: Date,
        dateOfArrival: Date?,
        adults: Int
    ): Async<Set<RoundTrip>> = remote {
        val dateFormat = SimpleDateFormat(ApiConstants.DATE_FORMAT)

        val departure = dateFormat.format(dateOfDeparture)
        val arrival: String? = dateOfArrival?.let { dateFormat.format(dateOfArrival) }

        api.search(source, destination, departure, dateOfArrival = arrival).await()
            .data.onwardFlights
    }
}
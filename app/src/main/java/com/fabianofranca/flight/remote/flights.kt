package com.fabianofranca.flight.remote

import android.annotation.SuppressLint
import com.fabianofranca.flight.business.Constants
import com.fabianofranca.flight.business.model.RoundTrip
import com.fabianofranca.flight.infrastructure.Async
import com.fabianofranca.flight.remote.tools.remote
import java.text.SimpleDateFormat
import java.util.*

interface FlightsRemote {

    fun search(
        source: String,
        destination: String,
        departureDate: Date,
        arrivalDate: Date? = null,
        adults: Int = Constants.ADULTS
    ): Async<Set<RoundTrip>>
}

class FlightsRemoteImpl(private val api: Api) : FlightsRemote {

    @SuppressLint("SimpleDateFormat")
    override fun search(
        source: String,
        destination: String,
        departureDate: Date,
        arrivalDate: Date?,
        adults: Int
    ): Async<Set<RoundTrip>> = remote {
        val dateFormat = SimpleDateFormat(ApiConstants.DATE_FORMAT)

        val departure = dateFormat.format(departureDate)
        val arrival: String? = arrivalDate?.let { dateFormat.format(arrivalDate) }

        api.search(source, destination, departure, arrivalDate = arrival).await()
            .data.onwardFlights
    }
}
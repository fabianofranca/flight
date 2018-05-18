package com.fabianofranca.flight.providers

import android.annotation.SuppressLint
import com.fabianofranca.flight.providers.api.Api
import com.fabianofranca.flight.providers.api.ApiConstants
import com.fabianofranca.flight.providers.api.Request
import java.text.SimpleDateFormat
import java.util.*

interface FlightsProvider {

    fun search(
        source: String, destination: String, dateOfDeparture: Date,
        dateOfArrival: Date? = null,
        adults: Int = ApiConstants.ADULTS
    ): Request<SearchData>
}

class FlightsProviderImpl(private val api: Api) : FlightsProvider {

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

        return api.search(source, destination, departure, dateOfArrival =  arrival)
    }
}
package com.fabianofranca.flight.business

import com.fabianofranca.flight.business.model.RoundTrip
import com.fabianofranca.flight.infrastructure.Async
import com.fabianofranca.flight.remote.FlightsRemote
import java.util.*

interface RoundTripsRepository {

    fun search(
        departure: String, destination: String, dateOfDeparture: Date,
        dateOfArrival: Date? = null,
        adults: Int = Constants.ADULTS
    ): Async<Set<RoundTrip>>

}

class RoundTripsRepositoryImpl(private val remote: FlightsRemote) : RoundTripsRepository {
    override fun search(
        departure: String,
        destination: String,
        dateOfDeparture: Date,
        dateOfArrival: Date?,
        adults: Int
    ): Async<Set<RoundTrip>> = remote.search(departure, destination, dateOfDeparture, dateOfArrival)
}
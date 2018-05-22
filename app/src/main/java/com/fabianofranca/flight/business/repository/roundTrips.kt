package com.fabianofranca.flight.business.repository

import com.fabianofranca.flight.business.Constants
import com.fabianofranca.flight.business.model.RoundTrip
import com.fabianofranca.flight.infrastructure.Async
import com.fabianofranca.flight.remote.FlightsRemote
import java.util.*
import javax.inject.Inject

interface RoundTripsRepository {

    fun search(
        departure: String,
        destination: String,
        departureDate: Date,
        arrivalDate: Date? = null,
        adults: Int = Constants.ADULTS
    ): Async<Set<RoundTrip>>
}

class RoundTripsRepositoryImpl @Inject constructor(private val remote: FlightsRemote) :
    RoundTripsRepository {
    override fun search(
        departure: String,
        destination: String,
        departureDate: Date,
        arrivalDate: Date?,
        adults: Int
    ): Async<Set<RoundTrip>> = remote.search(departure, destination, departureDate, arrivalDate)
}

fun Array<RoundTrip>.airlines() = this.map({ it.inboundFlight.airline }).distinct().sorted()

fun List<RoundTrip>.filterByAirLine(airlines: Set<String>) =
    this.filter { airlines.contains(it.inboundFlight.airline) }
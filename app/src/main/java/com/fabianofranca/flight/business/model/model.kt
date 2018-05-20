package com.fabianofranca.flight.business.model

data class RoundTrip(var outboundFlight: Flight, var inboundFlight: Flight, var price: Double) :
    Comparable<RoundTrip> {
    override fun compareTo(other: RoundTrip): Int {
        if (price < other.price) return -1
        if (price > other.price) return 1
        return 0
    }
}

data class Flight(
    var date: String,
    var airline: String,
    var departureTime: String,
    var duration: String,
    var arrivalTime: String,
    var flightNumber: String,
    var departure: String,
    var hasStop: Boolean,
    var destination: String
)
package com.fabianofranca.flight.business.model

data class RoundTrip(var outboundFlight: Flight, var inboundFlight: Flight, var price: Number)

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
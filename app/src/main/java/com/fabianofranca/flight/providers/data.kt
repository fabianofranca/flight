package com.fabianofranca.flight.providers

import com.google.gson.annotations.SerializedName

data class SearchData(var data: Data)

data class Data(@SerializedName("onwardflights") var onwardFlights: Set<FlightData>)

data class FlightData(@SerializedName("flightcode") var flightCode: Int)
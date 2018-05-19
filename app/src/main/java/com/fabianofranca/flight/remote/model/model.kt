package com.fabianofranca.flight.remote.model

import com.fabianofranca.flight.business.model.RoundTrip
import com.google.gson.annotations.SerializedName

data class SearchData(var data: Data)

data class Data(@SerializedName("onwardflights") var onwardFlights: Set<RoundTrip>)
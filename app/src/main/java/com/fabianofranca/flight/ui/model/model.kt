package com.fabianofranca.flight.ui.model

import com.fabianofranca.flight.business.Constants
import java.util.*

data class Search(
    var departure: String,
    var destination: String,
    var departureDate: Date,
    var arrivalDate: Date? = null,
    var adults: Int = Constants.ADULTS
)
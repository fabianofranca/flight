package com.fabianofranca.flight.ui.model

import android.os.Parcelable
import com.fabianofranca.flight.business.Constants
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Search(
    var departure: String,
    var destination: String,
    var departureDate: Date,
    var arrivalDate: Date? = null,
    var adults: Int = Constants.ADULTS
): Parcelable
package com.fabianofranca.flight.business

import com.fabianofranca.flight.business.model.Flight
import com.fabianofranca.flight.business.model.RoundTrip
import com.fabianofranca.flight.tools.BaseTest
import org.junit.Test

class RoundTripTest : BaseTest() {

    private val flight = Flight("", "", "", "", "", "", "", false, "")

    private fun roundTrip(number: Double) = RoundTrip(flight, flight, number)

    private val trip1 = roundTrip(1.0)
    private val trip2 = roundTrip(2.0)
    private val trip3 = roundTrip(3.0)
    private val trip4 = roundTrip(4.0)
    private val trip5 = roundTrip(5.0)

    private val randomTrips = setOf(trip3, trip5, trip1, trip4, trip2)
    private val ascendTrips = arrayOf(trip1, trip2, trip3, trip4, trip5)
    private val descendTrips = arrayOf(trip5, trip4, trip3, trip2, trip1)

    @Test
    fun roundTrip_shouldSortAscending() {
        assert(ascendTrips contentEquals randomTrips.sorted().toTypedArray())
    }

    @Test
    fun roundTrip_shouldSortDescending() {
        assert(descendTrips contentEquals randomTrips.sortedDescending().toTypedArray())
    }
}
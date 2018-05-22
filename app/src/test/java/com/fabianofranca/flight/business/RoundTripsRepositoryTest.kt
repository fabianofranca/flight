package com.fabianofranca.flight.business

import com.fabianofranca.flight.business.model.Flight
import com.fabianofranca.flight.business.model.RoundTrip
import com.fabianofranca.flight.business.repository.RoundTripsRepository
import com.fabianofranca.flight.business.repository.RoundTripsRepositoryImpl
import com.fabianofranca.flight.business.repository.airlines
import com.fabianofranca.flight.business.repository.filterByAirLine
import com.fabianofranca.flight.remote.FlightsRemote
import com.fabianofranca.flight.tools.BaseTest
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import java.util.*

class RoundTripsRepositoryTest : BaseTest() {

    @Mock
    lateinit var remote: FlightsRemote

    private lateinit var repository: RoundTripsRepository

    private val tripA = roundTrip("a")
    private val tripB = roundTrip("b")
    private val tripC = roundTrip("c")

    private val randomTrips = arrayOf(tripC, tripA, tripB)

    private fun flight(airline: String) = Flight("", airline, "", "", "", "", "", false, "")

    private fun roundTrip(airline: String) = RoundTrip(flight(airline), flight(airline), 0.0)

    override fun setup() {
        super.setup()

        repository = RoundTripsRepositoryImpl(remote)
    }

    @Test
    fun roundTripsRepository_shouldReturnRoundTrips() {
        val date = Calendar.getInstance().time

        Mockito.`when`(remote.search("", "", date, null, Constants.ADULTS))
            .thenReturn(async(setOf()))

        repository.search("", "", date)

        verify(remote).search("", "", date, null, Constants.ADULTS)
    }

    @Test
    fun roundTripsRepository_shouldReturnArlines() {

        val ascendingAirlines = arrayOf("a", "b", "c")

        assert(ascendingAirlines contentEquals randomTrips.airlines().toTypedArray())
    }

    @Test
    fun roundTripsRepository_shouldFilter() {
        val filteredTrips = arrayOf(tripA)

        assert(filteredTrips contentEquals randomTrips.toList().filterByAirLine(setOf("a")).toTypedArray())
    }

}
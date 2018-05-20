package com.fabianofranca.flight.business

import com.fabianofranca.flight.business.repository.RoundTripsRepository
import com.fabianofranca.flight.business.repository.RoundTripsRepositoryImpl
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
}
package com.fabianofranca.flight.business

import com.fabianofranca.flight.remote.FlightsRemote
import com.fabianofranca.flight.utils.AsyncTestRule
import com.fabianofranca.flight.utils.BaseTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import java.util.*

class RoundTripsRepositoryTest : BaseTest() {

    @Rule
    @JvmField
    val asyncTestRule = AsyncTestRule()

    @Mock
    lateinit var remote: FlightsRemote

    private lateinit var repository: RoundTripsRepository

    override fun setup() {
        super.setup()

        repository = RoundTripsRepositoryImpl(remote)
    }

    @Test
    fun search_shouldReturnRoundTrips() {
        val date = Calendar.getInstance().time

        Mockito.`when`(remote.search("", "", date, null, Constants.ADULTS))
            .thenReturn(async(setOf()))

        repository.search("", "", date)

        verify(remote).search("", "", date, null, Constants.ADULTS)
    }
}
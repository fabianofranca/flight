package com.fabianofranca.flight.ui

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.fabianofranca.flight.business.model.Flight
import com.fabianofranca.flight.business.model.RoundTrip
import com.fabianofranca.flight.business.repository.RoundTripsRepository
import com.fabianofranca.flight.business.repository.airlines
import com.fabianofranca.flight.infrastructure.Async
import com.fabianofranca.flight.tools.AsyncTestRule
import com.fabianofranca.flight.tools.BaseTest
import com.fabianofranca.flight.ui.model.Search
import com.fabianofranca.flight.ui.viewModel.ResultViewModel
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import java.util.*
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ResultViewModelTest : BaseTest() {

    @Rule
    @JvmField
    val asyncTestRule = AsyncTestRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: RoundTripsRepository

    private lateinit var viewModel: ResultViewModel

    private val date = Calendar.getInstance().time

    private val trips = spy(setOf(roundTrip("a")))

    private fun flight(airline: String) = Flight("", airline, "", "", "", "", "", false, "")

    private fun roundTrip(airline: String) = RoundTrip(flight(airline), flight(airline), 0.0)

    override fun setup() {
        super.setup()

        viewModel = ResultViewModel(repository)

        `when`(repository.search("", "", date)).thenReturn(Async.create { trips })
    }

    @Test
    fun resultViewModel_shouldSearchRoundTrips() {

        viewModel.init(Search("", "", date))

        assertNotNull(viewModel.roundTrips.value)
    }

    @Test
    fun resultViewModel_shouldHandleException() {

        `when`(repository.search("", "", date)).thenReturn(Async.create { throw Exception() })

        viewModel.init(Search("", "", date))

        assertTrue(viewModel.handleException.value!!)
    }

    @Test
    fun resultViewModel_shouldSortAscending() {
        viewModel.init(Search("", "", date))

        viewModel.sortAscending()

        verify(trips, atLeastOnce()).sorted()
    }

    @Test
    fun resultViewModel_shouldSortDescending() {
        viewModel.init(Search("", "", date))

        viewModel.sortDescending()

        verify(trips, atLeastOnce()).sortedDescending()
    }

    @Test
    fun resultViewModel_shouldCallAirlines() {
        viewModel.init(Search("", "", date))

        verify(trips, atLeastOnce()).airlines()

        assertNotNull(viewModel.airlines)
    }

    @Test
    fun resultViewModel_shouldFilter() {
        viewModel.init(Search("", "", date))

        viewModel.filter("b")

        assert(viewModel.roundTrips.value?.size == 0)

        viewModel.clearFilter()

        assert(viewModel.roundTrips.value?.size == 1)
    }

}
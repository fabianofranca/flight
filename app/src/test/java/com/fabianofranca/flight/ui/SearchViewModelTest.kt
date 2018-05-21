package com.fabianofranca.flight.ui

import com.fabianofranca.flight.tools.BaseTest
import com.fabianofranca.flight.ui.viewModel.SearchViewModel
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class SearchViewModelTest : BaseTest() {

    private fun assertDate(date: Date) {
        val calendar = Calendar.getInstance()
        calendar.time = date

        assertEquals(calendar.get(Calendar.DAY_OF_MONTH), 1)
        assertEquals(calendar.get(Calendar.MONTH), 0)
        assertEquals(calendar.get(Calendar.YEAR), 2018)
    }

    @Test
    fun searchViewModel_shouldSearching() {

        val viewModel = SearchViewModel({
            assertEquals(it.departure, "A")
            assertEquals(it.destination, "A")
            assertEquals(it.adults, 6)
        })

        viewModel.search("A", "A", 6)
    }

    @Test
    fun searchViewModel_shouldSetDepartureDate() {
        val viewModel = SearchViewModel({
            assertDate(it.departureDate)
        })

        viewModel.departureDate(2018, 0, 1)

        assertEquals(viewModel.departureDate.value, "01/01/2018")

        viewModel.search("", "", 0)
    }

    @Test
    fun searchViewModel_shouldSetArrivalDate() {
        val viewModel = SearchViewModel({
            assertDate(it.arrivalDate!!)
        })

        viewModel.arrivalDate(2018, 0, 1)

        assertEquals(viewModel.arrivalDate.value, "01/01/2018")

        viewModel.search("", "", 0)
    }
}
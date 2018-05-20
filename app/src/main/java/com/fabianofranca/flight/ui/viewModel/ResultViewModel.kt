package com.fabianofranca.flight.ui.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.fabianofranca.flight.business.model.RoundTrip
import com.fabianofranca.flight.business.repository.RoundTripsRepository
import com.fabianofranca.flight.business.repository.airlines
import com.fabianofranca.flight.business.repository.filterByAirLine
import com.fabianofranca.flight.business.tools.business
import com.fabianofranca.flight.business.tools.failure
import com.fabianofranca.flight.business.tools.success
import com.fabianofranca.flight.ui.model.Search

class ResultViewModel(private val repository: RoundTripsRepository) : ViewModel() {

    val roundTrips = MutableLiveData<List<RoundTrip>>()
    val airlines = MutableLiveData<List<String>>()
    val handleException = MutableLiveData<Boolean>()

    private var fullRoundTrips: List<RoundTrip>? = null

    init {
        handleException.value = false
    }

    fun init(search: Search) {
        business {
            repository.search(
                search.departure,
                search.destination,
                search.departureDate,
                search.arrivalDate,
                search.adults
            ) success {
                fullRoundTrips = it.sorted()
                roundTrips.value = it.sorted()
                airlines.value = it.airlines()
            } failure {
                handleException.value = true
            }
        }
    }

    fun sortAscending() {
        roundTrips.value = roundTrips.value?.sorted()
    }

    fun sortDescending() {
        roundTrips.value = roundTrips.value?.sortedDescending()
    }

    fun filter(airline: String) {
        roundTrips.value = fullRoundTrips?.filterByAirLine(airline)
    }

    fun clearFilter() {
        roundTrips.value = fullRoundTrips
    }
}
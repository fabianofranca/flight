package com.fabianofranca.flight.ui.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.fabianofranca.flight.business.model.RoundTrip
import com.fabianofranca.flight.business.repository.RoundTripsRepository
import com.fabianofranca.flight.business.repository.airlines
import com.fabianofranca.flight.business.repository.filterByAirLine
import com.fabianofranca.flight.business.tools.business
import com.fabianofranca.flight.business.tools.failure
import com.fabianofranca.flight.business.tools.success
import com.fabianofranca.flight.ui.model.Search

class ResultViewModel(
    private val repository: RoundTripsRepository,
    private val handleException: () -> Unit
) : ViewModel() {

    val roundTrips = MutableLiveData<List<RoundTrip>>()
    val airlines = MutableLiveData<List<String>>()

    private var fullRoundTrips: List<RoundTrip>? = null

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
                handleException()
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

class ResultViewModelFactory(private val handleException: () -> Unit) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass == ResultViewModel::class.java) {
            return ResultViewModel(RoundTripsRepository.Instance, handleException) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
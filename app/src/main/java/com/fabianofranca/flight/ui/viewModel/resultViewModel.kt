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
    private val repository: RoundTripsRepository
) : ViewModel() {

    val roundTrips = MutableLiveData<List<RoundTrip>>()
    val airlines = MutableLiveData<List<String>>()
    var isAscendingOrder: Boolean = true
        private set

    lateinit var handleException: () -> Unit

    private var fullRoundTrips: List<RoundTrip>? = null

    private val filters = mutableSetOf<String>()

    fun init(search: Search, handleException: () -> Unit) {
        this.handleException = handleException

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

                filters.clear()
                filters.addAll(airlines.value!!)
            } failure {
                handleException()
            }
        }
    }

    fun sortAscending() {
        isAscendingOrder = true
        roundTrips.value = roundTrips.value?.sorted()
    }

    fun sortDescending() {
        isAscendingOrder = false
        roundTrips.value = roundTrips.value?.sortedDescending()
    }

    fun filter(airline: String, adding: Boolean = true) {
        if (adding) {
            filters.add(airline)
        } else {
            filters.remove(airline)
        }

        roundTrips.value = fullRoundTrips?.filterByAirLine(filters)

        if (isAscendingOrder) {
            sortAscending()
        } else {
            sortDescending()
        }
    }

    fun clearFilter() {
        roundTrips.value = fullRoundTrips
    }
}

class ResultViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass == ResultViewModel::class.java) {
            return ResultViewModel(RoundTripsRepository.Instance) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
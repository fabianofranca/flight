package com.fabianofranca.flight.ui.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.fabianofranca.flight.business.model.RoundTrip
import com.fabianofranca.flight.business.repository.airlines
import com.fabianofranca.flight.business.repository.filterByAirLine

class ResultViewModel(roundTrips: Array<RoundTrip>) : ViewModel() {

    val roundTrips = MutableLiveData<List<RoundTrip>>()
    val airlines = MutableLiveData<List<String>>()
    var isAscendingOrder: Boolean = true
        private set

    private var fullRoundTrips: List<RoundTrip>? = null

    private val filters = mutableSetOf<String>()

    init {
        fullRoundTrips = roundTrips.sorted()
        this.roundTrips.value = roundTrips.sorted()
        airlines.value = roundTrips.airlines()

        filters.clear()
        filters.addAll(airlines.value!!)
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

class ResultViewModelFactory(private val roundTrips: Array<RoundTrip>) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass == ResultViewModel::class.java) {
            return ResultViewModel(roundTrips) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
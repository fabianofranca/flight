package com.fabianofranca.flight.ui.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.fabianofranca.flight.business.model.RoundTrip
import com.fabianofranca.flight.business.repository.airlines
import com.fabianofranca.flight.business.repository.filterByAirLine

class ResultViewModel : ViewModel() {

    val roundTrips = MutableLiveData<List<RoundTrip>>()
    val airlines = MutableLiveData<List<String>>()
    var isAscendingOrder: Boolean = true
        private set

    private var fullRoundTrips: List<RoundTrip>? = null

    val filters: LiveData<MutableSet<String>> = MutableLiveData()

    init {
    }

    fun init(roundTrips: Array<RoundTrip>) {
        fullRoundTrips = roundTrips.sorted()
        this.roundTrips.value = roundTrips.sorted()
        airlines.value = roundTrips.airlines()

        (filters as MutableLiveData).value = mutableSetOf()

        filters.value?.clear()
        filters.value?.addAll(airlines.value!!)
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
            filters.value?.add(airline)
        } else {
            filters.value?.remove(airline)
        }

        roundTrips.value = fullRoundTrips?.filterByAirLine(filters.value!!)

        if (isAscendingOrder) {
            sortAscending()
        } else {
            sortDescending()
        }
    }
}
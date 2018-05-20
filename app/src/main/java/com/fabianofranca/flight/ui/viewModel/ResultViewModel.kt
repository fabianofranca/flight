package com.fabianofranca.flight.ui.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.fabianofranca.flight.business.model.RoundTrip
import com.fabianofranca.flight.business.repository.RoundTripsRepository
import com.fabianofranca.flight.business.tools.business
import com.fabianofranca.flight.business.tools.failure
import com.fabianofranca.flight.business.tools.success
import com.fabianofranca.flight.ui.model.Search

class ResultViewModel(private val repository: RoundTripsRepository) : ViewModel() {

    val roundTrips = MutableLiveData<Set<RoundTrip>>()
    val handleException = MutableLiveData<Boolean>()

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
                roundTrips.value = it
            } failure {
                handleException.value = true
            }
        }
    }
}
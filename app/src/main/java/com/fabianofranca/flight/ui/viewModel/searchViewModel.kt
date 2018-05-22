package com.fabianofranca.flight.ui.viewModel

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.fabianofranca.flight.business.model.RoundTrip
import com.fabianofranca.flight.business.repository.RoundTripsRepository
import com.fabianofranca.flight.business.tools.business
import com.fabianofranca.flight.business.tools.failure
import com.fabianofranca.flight.business.tools.success
import com.fabianofranca.flight.ui.model.Search
import java.text.SimpleDateFormat

class SearchViewModel(private val repository: RoundTripsRepository) : ViewModel() {
    private lateinit var success: (Set<RoundTrip>) -> Unit
    private lateinit var failure: () -> Unit

    val departure = MutableLiveData<String>()
    val destination = MutableLiveData<String>()
    val departureDate = MutableLiveData<String>()
    val arrivalDate = MutableLiveData<String>()
    val numberOfPassengers = MutableLiveData<Int>()

    val numberOfPassengersRange = (1..9).toList()

    @SuppressLint("SimpleDateFormat")
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")

    fun init(success: (Set<RoundTrip>) -> Unit, failure: () -> Unit) {
        this.success = success
        this.failure = failure
    }

    fun search() {
        val departure = this.departure.value?.toUpperCase()?.trim()
        val destination = this.destination.value?.toUpperCase()?.trim()
        val departureDate = dateFormat.parse(this.departureDate.value)
        val arrivalDate = dateFormat.parse(this.arrivalDate.value)
        val adults = numberOfPassengers.value

        val search = Search(departure!!, destination!!, departureDate, arrivalDate, adults!!)


        business {
            repository.search(
                search.departure,
                search.destination,
                search.departureDate,
                search.arrivalDate,
                search.adults
            ) success {
                success(it)
            } failure {
                failure()
            }
        }
    }
}

class SearchViewModelFactory(private val repository: RoundTripsRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass == SearchViewModel::class.java) {
            return SearchViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
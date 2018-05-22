package com.fabianofranca.flight.ui.viewModel

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.fabianofranca.flight.ui.model.Search
import java.text.SimpleDateFormat

class SearchViewModel : ViewModel() {

    private lateinit var _search: (Search) -> Unit

    val departure = MutableLiveData<String>()
    val destination = MutableLiveData<String>()
    val departureDate = MutableLiveData<String>()
    val arrivalDate = MutableLiveData<String>()
    val numberOfPassengers = MutableLiveData<Int>()

    val numberOfPassengersRange = (1..9).toList()

    @SuppressLint("SimpleDateFormat")
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")

    fun init(search: (Search) -> Unit) {
        _search = search
    }

    fun search() {
        if (::_search.isInitialized) {
            val departure = this.departure.value?.toUpperCase()?.trim()
            val destination = this.destination.value?.toUpperCase()?.trim()
            val departureDate = dateFormat.parse(this.departureDate.value)
            val arrivalDate = dateFormat.parse(this.arrivalDate.value)
            val adults = numberOfPassengers.value

            _search(Search(departure!!, destination!!, departureDate, arrivalDate, adults!!))
        }
    }
}
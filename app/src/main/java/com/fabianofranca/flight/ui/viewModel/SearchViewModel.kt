package com.fabianofranca.flight.ui.viewModel

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.fabianofranca.flight.ui.model.Search
import java.text.SimpleDateFormat
import java.util.*

class SearchViewModel(private val search: (Search) -> Unit) :
    ViewModel() {

    val departure: LiveData<String> = MutableLiveData()
    val destination: LiveData<String> = MutableLiveData()
    val departureDate: LiveData<String> = MutableLiveData()
    val arrivalDate: LiveData<String> = MutableLiveData()
    val numberOfPassengers: LiveData<Int> = MutableLiveData()
    val numberOfPassengersRange: LiveData<List<Int>>

    private val _search = Search("", "", Calendar.getInstance().time)

    @SuppressLint("SimpleDateFormat")
    private val formatter = SimpleDateFormat("dd/MM/yyyy")

    init {
        val range = MutableLiveData<List<Int>>()
        range.value = (1..9).toList()

        numberOfPassengersRange = range
    }

    fun departureDate(year: Int, month: Int, dayOfMonth: Int) {
        _search.departureDate = date(year, month, dayOfMonth, departureDate)
    }

    fun arrivalDate(year: Int, month: Int, dayOfMonth: Int) {
        _search.arrivalDate = date(year, month, dayOfMonth, arrivalDate)
    }

    private fun date(year: Int, month: Int, dayOfMonth: Int, string: LiveData<String>) : Date {
        val date = Calendar.getInstance()
        date.set(year, month - 1, dayOfMonth)

        (string as MutableLiveData<String>).value = formatter.format(date.time)

        return date.time
    }

    fun search(departure: String, destination: String, numberOfPassengers: Int) {
        _search.departure = departure.toUpperCase().trim()
        _search.destination = destination.toUpperCase().trim()

        numberOfPassengersRange.value?.let {
            if (it.contains(numberOfPassengers)) {
                _search.adults = numberOfPassengers
            }
        }

        search(_search)
    }
}

class SearchViewModelFactory(private val search: (Search) -> Unit) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass == SearchViewModel::class.java) {
            return SearchViewModel(search) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
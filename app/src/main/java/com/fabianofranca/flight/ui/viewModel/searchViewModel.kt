package com.fabianofranca.flight.ui.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.*
import com.fabianofranca.flight.R
import com.fabianofranca.flight.business.model.RoundTrip
import com.fabianofranca.flight.business.repository.RoundTripsRepository
import com.fabianofranca.flight.business.tools.business
import com.fabianofranca.flight.business.tools.failure
import com.fabianofranca.flight.business.tools.success
import com.fabianofranca.flight.ui.model.Search
import java.text.SimpleDateFormat
import java.util.*

class SearchViewModel(private val repository: RoundTripsRepository, application: Application) :
    AndroidViewModel(application) {

    private lateinit var success: (Set<RoundTrip>) -> Unit
    private lateinit var failure: () -> Unit

    val departure = MutableLiveData<String>()
    val destination = MutableLiveData<String>()
    val departureDate = MutableLiveData<String>()
    val arrivalDate = MutableLiveData<String>()
    val numberOfPassengers = MutableLiveData<Int>()
    val loading: LiveData<Boolean> = MutableLiveData()
    val isValidSearch: LiveData<Boolean> = MutableLiveData()
    val minDate: LiveData<Calendar> = Transformations.map(departureDate, {
        if (!it.isEmpty()) {
            Calendar.getInstance().apply { time = dateFormat.parse(it) }
        } else {
            Calendar.getInstance()
        }
    })

    val numberOfPassengersRange = (1..9).toList()
    val iata: List<CharSequence>

    @SuppressLint("SimpleDateFormat")
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")

    init {
        (loading as MutableLiveData).value = false
        (isValidSearch as MutableLiveData).value = false
        iata = getApplication<Application>().resources.getTextArray(R.array.iata).asList()
    }

    fun init(success: (Set<RoundTrip>) -> Unit, failure: () -> Unit) {
        this.success = success
        this.failure = failure
    }

    fun search() {
        (loading as MutableLiveData).value = true

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
                loading.value = false
                success(it)
            } failure {
                loading.value = false
                failure()
            }
        }
    }

    fun validate() {

        restrictDate()

        val departure = departure.validate { !it.isNullOrEmpty() && iata.contains(it) }
        val destination = destination.validate { !it.isNullOrEmpty() && iata.contains(it) }
        val departureDate = departureDate.validate { !it.isNullOrEmpty() }
        val arrivalDate = arrivalDate.validate { !it.isNullOrEmpty() }
        val adults = numberOfPassengers.validate { numberOfPassengersRange.contains(it) }

        (isValidSearch as MutableLiveData).value = departure && destination && departureDate &&
                arrivalDate && adults
    }

    private fun restrictDate() {
        departureDate.value?.let { d ->
            arrivalDate.value?.let { a ->
                if (!d.isEmpty()) {
                    if (!a.isEmpty() && dateFormat.parse(d) > dateFormat.parse(a)) {
                        this.arrivalDate.value = this.departureDate.value
                    }
                }
            }
        }
    }

    private fun <T> LiveData<T>.validate(block: (T) -> Boolean): Boolean {
        return this.value != null && block(this.value!!)
    }
}

class SearchViewModelFactory(
    private val repository: RoundTripsRepository,
    private val application: Application
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass == SearchViewModel::class.java) {
            return SearchViewModel(repository, application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
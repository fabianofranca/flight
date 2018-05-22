package com.fabianofranca.flight.ui.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.fabianofranca.flight.R
import com.fabianofranca.flight.infrastructure.binding
import com.fabianofranca.flight.infrastructure.compatActivity
import com.fabianofranca.flight.infrastructure.mainActivity
import com.fabianofranca.flight.ui.custom.DatePickerControl
import com.fabianofranca.flight.ui.viewModel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {
    private lateinit var viewModel: SearchViewModel

    private lateinit var departurePicker: DatePickerControl
    private lateinit var arrivalPicker: DatePickerControl

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(compatActivity!!).get(SearchViewModel::class.java)

        viewModel.init {
            mainActivity?.replace(ResultFragment.newInstance(it), ResultFragment.TAG)
        }

        iata_departure_edit.binding(this, viewModel.departure)
        iata_destination_edit.binding(this, viewModel.destination)

        departurePicker = DatePickerControl(activity!!, departure_date_edit, viewModel.dateFormat)
        arrivalPicker = DatePickerControl(activity!!, arrival_date_edit, viewModel.dateFormat)

        departure_date_edit.binding(this, viewModel.departureDate)
        arrival_date_edit.binding(this, viewModel.arrivalDate)

        setupNumberOfPassengers()

        search_button.setOnClickListener { viewModel.search() }
    }

    private fun setupNumberOfPassengers() {
        val adapter = ArrayAdapter<Int>(context, R.layout.number_of_passengers_item)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        adapter.addAll(listOf(0).union(viewModel.numberOfPassengersRange))
        number_of_passengers_spinner.adapter = adapter
        number_of_passengers_spinner.binding(this, viewModel.numberOfPassengers)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
}
package com.fabianofranca.flight.ui.view

import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import com.fabianofranca.flight.R
import com.fabianofranca.flight.ui.model.Search
import com.fabianofranca.flight.ui.viewModel.SearchViewModel
import com.fabianofranca.flight.ui.viewModel.SearchViewModelFactory
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*

class SearchFragment : Fragment() {
    private val today = Calendar.getInstance()
    private lateinit var viewModel: SearchViewModel

    private lateinit var departurePicker: DatePickerDialog
    private lateinit var arrivalPicker: DatePickerDialog

    private fun search(search: Search) {
        (activity as MainActivity).replace(ResultFragment.newInstance(search), ResultFragment.NAME)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = SearchViewModelFactory(::search)
        viewModel = ViewModelProviders.of(this, factory).get(SearchViewModel::class.java)

        setupIata()
        setupDates()
        setupNumberOfPassengers()
        setupSearch()
    }

    private fun setupIata() {
        viewModel.departure.observe(this, Observer { iata_departure_edit.setText(it) })
        viewModel.destination.observe(this, Observer { iata_destination_edit.setText(it) })
    }

    private fun setupDates() {

        fun createDatePicker() = DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                if (departurePicker.datePicker == view) {
                    viewModel.departureDate(year, month, dayOfMonth)
                } else {
                    viewModel.arrivalDate(year, month, dayOfMonth)
                }
            },
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        )

        departurePicker = createDatePicker()
        arrivalPicker = createDatePicker()

        departure_date_edit.setOnClickListener {
            hideKeyboard()
            departurePicker.show()
        }

        arrival_date_edit.setOnClickListener {
            hideKeyboard()
            arrivalPicker.show()
        }

        viewModel.departureDate.observe(this, Observer { departure_date_edit.setText(it) })
        viewModel.arrivalDate.observe(this, Observer { arrival_date_edit.setText(it) })
    }

    private fun setupNumberOfPassengers() {
        val adapter = ArrayAdapter<Int>(context, R.layout.number_of_passengers_item)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        viewModel.numberOfPassengers.observe(
            this,
            Observer { it?.let { number_of_passengers_spinner.setSelection(it) } })

        viewModel.numberOfPassengersRange.value?.let {
            adapter.addAll(listOf(0).union(it))
        }

        number_of_passengers_spinner.adapter = adapter
    }

    private fun setupSearch() {

        search_button.setOnClickListener {
            viewModel.search(
                iata_departure_edit.text.toString(),
                iata_destination_edit.text.toString(),
                number_of_passengers_spinner.selectedItemPosition
            )
        }
    }

    private fun hideKeyboard() {
        val view = activity?.currentFocus

        if (view != null) {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
}

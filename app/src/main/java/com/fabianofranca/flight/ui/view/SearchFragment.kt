package com.fabianofranca.flight.ui.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.fabianofranca.flight.R
import com.fabianofranca.flight.business.model.RoundTrip
import com.fabianofranca.flight.infrastructure.binding
import com.fabianofranca.flight.infrastructure.compatActivity
import com.fabianofranca.flight.infrastructure.mainActivity
import com.fabianofranca.flight.ui.adapter.SpinnerAdapter
import com.fabianofranca.flight.ui.custom.DatePickerControl
import com.fabianofranca.flight.ui.viewModel.SearchViewModel
import com.fabianofranca.flight.ui.viewModel.SearchViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*
import javax.inject.Inject

class SearchFragment : DaggerFragment() {
    private lateinit var viewModel: SearchViewModel

    @Inject
    lateinit var factory: SearchViewModelFactory

    private lateinit var departurePicker: DatePickerControl
    private lateinit var arrivalPicker: DatePickerControl
    private lateinit var snackbar: Snackbar

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel =
                ViewModelProviders.of(compatActivity!!, factory).get(SearchViewModel::class.java)

        viewModel.init(::success, ::failure)

        setupIata()

        setupDate()

        setupNumberOfPassengers()

        viewModel.loading.observe(this, Observer(::loading))

        search_button.setOnClickListener {
            viewModel.search()
        }

        viewModel.isValidSearch.observe(this, Observer(::validate))

        setupSnackbar()
    }

    private fun setupDate() {
        departurePicker = DatePickerControl(activity!!, departure_date_edit, viewModel.dateFormat)
        departurePicker.setMinDate(Calendar.getInstance())

        arrivalPicker = DatePickerControl(activity!!, arrival_date_edit, viewModel.dateFormat)
        arrivalPicker.setMinDate(Calendar.getInstance())

        viewModel.minDate.observe(this, Observer { it?.let { arrivalPicker.setMinDate(it) } })

        departure_date_edit.binding(this, viewModel.departureDate) { viewModel.validate() }

        arrival_date_edit.binding(this, viewModel.arrivalDate) { viewModel.validate() }
    }

    private fun success(roundTrips: Set<RoundTrip>) {
        mainActivity?.replace(ResultFragment.newInstance(roundTrips), ResultFragment.TAG)
    }

    private fun failure() {
        snackbar.show()
    }

    private fun setupIata() {
        val adapter =
            ArrayAdapter<CharSequence>(context, R.layout.iata_item, viewModel.iata)

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        iata_departure_edit.threshold = 1
        iata_departure_edit.setAdapter(adapter)
        iata_departure_edit.binding(this, viewModel.departure) { viewModel.validate() }

        iata_destination_edit.threshold = 1
        iata_destination_edit.setAdapter(adapter)
        iata_destination_edit.binding(this, viewModel.destination) { viewModel.validate() }
    }

    private fun setupNumberOfPassengers() {

        val list = mutableListOf(0)
        list.addAll(viewModel.numberOfPassengersRange)

        val adapter = SpinnerAdapter(context!!, R.layout.number_of_passengers_item, list)

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)

        number_of_passengers_spinner.adapter = adapter

        number_of_passengers_spinner.binding(this, viewModel.numberOfPassengers) {
            viewModel.validate()
        }
    }

    private fun setupSnackbar() {
        val rootView = activity?.window?.decorView?.findViewById<View>(android.R.id.content)

        snackbar = Snackbar.make(rootView!!, getString(R.string.error), Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.retry)) {
                snackbar.dismiss()
                viewModel.search()
            }
    }

    private fun loading(loading: Boolean?) {
        if (loading != null && loading) {
            progress.visibility = VISIBLE
        } else {
            progress.visibility = GONE
        }
    }

    private fun validate(valid: Boolean?) {
        search_button.isEnabled = valid != null && valid
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onResume() {
        super.onResume()

        activity?.currentFocus?.clearFocus()
    }
}
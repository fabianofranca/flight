package com.fabianofranca.flight.ui.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.fabianofranca.flight.R
import com.fabianofranca.flight.business.model.RoundTrip
import com.fabianofranca.flight.infrastructure.compatActivity
import com.fabianofranca.flight.ui.adapter.RoundTripAdapter
import com.fabianofranca.flight.ui.model.Search
import com.fabianofranca.flight.ui.viewModel.ResultViewModel
import com.fabianofranca.flight.ui.viewModel.ResultViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_result.*
import javax.inject.Inject

private const val ARG_SEARCH = "search"

class ResultFragment : DaggerFragment() {

    private lateinit var search: Search
    private lateinit var viewModel: ResultViewModel
    private val roundTripAdapter = RoundTripAdapter()

    @Inject
    lateinit var factory: ResultViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            search = it.getParcelable(ARG_SEARCH) as Search
        }
        tag
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel =
                ViewModelProviders.of(compatActivity!!, factory).get(ResultViewModel::class.java)

        viewModel.roundTrips.observe(this, Observer(::update))

        viewModel.init(search, ::handleException)

        viewModel.airlines.observe(this, Observer(::filters))

        setupRecycler()

        filter_button.setOnClickListener {
            if (filter_container.visibility == GONE) {
                filter_container.visibility = VISIBLE
            } else {
                filter_container.visibility = GONE
            }
        }

        order_button.setOnClickListener {
            if (viewModel.isAscendingOrder) {
                viewModel.sortDescending()
            } else {
                viewModel.sortAscending()
            }
        }
    }

    private fun setupRecycler() {
        val viewManager = LinearLayoutManager(activity)

        round_trips_recycler.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = roundTripAdapter
        }
    }

    private fun update(roundTrips: List<RoundTrip>?) {

        roundTrips?.let {
            roundTripAdapter.update(it)
        }
    }

    private fun filters(airlines: List<String>?) {

        airlines?.let {

            it.forEach {
                val checkbox = AppCompatCheckBox(context)
                checkbox.isChecked = true
                checkbox.text = it

                checkbox.setOnCheckedChangeListener { _, isChecked ->
                    viewModel.filter(it, isChecked)
                }

                filter_container.addView(checkbox)
            }
        }
    }

    fun handleException() {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    companion object {
        const val TAG = "result"

        @JvmStatic
        fun newInstance(search: Search) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SEARCH, search)
                }
            }
    }
}

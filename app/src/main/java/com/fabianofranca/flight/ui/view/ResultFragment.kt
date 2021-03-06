package com.fabianofranca.flight.ui.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.fabianofranca.flight.R
import com.fabianofranca.flight.business.model.RoundTrip
import com.fabianofranca.flight.infrastructure.compatActivity
import com.fabianofranca.flight.ui.adapter.RoundTripAdapter
import com.fabianofranca.flight.ui.viewModel.ResultViewModel
import kotlinx.android.synthetic.main.fragment_result.*

private const val ARG_ROUND_TRIPS = "roundTrips"
private const val ARG_UPDATE = "update"

class ResultFragment : Fragment() {

    private lateinit var roundTrips: Array<RoundTrip>
    private var update: Boolean = false
    private lateinit var viewModel: ResultViewModel
    private lateinit var roundTripAdapter: RoundTripAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            roundTrips = it.getParcelableArray(ARG_ROUND_TRIPS) as Array<RoundTrip>

            if (it.containsKey(ARG_UPDATE)) {
                update = it.getBoolean(ARG_UPDATE)
                it.remove(ARG_UPDATE)
            }
        }
        tag
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        roundTripAdapter = RoundTripAdapter(context!!)

        compatActivity?.setSupportActionBar(toolbar)
        compatActivity?.supportActionBar?.setDisplayShowTitleEnabled(false)
        compatActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (roundTrips.isEmpty()) {
            full_state.visibility = GONE
            empty_state.visibility = VISIBLE
        } else {
            full_state.visibility = VISIBLE
            empty_state.visibility = GONE
        }

        viewModel = ViewModelProviders.of(compatActivity!!).get(ResultViewModel::class.java)

        viewModel.roundTrips.observe(this, Observer(::update))

        viewModel.airlines.observe(this, Observer(::filters))

        if (update) {
            viewModel.init(roundTrips)
        }

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

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    checkbox.setTextAppearance(context, R.style.TextAppearance)
                } else {
                    checkbox.setTextAppearance(R.style.TextAppearance)
                }

                checkbox.isChecked = viewModel.filters.value?.contains(it)!!
                checkbox.text = it

                checkbox.setOnCheckedChangeListener { _, isChecked ->
                    viewModel.filter(it, isChecked)
                }

                filter_container.addView(checkbox)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item != null && item.itemId == android.R.id.home) {
            compatActivity?.supportFragmentManager?.popBackStack()
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val TAG = "result"

        @JvmStatic
        fun newInstance(roundTrips: Set<RoundTrip>) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArray(ARG_ROUND_TRIPS, roundTrips.toTypedArray())
                    putBoolean(ARG_UPDATE, true)
                }
            }
    }
}

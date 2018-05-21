package com.fabianofranca.flight.ui.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.fabianofranca.flight.R
import com.fabianofranca.flight.business.model.RoundTrip
import com.fabianofranca.flight.ui.model.Search
import com.fabianofranca.flight.ui.viewModel.ResultViewModel
import com.fabianofranca.flight.ui.viewModel.ResultViewModelFactory

private const val ARG_SEARCH = "search"

class ResultFragment : Fragment() {

    private lateinit var search: Search

    private lateinit var viewModel: ResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            search = it.getParcelable(ARG_SEARCH) as Search
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, ResultViewModelFactory(::handleException))
            .get(ResultViewModel::class.java)

        viewModel.roundTrips.observe(this, Observer<List<RoundTrip>>(::update))

        viewModel.init(search)
    }

    fun update(roundTrips: List<RoundTrip>?) {

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
        const val NAME = "result"

        @JvmStatic
        fun newInstance(search: Search) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SEARCH, search)
                }
            }
    }
}

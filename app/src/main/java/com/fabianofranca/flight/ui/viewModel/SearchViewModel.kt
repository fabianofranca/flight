package com.fabianofranca.flight.ui.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.fabianofranca.flight.ui.model.Search

class SearchViewModel : ViewModel() {

    var showSearchResult: ((Search) -> Unit)? = null

    var search = MutableLiveData<Search>()

    fun search() {
        search.value?.let { search ->
            showSearchResult?.let { it(search) }
        }
    }
}
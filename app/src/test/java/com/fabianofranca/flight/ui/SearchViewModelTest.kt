package com.fabianofranca.flight.ui

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.fabianofranca.flight.tools.BaseTest
import com.fabianofranca.flight.ui.model.Search
import com.fabianofranca.flight.ui.viewModel.SearchViewModel
import org.junit.Rule
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class SearchViewModelTest : BaseTest() {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun searchViewModel_shouldShowSearchResult() {

        val viewModel = SearchViewModel()

        val date = Calendar.getInstance().time
        val search = Search("", "", date)

        var searchArg: Search? = null

        viewModel.showSearchResult = { searchArg = it }

        viewModel.search.value = search

        viewModel.search()

        assertEquals(search, searchArg)
    }
}
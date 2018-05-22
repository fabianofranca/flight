package com.fabianofranca.flight.ui

import android.arch.lifecycle.ViewModel
import com.fabianofranca.flight.tools.BaseTest
import com.fabianofranca.flight.ui.viewModel.SearchViewModel
import org.junit.Test
import kotlin.test.assertNotNull

class SearchViewModelFactoryTest : BaseTest() {

    @Test(expected = IllegalArgumentException::class)
    fun searchViewModelFactory_shouldThrowException() {
        val factory = SearchViewModelFactory({})

        factory.create(ViewModel::class.java)
    }

    @Test
    fun searchViewModelFactory_shouldReturnViewModel() {
        val factory = SearchViewModelFactory({})

        val viewModel = factory.create(SearchViewModel::class.java)

        assertNotNull(viewModel)
    }
}
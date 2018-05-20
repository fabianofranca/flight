package com.fabianofranca.flight.ui

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.fabianofranca.flight.tools.BaseTest
import com.fabianofranca.flight.ui.viewModel.SearchViewModel
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

class SearchViewModelTest : BaseTest() {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    //TODO: Revisar testes

    @Test
    fun searchViewModel_shouldSearching() {

        var work = false

        val viewModel = SearchViewModel({ work = true })

        viewModel.search("", "", 0)

        assertTrue(work)
    }
}
package com.fabianofranca.flight.ui

import android.arch.lifecycle.ViewModel
import com.fabianofranca.flight.tools.BaseTest
import com.fabianofranca.flight.ui.viewModel.ResultViewModel
import com.fabianofranca.flight.ui.viewModel.ResultViewModelFactory
import org.junit.Test
import kotlin.test.assertNotNull

class ResultViewModelFactoryTest : BaseTest() {

    @Test(expected = IllegalArgumentException::class)
    fun searchViewModelFactory_shouldThrowException() {
        val factory = ResultViewModelFactory {}

        factory.create(ViewModel::class.java)
    }

    @Test
    fun searchViewModelFactory_shouldReturnViewModel() {
        val factory = ResultViewModelFactory {}

        val viewModel = factory.create(ResultViewModel::class.java)

        assertNotNull(viewModel)
    }
}
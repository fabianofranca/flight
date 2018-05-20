package com.fabianofranca.flight.ui

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.fabianofranca.flight.business.model.RoundTrip
import com.fabianofranca.flight.business.repository.RoundTripsRepository
import com.fabianofranca.flight.infrastructure.Async
import com.fabianofranca.flight.tools.AsyncTestRule
import com.fabianofranca.flight.tools.BaseTest
import com.fabianofranca.flight.ui.model.Search
import com.fabianofranca.flight.ui.viewModel.ResultViewModel
import kotlinx.coroutines.experimental.delay
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import java.util.*
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ResultViewModelTest : BaseTest() {

    @Rule
    @JvmField
    val asyncTestRule = AsyncTestRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: RoundTripsRepository

    private lateinit var viewModel: ResultViewModel

    override fun setup() {
        super.setup()

        viewModel = ResultViewModel(repository)
    }

    @Test
    fun resultViewModel_shouldSearchRoundTrips() {

        val date = Calendar.getInstance().time

        `when`(repository.search("", "", date)).thenReturn(Async.create {
            setOf<RoundTrip>()
        })

        viewModel.init(Search("", "", date))

        assertNotNull(viewModel.roundTrips.value)
    }

    @Test
    fun resultViewModel_shouldHandleException() {

        val date = Calendar.getInstance().time

        `when`(repository.search("", "", date)).thenReturn(Async.create {
            throw Exception()
        })

        viewModel.init(Search("", "", date))

        assertTrue(viewModel.handleException.value!!)
    }
}
package com.fabianofranca.flight.remote

import com.fabianofranca.flight.business.Constants
import com.fabianofranca.flight.remote.ApiConstants.DATE_FORMAT
import com.fabianofranca.flight.tools.AsyncTestRule
import com.fabianofranca.flight.tools.RemoteBaseTest
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import java.text.SimpleDateFormat

class FlightsRemoteTest : RemoteBaseTest() {

    @Rule
    @JvmField
    val coroutineTestRule = AsyncTestRule()

    private lateinit var remote: FlightsRemote

    @Mock
    private lateinit var api: Api

    override fun setup() {
        super.setup()
        remote = FlightsRemoteImpl(api)
    }

    @Test
    fun flightsRemote_shouldSearchFlights() {

        `when`(
            api.search(
                "",
                "",
                "20180101",
                ApiConstants.APP_ID,
                ApiConstants.APP_KEY,
                ApiConstants.FORMAT,
                null,
                Constants.ADULTS,
                100
            )
        ).thenReturn(
            requestFromFile(
                FLIGHTS_JSON
            )
        )

        val date = SimpleDateFormat(DATE_FORMAT).parse("20180101")

        runBlocking {
            remote.search("", "", date).await()
        }

        verify(api).search(
            "",
            "",
            "20180101",
            ApiConstants.APP_ID,
            ApiConstants.APP_KEY,
            ApiConstants.FORMAT,
            null,
            Constants.ADULTS,
            100
        )
    }

    private companion object {
        const val FLIGHTS_JSON = "flights.json"
    }
}

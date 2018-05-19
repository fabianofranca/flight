package com.fabianofranca.flight.remote

import com.fabianofranca.flight.remote.model.SearchData
import com.google.gson.Gson
import kotlinx.coroutines.experimental.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.util.*
import kotlin.test.assertNotNull

class FlightsRemoteTest : ProviderBaseTest<Api>(Api::class) {

    private lateinit var provider: FlightsRemote

    @Rule
    @JvmField
    val expectedException = ExpectedException.none()!!

    @Before
    fun setup() {
        provider = FlightsRemoteImpl(api)
    }

    @Test
    fun flightsRemote_shouldSearchFlights() {

        var searchData: SearchData? = null

        mockJsonResponse(FLIGHTS_JSON)

        runBlocking {
            searchData = provider.search("", "", Calendar.getInstance().time).await()
        }

        assertNotNull(searchData?.data?.onwardFlights)
    }

    @Test(expected = UnexpectedServerException::class)
    fun flightsRemote_shouldSearchAndThrowErrorBecauseNullBody() {
        mockWebServer.enqueue(MockResponse().setBody(Gson().toJson(null)))

        runBlocking {
            provider.search("", "", Calendar.getInstance().time).await()
        }
    }

    @Test(expected = UnexpectedServerException::class)
    fun flightsRemote_shouldSearchAndThrowErrorBecauseUnknownBehavior() {
        mockWebServer.enqueue(MockResponse())

        runBlocking {
            provider.search("", "", Calendar.getInstance().time).await()
        }
    }

    @Test(expected = HttpException::class)
    fun flightsRemote_shouldSearchAndThrowHttpError() {
        mockWebServer.enqueue(MockResponse().setResponseCode(401))

        runBlocking {
            provider.search("", "", Calendar.getInstance().time).await()
        }
    }

    private companion object {
        const val FLIGHTS_JSON = "flights.json"
    }
}

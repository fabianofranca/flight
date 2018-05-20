package com.fabianofranca.flight.remote

import com.fabianofranca.flight.remote.model.SearchData
import com.fabianofranca.flight.remote.tools.HttpException
import com.fabianofranca.flight.remote.tools.UnexpectedServerException
import com.fabianofranca.flight.tools.RetrofitTest
import com.google.gson.Gson
import kotlinx.coroutines.experimental.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ApiTest : RetrofitTest<Api>(Api::class) {

    @Test
    fun api_shouldSearchFlights() {

        var searchData: SearchData? = null

        mockJsonResponse(FLIGHTS_JSON)

        runBlocking {
            searchData = api.search("", "", "").await()
        }

        assertNotNull(searchData)
        assertTrue(searchData!!.data.onwardFlights.isNotEmpty())
    }

    @Test(expected = UnexpectedServerException::class)
    fun flightsRemote_shouldSearchAndThrowErrorBecauseNullBody() {
        mockWebServer.enqueue(MockResponse().setBody(Gson().toJson(null)))

        runBlocking {
            api.search("", "", "").await()
        }
    }

    @Test(expected = UnexpectedServerException::class)
    fun flightsRemote_shouldSearchAndThrowErrorBecauseUnknownBehavior() {
        mockWebServer.enqueue(MockResponse())

        runBlocking {
            api.search("", "", "").await()
        }
    }

    @Test(expected = HttpException::class)
    fun flightsRemote_shouldSearchAndThrowHttpError() {
        mockWebServer.enqueue(MockResponse().setResponseCode(401))

        runBlocking {
            api.search("", "", "").await()
        }
    }

    private companion object {
        const val FLIGHTS_JSON = "flights.json"
    }

}
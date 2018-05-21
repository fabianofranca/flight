package com.fabianofranca.flight.remote

import com.fabianofranca.flight.remote.model.SearchData
import com.fabianofranca.flight.remote.tools.HttpException
import com.fabianofranca.flight.remote.tools.UnexpectedServerException
import com.fabianofranca.flight.tools.RetrofitTest
import com.google.gson.Gson
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ApiTest : RetrofitTest<Api>(Api::class) {

    @Test
    fun api_shouldSearchFlights() {

        var searchData: SearchData? = null

        mockJsonResponse(FLIGHTS_JSON)

        async.run {
            searchData = api.search("", "", "").await()
        }

        assertNotNull(searchData)
        assertTrue(searchData!!.data.onwardFlights.isNotEmpty())
    }

    @Test(expected = UnexpectedServerException::class)
    fun api_shouldSearchAndThrowErrorBecauseNullBody() {
        mockWebServer.enqueue(MockResponse().setBody(Gson().toJson(null)))

        async.run {
            api.search("", "", "").await()
        }
    }

    @Test(expected = UnexpectedServerException::class)
    fun api_shouldSearchAndThrowErrorBecauseUnknownBehavior() {
        mockWebServer.enqueue(MockResponse())

        async.run {
            api.search("", "", "").await()
        }
    }

    @Test(expected = HttpException::class)
    fun api_shouldSearchAndThrowHttpError() {
        mockWebServer.enqueue(MockResponse().setResponseCode(401))

        async.run {
            api.search("", "", "").await()
        }
    }

    private companion object {
        const val FLIGHTS_JSON = "flights.json"
    }

}
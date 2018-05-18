package com.fabianofranca.flight.providers

import com.fabianofranca.flight.providers.api.Api
import com.fabianofranca.flight.providers.api.HttpException
import com.fabianofranca.flight.providers.api.UnexpectedServerException
import com.google.gson.Gson
import kotlinx.coroutines.experimental.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.util.*

class FlightsProviderTest : ProviderBaseTest<Api>(Api::class) {

    private lateinit var provider: FlightsProvider

    @Rule
    @JvmField
    val expectedException = ExpectedException.none()!!

    @Before
    fun setup() {
        provider = FlightsProviderImpl(api)
    }

    @Test
    fun flightsProvider_shouldSearchFlights() {

        var searchData: SearchData? = null

        mockWebServer.enqueue(MockResponse().setBody(Gson().toJson(SearchData(Data(emptySet())))))

        runBlocking {
            searchData = provider.search("", "", Calendar.getInstance().time).await()
        }

        assertNotNull(searchData?.data?.onwardFlights)
    }

    @Test(expected = UnexpectedServerException::class)
    fun flightsProvider_shouldSearchAndThrowErrorBecauseNullBody() {
        mockWebServer.enqueue(MockResponse().setBody(Gson().toJson(null)))

        runBlocking {
            provider.search("", "", Calendar.getInstance().time).await()
        }
    }

    @Test(expected = UnexpectedServerException::class)
    fun flightsProvider_shouldSearchAndThrowErrorBecauseUnknownBehavior() {
        mockWebServer.enqueue(MockResponse())

        runBlocking {
            provider.search("", "", Calendar.getInstance().time).await()
        }
    }

    @Test(expected = HttpException::class)
    fun flightsProvider_shouldSearchAndThrowHttpError() {
        mockWebServer.enqueue(MockResponse().setResponseCode(401))

        runBlocking {
            provider.search("", "", Calendar.getInstance().time).await()
        }
    }
}

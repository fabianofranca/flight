package com.fabianofranca.flight.providers

import com.fabianofranca.flight.providers.api.Api
import com.google.gson.Gson
import kotlinx.coroutines.experimental.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.util.*

class FlightsProviderTest : ProviderBaseTest<Api>(Api::class) {

    @Test
    fun api_shouldSearchFlights() {

        val provider = FlightsProviderImpl(api)

        var searchData: SearchData? = null

        mockWebServer.enqueue(MockResponse().setBody(Gson().toJson(SearchData(Data(emptySet())))))

        runBlocking {
            searchData = provider.search("", "", Calendar.getInstance().time).await()
        }

        assertNotNull(searchData?.data?.onwardFlights)
    }
}

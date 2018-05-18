package com.fabianofranca.flight.providers

import com.fabianofranca.flight.providers.api.Api
import com.fabianofranca.flight.providers.api.ApiConstants
import com.fabianofranca.flight.providers.api.RequestAdapterFactory
import com.google.gson.Gson
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class FlightsProviderTest {

    private lateinit var api: Api

    @Before
    fun setup() {
        api = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addCallAdapterFactory(RequestAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(Api::class.java)
    }

    @Test
    fun api_shouldSearchFlights() {

        val provider = FlightsProviderImpl(api)
        var searchData: SearchData? = null

        runBlocking {
            val cal = Calendar.getInstance()
            cal.set(Calendar.YEAR, 2018)
            cal.set(Calendar.MONTH, Calendar.MAY)
            cal.set(Calendar.DAY_OF_MONTH, 18)

            searchData = provider.search("CNF", "VIX", cal.time).await()
        }

        assertNotNull(searchData?.data?.onwardFlights)
    }
}

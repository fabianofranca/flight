package com.fabianofranca.flight

import com.fabianofranca.flight.providers.api.Api
import com.fabianofranca.flight.providers.api.ApiValues
import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiTest {

    private lateinit var api: Api

    @Before
    fun setup() {
        api = Retrofit.Builder()
            .baseUrl(ApiValues.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(Api::class.java)
    }

    @Test
    fun api_shouldSearchFlights() {

        val call = api.search("CNF", "VIX", "20180518")

        val result = call.execute()

        assertTrue(result.isSuccessful)
    }
}

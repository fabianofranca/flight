package com.fabianofranca.flight.providers.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("search/")
    fun search(
        @Query("source") source: String,
        @Query("destination") destination: String,
        @Query("dateofdeparture") dateOfDeparture: String,
        @Query("app_id") appId: String = ApiValues.APP_ID,
        @Query("app_key") appKey: String = ApiValues.APP_KEY,
        @Query("format") format: String = ApiValues.FORMAT,
        @Query("dateofarrival") dateOfArrival: String? = null,
        @Query("adults") adults: Int = 1,
        @Query("counter") counter: Int = 100): Call<Any>
}

object ApiValues {
    const val BASE_URL = "http://developer.goibibo.com/api/"
    const val APP_ID = "8dca5fff"
    const val APP_KEY = "d2eac8304684f03af5f7bdab5fa54b92"
    const val FORMAT = "json"
}
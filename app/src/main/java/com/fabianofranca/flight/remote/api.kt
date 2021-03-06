package com.fabianofranca.flight.remote

import com.fabianofranca.flight.business.Constants
import com.fabianofranca.flight.remote.model.SearchData
import com.fabianofranca.flight.remote.tools.Request
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("search/")
    fun search(
        @Query("source") source: String,
        @Query("destination") destination: String,
        @Query("dateofdeparture") departureDate: String,
        @Query("app_id") appId: String = ApiConstants.APP_ID,
        @Query("app_key") appKey: String = ApiConstants.APP_KEY,
        @Query("format") format: String = ApiConstants.FORMAT,
        @Query("dateofarrival") arrivalDate: String? = null,
        @Query("adults") adults: Int = Constants.ADULTS,
        @Query("counter") counter: Int = 100
    ): Request<SearchData>
}

object ApiConstants {
    const val BASE_URL = "http://developer.goibibo.com/api/"
    const val APP_ID = "8dca5fff"
    const val APP_KEY = "d2eac8304684f03af5f7bdab5fa54b92"
    const val FORMAT = "json"
    const val DATE_FORMAT = "yyyyMMdd"
}
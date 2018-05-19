package com.fabianofranca.flight.remote

import com.fabianofranca.flight.BaseTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import kotlin.reflect.KClass

abstract class ProviderBaseTest<out T : Any>(kclass: KClass<T>) : BaseTest() {

    protected val mockWebServer: MockWebServer = MockWebServer()
    protected val api: T

    init {
        mockWebServer.start()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .addCallAdapterFactory(RequestAdapterFactory())
            .addConverterFactory(buildGsonConverter())
            .build()
            .create(kclass.java)
    }

    fun mockJsonResponse(file: String) {
        mockWebServer.enqueue(MockResponse().setBody(getStringFileContent(file)))
    }
}
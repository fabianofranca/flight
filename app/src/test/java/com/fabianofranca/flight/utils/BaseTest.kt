package com.fabianofranca.flight.utils

import com.fabianofranca.flight.infrastructure.Async
import com.google.gson.Gson
import org.junit.Before
import org.mockito.MockitoAnnotations

abstract class BaseTest {

    @Before
    open fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    fun stringFileContent(file: String): String {
        return javaClass.classLoader.getResourceAsStream(file).bufferedReader()
            .use { it.readText() }
    }

    inline fun <reified T> serialize(file: String): T {
        return Gson().fromJson(stringFileContent(file), T::class.java)
    }

    fun <T> async(data: T): Async<T> = Async.create { data }
}
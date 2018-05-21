package com.fabianofranca.flight.tools

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.fabianofranca.flight.infrastructure.Async
import com.google.gson.Gson
import org.junit.Before
import org.junit.Rule
import org.mockito.MockitoAnnotations

abstract class BaseTest {

    @Rule
    @JvmField
    val async = AsyncTestRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

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
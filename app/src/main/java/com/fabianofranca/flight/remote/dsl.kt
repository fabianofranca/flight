package com.fabianofranca.flight.remote

import com.fabianofranca.flight.infrastructure.Async
import com.fabianofranca.flight.infrastructure.AsyncContext
import kotlinx.coroutines.experimental.async

fun <T> remote(
    block: suspend () -> T
) : Async<T> {

    val deferred = async(AsyncContext.current.commonPool) {
        block()
    }

    return Async(deferred)
}
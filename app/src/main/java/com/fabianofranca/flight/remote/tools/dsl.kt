package com.fabianofranca.flight.remote.tools

import com.fabianofranca.flight.infrastructure.Async
import com.fabianofranca.flight.infrastructure.AsyncContext
import kotlinx.coroutines.experimental.async

fun <T> remote(
    block: suspend () -> T
) : Async<T> {

    return Async.create { block() }
}
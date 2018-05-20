package com.fabianofranca.flight.business.tools

import com.fabianofranca.flight.infrastructure.Async
import com.fabianofranca.flight.infrastructure.AsyncContext

fun business(block: suspend () -> Unit) {
    Async.create(AsyncContext.current.ui) { block() }
}

suspend infix fun <R> Async<R>.success(block: suspend (R) -> Unit): Exception? {

    return try {
        AsyncContext.current.executor.execute {
            block(this.await())
        }
        null
    } catch (e: Exception) {
        e
    }
}

infix fun Exception?.failure(block: (Exception) -> Unit) {

    this?.let { block(this) }
}
package com.fabianofranca.flight.infrastructure

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlin.coroutines.experimental.CoroutineContext

class Async<T>(deferred: Deferred<T>): Deferred<T> by deferred {
    companion object {
        fun <T> create(block: () -> T): Async<T> {
            val deferred = async { block() }

            return Async(deferred)
        }
    }
}

class AsyncContext {

    private var _ui: CoroutineContext? = null
    private var _commonPool: CoroutineContext? = null

    val ui: CoroutineContext
        get() {
            return _ui?.let { it } ?: UI
        }

    val commonPool: CoroutineContext
        get() {
            return _commonPool?.let { it } ?: CommonPool
        }

    fun setContexts(ui: CoroutineContext, commonPool: CoroutineContext) {
        _ui = ui
        _commonPool = commonPool
    }

    fun setDefaultContexts() {
        _ui = null
        _commonPool = null
    }

    companion object {
        val current = AsyncContext()
    }
}
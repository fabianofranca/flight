package com.fabianofranca.flight.infrastructure

import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

class Async<T>(deferred: Deferred<T>) : Deferred<T> by deferred {
    companion object {
        fun <T> create(
            context: CoroutineContext = AsyncContext.current.commonPool,
            block: suspend () -> T
        ): Async<T> {
            val deferred = async(context) { block() }

            return Async(deferred)
        }
    }
}

class AsyncContext {

    private var _ui: CoroutineContext? = null
    private var _commonPool: CoroutineContext? = null
    private var _executor: AsyncExecutor? = null

    val ui: CoroutineContext
        get() {
            return _ui?.let { it } ?: UI
        }

    val commonPool: CoroutineContext
        get() {
            return _commonPool?.let { it } ?: CommonPool
        }

    val executor: AsyncExecutor
    get() {
        return _executor?.let { it } ?: LaunchExecutor()
    }

    fun setContexts(ui: CoroutineContext, commonPool: CoroutineContext) {
        _ui = ui
        _commonPool = commonPool
    }

    fun setExecutor(executor: AsyncExecutor) {
        _executor = executor
    }

    fun setDefaultContexts() {
        _ui = null
        _commonPool = null
    }

    fun setDefaultExecutor() {
        _executor = null
    }

    companion object {
        val current = AsyncContext()
    }
}

sealed class AsyncExecutor {
    abstract fun execute(block: suspend () -> Unit)
}

class LaunchExecutor : AsyncExecutor() {
    override fun execute(block: suspend () -> Unit) {
        launch { block() }
    }
}

class RunBlockingExecutor : AsyncExecutor() {
    override fun execute(block: suspend () -> Unit) {
        runBlocking { block() }
    }
}
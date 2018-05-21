package com.fabianofranca.flight.tools

import com.fabianofranca.flight.infrastructure.AsyncContext
import com.fabianofranca.flight.infrastructure.BlockingExecutor
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.runBlocking
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class AsyncTestRule : TestWatcher() {

    override fun starting(description: Description?) {
        AsyncContext.Instance.setContexts(Unconfined, Unconfined)
        AsyncContext.Instance.setExecutor(BlockingExecutor())
        super.starting(description)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        AsyncContext.Instance.setDefaultContexts()
        AsyncContext.Instance.setDefaultExecutor()
    }

    fun run(block: suspend () -> Unit) {
        runBlocking { AsyncContext.Instance.execute(block) }
    }
}
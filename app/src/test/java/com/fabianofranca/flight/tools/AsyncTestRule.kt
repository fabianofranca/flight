package com.fabianofranca.flight.tools

import com.fabianofranca.flight.infrastructure.AsyncContext
import kotlinx.coroutines.experimental.Unconfined
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class AsyncTestRule : TestWatcher() {

    override fun starting(description: Description?) {
        AsyncContext.current.setContexts(Unconfined, Unconfined)
        super.starting(description)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        AsyncContext.current.setDefaultContexts()
    }
}
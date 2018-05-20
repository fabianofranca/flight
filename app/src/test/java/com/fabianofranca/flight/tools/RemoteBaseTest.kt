package com.fabianofranca.flight.tools

import com.fabianofranca.flight.remote.tools.Request

abstract class RemoteBaseTest : BaseTest() {

    inline fun <reified T> requestFromFile(file: String) = Request.create { serialize<T>(file) }
}
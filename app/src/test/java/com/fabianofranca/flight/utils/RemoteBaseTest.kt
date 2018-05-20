package com.fabianofranca.flight.utils

import com.fabianofranca.flight.remote.Request

abstract class RemoteBaseTest : BaseTest() {

    inline fun <reified T> requestFromFile(file: String) = Request.create { serialize<T>(file) }
}
package com.fabianofranca.flight.remote

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class Request<out R>(deferred: Deferred<R>) : Deferred<R> by deferred

class RequestAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>?,
        retrofit: Retrofit?
    ): CallAdapter<*, *>? {

        if (getRawType(returnType) != Request::class.java) {
            return null
        }

        val innerType = getParameterUpperBound(0, returnType as ParameterizedType)

        if (getRawType(innerType) != Response::class.java) {
            return CustomCallAdapter<Any>(innerType)
        }

        return null
    }

    private inner class CustomCallAdapter<R>(private val responseType: Type) :
        CallAdapter<R, Request<R?>> {

        override fun adapt(call: Call<R>?): Request<R?> {
            return Request(deferred(call!!))
        }

        override fun responseType(): Type {
            return responseType
        }
    }

    private fun <R> deferred(call: Call<R>) = async {
        try {
            val response = call.execute()

            if (!response.isSuccessful) {
                throw HttpException(
                    response.code(),
                    response.message()
                )
            }

            return@async response.body() ?: throw UnexpectedServerException()
        } catch (e: Exception) {
            throw when (e) {
                is HttpException -> e
                else -> UnexpectedServerException(e)
            }
        }
    }
}

class UnexpectedServerException() : RuntimeException() {
    constructor(cause: Throwable) : this() {
        initCause(cause)
    }
}

class HttpException(val code: Int, message: String) : RuntimeException(message)
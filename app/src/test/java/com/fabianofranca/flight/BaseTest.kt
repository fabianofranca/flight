package com.fabianofranca.flight

abstract class BaseTest {

    fun getStringFileContent(file: String): String {
        return javaClass.classLoader.getResourceAsStream(file).bufferedReader()
            .use { it.readText() }
    }
}
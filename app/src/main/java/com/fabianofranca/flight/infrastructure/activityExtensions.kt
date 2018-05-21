package com.fabianofranca.flight.infrastructure

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.replaceFragment(
    @IdRes container: Int, fragment: Fragment,
    name: String? = null
) {
    val transaction = this.supportFragmentManager.beginTransaction()
    transaction.replace(container, fragment)

    name?.let { transaction.addToBackStack(it) }

    transaction.commit()
}
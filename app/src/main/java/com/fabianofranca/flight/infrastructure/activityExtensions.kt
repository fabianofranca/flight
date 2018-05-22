package com.fabianofranca.flight.infrastructure

import android.app.Activity
import android.content.Context
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager

fun AppCompatActivity.replaceFragment(
    @IdRes container: Int, fragment: Fragment,
    tag: String? = null
) {
    val transaction = this.supportFragmentManager.beginTransaction()
    transaction.replace(container, fragment, tag)

    tag?.let { transaction.addToBackStack(it) }

    transaction.commit()
}

fun Activity.hideKeyboard() {
    val view = currentFocus

    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
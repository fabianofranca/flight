package com.fabianofranca.flight.infrastructure

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.view.View

inline fun <reified T> View.binding(
    owner: LifecycleOwner,
    liveData: LiveData<T>,
    crossinline block: (T?) -> Unit
) {
    liveData.observe(owner, Observer { block(it) })
}

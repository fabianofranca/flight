package com.fabianofranca.flight.infrastructure

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText

inline fun <reified T> EditText.binding(
    owner: LifecycleOwner,
    liveData: LiveData<T>
) {
    (this as View).binding(owner, liveData) {
        if (it != this.text.toString()) {
            this.setText(it.toString())
        }
    }
}

inline fun <reified T> EditText.binding(
    owner: LifecycleOwner,
    liveData: MutableLiveData<T>,
    oneWay: Boolean = false,
    crossinline change: (T) -> Unit = {}
) {
    this.binding(owner, liveData as LiveData<T>)

    if (!oneWay) {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                liveData.value = s.toString() as T
                change(s.toString() as T)
            }
        })
    }
}

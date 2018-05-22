package com.fabianofranca.flight.infrastructure

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

fun Spinner.binding(
    owner: LifecycleOwner,
    liveData: LiveData<Int>
) {
    (this as View).binding(owner, liveData) {
        if (it != this.selectedItemPosition) {
            it?.let { this.setSelection(it) }
        }
    }
}

fun Spinner.binding(
    owner: LifecycleOwner,
    liveData: MutableLiveData<Int>,
    oneWay: Boolean = false
) {
    this.binding(owner, liveData as LiveData<Int>)

    if (!oneWay) {

        this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                liveData.value = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }
}
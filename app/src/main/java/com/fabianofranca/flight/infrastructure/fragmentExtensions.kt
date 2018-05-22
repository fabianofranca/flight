package com.fabianofranca.flight.infrastructure

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.fabianofranca.flight.ui.view.MainActivity

val Fragment.compatActivity: AppCompatActivity?
    get() = activity as AppCompatActivity?

val Fragment.mainActivity: MainActivity?
    get() = activity as MainActivity?
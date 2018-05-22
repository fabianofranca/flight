package com.fabianofranca.flight.ui.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.fabianofranca.flight.R
import com.fabianofranca.flight.infrastructure.replaceFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.findFragmentByTag(ResultFragment.TAG) == null) {
            replace(SearchFragment())
        }
    }

    fun replace(fragment: Fragment, tag: String? = null) {
        replaceFragment(R.id.main_container, fragment, tag)
    }
}

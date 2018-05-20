package com.fabianofranca.flight.ui.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fabianofranca.flight.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.main_container, SearchFragment())

        transaction.commit()
    }
}

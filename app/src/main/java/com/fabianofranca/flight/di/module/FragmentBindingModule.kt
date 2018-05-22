package com.fabianofranca.flight.di.module

import com.fabianofranca.flight.ui.view.ResultFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @ContributesAndroidInjector
    abstract fun resultFragment() : ResultFragment
}
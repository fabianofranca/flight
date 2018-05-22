package com.fabianofranca.flight.di.module

import com.fabianofranca.flight.business.repository.RoundTripsRepository
import com.fabianofranca.flight.ui.viewModel.ResultViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelFactoryModule {

    @Provides
    fun provideResultViewModelFactory(repository: RoundTripsRepository): ResultViewModelFactory {
        return ResultViewModelFactory(repository)
    }
}
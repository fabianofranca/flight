package com.fabianofranca.flight.di.module

import com.fabianofranca.flight.business.repository.RoundTripsRepository
import com.fabianofranca.flight.ui.viewModel.SearchViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelFactoryModule {

    @Provides
    fun provideSearchViewModelFactory(repository: RoundTripsRepository): SearchViewModelFactory {
        return SearchViewModelFactory(repository)
    }
}
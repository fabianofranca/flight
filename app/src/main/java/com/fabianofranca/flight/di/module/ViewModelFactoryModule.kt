package com.fabianofranca.flight.di.module

import android.app.Application
import com.fabianofranca.flight.business.repository.RoundTripsRepository
import com.fabianofranca.flight.ui.viewModel.SearchViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelFactoryModule {

    @Provides
    fun provideSearchViewModelFactory(repository: RoundTripsRepository, application: Application): SearchViewModelFactory {
        return SearchViewModelFactory(repository, application)
    }
}
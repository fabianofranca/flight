package com.fabianofranca.flight.di.module

import com.fabianofranca.flight.business.repository.RoundTripsRepository
import com.fabianofranca.flight.business.repository.RoundTripsRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BusinessModule {
    @Provides
    @Singleton
    fun provideRoundTripsRepository(repository: RoundTripsRepositoryImpl): RoundTripsRepository {
        return repository
    }

}
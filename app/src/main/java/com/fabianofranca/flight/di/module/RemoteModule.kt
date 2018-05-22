package com.fabianofranca.flight.di.module

import com.fabianofranca.flight.business.model.RoundTrip
import com.fabianofranca.flight.remote.*
import com.fabianofranca.flight.remote.tools.RequestAdapterFactory
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RemoteModule {

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val builder = GsonBuilder()

        builder.registerTypeAdapter(
            RoundTrip::class.java,
            RoundTripDeserializer()
        )

        return GsonConverterFactory.create(builder.create())
    }

    @Provides
    @Singleton
    fun provideRequestAdapterFactory(): RequestAdapterFactory {
        return RequestAdapterFactory()
    }

    @Provides
    @Singleton
    fun provideApi(
        gsonConverterFactory: GsonConverterFactory,
        requestAdapterFactory: RequestAdapterFactory
    ): Api {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addCallAdapterFactory(requestAdapterFactory)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideFlightsRemote(remote: FlightsRemoteImpl): FlightsRemote = remote
}
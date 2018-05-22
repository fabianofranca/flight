package com.fabianofranca.flight.di

import android.app.Application
import com.fabianofranca.flight.App
import com.fabianofranca.flight.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ViewModelFactoryModule::class,
    FragmentBindingModule::class,
    RemoteModule::class,
    BusinessModule::class])
interface AppComponent : AndroidInjector<App> {

    override fun inject(instance: App?)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}
package com.jmcarcedo.internationbusinessmen.core.di

import com.jmcarcedo.internationbusinessmen.InternationalBusinessMen
import com.jmcarcedo.internationbusinessmen.transactions.di.TransactionsBuilderModule
import com.jmcarcedo.internationbusinessmen.transactions.di.TransactionsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        RealmModule::class,
        ViewModelModule::class,
        TransactionsModule::class,
        TransactionsBuilderModule::class
    ]
)
interface AppComponent {

    fun inject(application: InternationalBusinessMen)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: InternationalBusinessMen,
            realmModule: RealmModule
        ): AppComponent
    }
}
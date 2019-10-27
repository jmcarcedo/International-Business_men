package com.jmcarcedo.internationbusinessmen

import android.app.Application
import com.jmcarcedo.internationbusinessmen.core.di.DaggerAppComponent
import com.jmcarcedo.internationbusinessmen.core.di.RealmModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class InternationalBusinessMen: Application(), HasAndroidInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        setUpDagger()
    }

    private fun setUpDagger() {
        DaggerAppComponent.factory()
            .create(this, RealmModule(this))
            .also {
                it.inject(this)
            }
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return activityDispatchingAndroidInjector
    }
}
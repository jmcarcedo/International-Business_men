package com.jmcarcedo.internationbusinessmen.core.di

import android.content.Context
import com.jmcarcedo.internationbusinessmen.core.data.datasource.RealmInstanceHelper
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

@Module
class RealmModule(context: Context) {

    init {
        Realm.init(context)
    }

    @Provides
    @Singleton
    internal fun providesRealmIntance(): RealmInstanceHelper {
        return RealmInstanceHelper()
    }
}
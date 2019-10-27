package com.jmcarcedo.internationbusinessmen.transactions.di

import com.jmcarcedo.internationbusinessmen.transactions.data.datasource.TransactionsRealmDataSource
import com.jmcarcedo.internationbusinessmen.transactions.data.datasource.TransactionsRetrofitDataSource
import com.jmcarcedo.internationbusinessmen.transactions.data.repository.TransactionsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TransactionsRepositoryModule {

    @JvmStatic
    @Provides
    @Singleton
    fun providesTransactionsRepository(
        transactionsRetrofitDS: TransactionsRetrofitDataSource,
        realmDataSource: TransactionsRealmDataSource):
        TransactionsRepository {
        return TransactionsRepository(transactionsRetrofitDS, realmDataSource)
    }
}
package com.jmcarcedo.internationbusinessmen.transactions.di

import com.jmcarcedo.internationbusinessmen.transactions.data.datasource.TransactionsRetrofitApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object TransactionsRetrofitModule {

    @JvmStatic
    @Provides
    fun providesTransactionsService(retrofit: Retrofit): TransactionsRetrofitApi {
        return retrofit.create(TransactionsRetrofitApi::class.java)
    }
}
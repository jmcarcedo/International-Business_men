package com.jmcarcedo.internationbusinessmen.transactions.di

import dagger.Module

@Module(
    includes = [
        TransactionsBuilderModule::class,
        TransactionsRepositoryModule::class,
        TransactionsRetrofitModule::class
    ]
)
object TransactionsModule
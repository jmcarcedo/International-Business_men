package com.jmcarcedo.internationbusinessmen.transactions.data.datasource

import com.jmcarcedo.internationbusinessmen.transactions.data.datasource.model.RateResponse
import com.jmcarcedo.internationbusinessmen.transactions.data.datasource.model.TransactionResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TransactionsRetrofitDataSource @Inject constructor(
    private val transactionsRetrofitApi: TransactionsRetrofitApi
) {

    fun getRates(): Single<List<RateResponse>> {
        return transactionsRetrofitApi.getRates().subscribeOn(Schedulers.computation())
    }

    fun getTransactions(): Single<List<TransactionResponse>> {
        return transactionsRetrofitApi.getTransactions().subscribeOn(Schedulers.computation())
    }
}
package com.jmcarcedo.internationbusinessmen.transactions.data.datasource

import com.jmcarcedo.internationbusinessmen.transactions.data.datasource.model.RateResponse
import com.jmcarcedo.internationbusinessmen.transactions.data.datasource.model.TransactionResponse
import io.reactivex.Single
import retrofit2.http.GET

interface TransactionsRetrofitApi {

    @GET("rates")
    fun getRates(): Single<List<RateResponse>>

    @GET("transactions")
    fun getTransactions(): Single<List<TransactionResponse>>
}
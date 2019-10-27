package com.jmcarcedo.internationbusinessmen.transactions.data.repository

import com.jmcarcedo.internationbusinessmen.transactions.data.datasource.TransactionsRealmDataSource
import com.jmcarcedo.internationbusinessmen.transactions.data.datasource.TransactionsRetrofitDataSource
import com.jmcarcedo.internationbusinessmen.transactions.data.mapper.TransactionsMapper
import com.jmcarcedo.internationbusinessmen.transactions.domain.model.Product
import com.jmcarcedo.internationbusinessmen.transactions.domain.model.Rate
import io.reactivex.Completable
import io.reactivex.Single

class TransactionsRepository(
    private val transactionsRetrofitDS: TransactionsRetrofitDataSource,
    private val realmDS: TransactionsRealmDataSource
) {

    fun getTransactions(): Single<List<String>> {
        return transactionsRetrofitDS.getTransactions()
            .map(TransactionsMapper::fromTransactionsResponseToProductsEntity)
            .flatMapCompletable{ products ->
                realmDS.deleteDB()
                    .andThen(realmDS.insertProducts(products))
            }
            .onErrorResumeNext { Completable.complete() } //This is wrong in many levels but I haven't got enough time to implement the network error handler
            .andThen(realmDS.getProducts())
            .toSingle() //Again this should be handle warning the user or at least retrying the calling to populate the DB
            .map(TransactionsMapper::fromProductsEntityToModel)
            .map { products -> products.map { it.product } }
    }

    fun getRates(): Single<List<Rate>> {
        return transactionsRetrofitDS.getRates()
            .map(TransactionsMapper::fromRatesResponseToEntity)
            .flatMapCompletable(realmDS::insertRates)
            .onErrorResumeNext { Completable.complete() } //This is wrong in many levels but I haven't got enough time to implement the network error handler
            .andThen(realmDS.getRates())
            .toSingle()
            .map(TransactionsMapper::fromRatesEntityToModel)
    }

    fun getProduct(selectedProduct: String): Single<Product> {
        return realmDS.getProductDetail(selectedProduct)
            .toSingle()
            .map(TransactionsMapper::fromProductEntityToModel)
    }
}
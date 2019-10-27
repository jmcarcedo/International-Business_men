package com.jmcarcedo.internationbusinessmen.transactions.domain

import com.jmcarcedo.internationbusinessmen.transactions.data.repository.TransactionsRepository
import com.jmcarcedo.internationbusinessmen.transactions.domain.helper.ConversionHelper
import com.jmcarcedo.internationbusinessmen.transactions.domain.model.Transaction
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetProductDetail @Inject constructor(private val transactionsRepo: TransactionsRepository) {

    fun execute(selectedProduct: String): Single<Transaction> {
        return Single.zip(
            transactionsRepo.getRates(),
            transactionsRepo.getProduct(selectedProduct),
            BiFunction { rates, product ->
                ConversionHelper.convertTransactions(product.transactions, "EUR", rates)
            }
        )
    }
}
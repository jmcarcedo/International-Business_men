package com.jmcarcedo.internationbusinessmen.transactions.domain

import com.jmcarcedo.internationbusinessmen.transactions.data.repository.TransactionsRepository
import io.reactivex.Single
import javax.inject.Inject

class GetProducts @Inject constructor(private val transactionsRepo: TransactionsRepository) {

    fun execute(): Single<List<String>> {
        return transactionsRepo.getTransactions()
    }
}
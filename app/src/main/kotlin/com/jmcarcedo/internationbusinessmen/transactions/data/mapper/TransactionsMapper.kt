package com.jmcarcedo.internationbusinessmen.transactions.data.mapper

import com.jmcarcedo.internationbusinessmen.core.data.datasource.toRealmList
import com.jmcarcedo.internationbusinessmen.transactions.data.datasource.model.ProductRealm
import com.jmcarcedo.internationbusinessmen.transactions.data.datasource.model.RateRealm
import com.jmcarcedo.internationbusinessmen.transactions.data.datasource.model.RateResponse
import com.jmcarcedo.internationbusinessmen.transactions.data.datasource.model.TransactionRealm
import com.jmcarcedo.internationbusinessmen.transactions.data.datasource.model.TransactionResponse
import com.jmcarcedo.internationbusinessmen.transactions.domain.model.Product
import com.jmcarcedo.internationbusinessmen.transactions.domain.model.Rate
import com.jmcarcedo.internationbusinessmen.transactions.domain.model.Transaction
import io.realm.RealmList

object TransactionsMapper {

    private const val DEFAULT_AMOUNT = "0"
    private const val DEFAULT_CURRENCY = "EUR"

    fun fromTransactionsResponseToProductsEntity(transactionResponse: List<TransactionResponse>):
        List<ProductRealm> {
        val groupTransactions = transactionResponse.groupBy(TransactionResponse::sku)
        return groupTransactions.map {
            ProductRealm(
                product = it.key,
                transactions = fromTransactionsResponseToEntity(it.value)
            )
        }
    }

    private fun fromTransactionsResponseToEntity(transactionResponse: List<TransactionResponse>):
        RealmList<TransactionRealm> {
        return transactionResponse.map(::fromTransactionResponseToEntity).toRealmList()
    }

    private fun fromTransactionResponseToEntity(transactionResponse: TransactionResponse):
        TransactionRealm {
        return transactionResponse.let {
            TransactionRealm(
                amount = it.amount.ifBlank { DEFAULT_AMOUNT },
                currency = it.currency.ifBlank { DEFAULT_CURRENCY }
            )
        }
    }

    fun fromRatesResponseToEntity(ratesResponse: List<RateResponse>): List<RateRealm> {
        return ratesResponse.map {
            RateRealm(it.from, it.to, it.rate)
        }
    }

    fun fromProductsEntityToModel(productsRealm: List<ProductRealm>): List<Product> {
        return productsRealm.map (::fromProductEntityToModel)
    }

    fun fromProductEntityToModel(productRealm: ProductRealm): Product {
        return Product(
            product = productRealm.product,
            transactions = fromTransactionsRealmToModel(productRealm.transactions)
        )
    }

    private fun fromTransactionsRealmToModel(transactionsRealm: RealmList<TransactionRealm>?):
        List<Transaction> {
        return transactionsRealm?.map {
            Transaction(
                amount = it.amount,
                currency = it.currency
            )
        } ?: listOf()

    }

    fun fromRatesEntityToModel(ratesRealm: List<RateRealm>): List<Rate> {
        return ratesRealm.map {
            Rate(it.from, it.to, it.rate)
        }
    }
}
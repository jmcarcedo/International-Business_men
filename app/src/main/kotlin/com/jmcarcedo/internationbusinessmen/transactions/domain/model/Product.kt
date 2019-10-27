package com.jmcarcedo.internationbusinessmen.transactions.domain.model

data class Product(
    val product: String,
    val transactions: List<Transaction>
)
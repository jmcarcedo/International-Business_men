package com.jmcarcedo.internationbusinessmen.transactions.vm.sealed

sealed class ProductsStatus {
    object Loading: ProductsStatus()
    data class Products(val products: List<String>): ProductsStatus()
}
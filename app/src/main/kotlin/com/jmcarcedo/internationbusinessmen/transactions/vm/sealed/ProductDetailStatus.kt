package com.jmcarcedo.internationbusinessmen.transactions.vm.sealed

import com.jmcarcedo.internationbusinessmen.transactions.domain.model.Transaction

sealed class ProductDetailStatus {
    object Loading: ProductDetailStatus()
    data class ProductDetail(val productDetail: Transaction): ProductDetailStatus()
}
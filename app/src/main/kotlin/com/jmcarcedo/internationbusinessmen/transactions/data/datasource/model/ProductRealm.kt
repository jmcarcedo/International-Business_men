package com.jmcarcedo.internationbusinessmen.transactions.data.datasource.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ProductRealm(
    @PrimaryKey
    var product: String = "",
    var transactions: RealmList<TransactionRealm>? = null
) : RealmObject() {

    companion object {
        const val PRODUCT_SKU = "product"
    }
}
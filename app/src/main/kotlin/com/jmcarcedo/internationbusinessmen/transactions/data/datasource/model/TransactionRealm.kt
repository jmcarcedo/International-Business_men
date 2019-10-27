package com.jmcarcedo.internationbusinessmen.transactions.data.datasource.model

import io.realm.RealmObject

open class TransactionRealm(
    var amount: String = "",
    var currency: String = ""
): RealmObject()
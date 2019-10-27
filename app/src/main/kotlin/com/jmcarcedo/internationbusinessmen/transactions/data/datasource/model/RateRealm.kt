package com.jmcarcedo.internationbusinessmen.transactions.data.datasource.model

import io.realm.RealmObject

open class RateRealm(
    var from: String = "",
    var to: String = "",
    var rate: String = ""
) : RealmObject()
package com.jmcarcedo.internationbusinessmen.core.data.datasource

import io.realm.RealmList

fun <T: Any> List<T>.toRealmList(): RealmList<T> {
    val realmList = RealmList<T>()
    realmList.addAll(this)
    return realmList
}
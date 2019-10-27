package com.jmcarcedo.internationbusinessmen.core.data.datasource

import io.realm.Realm
import io.realm.RealmConfiguration

class RealmInstanceHelper {

    private val realmConfiguration = buildRealmConfiguration()

    companion object {
        private const val INSTANCE_NAME = "Transactions"
        private const val INSTANCE_VERSION = 1L
    }

    private fun buildRealmConfiguration(): RealmConfiguration {
        return RealmConfiguration.Builder()
            .name(INSTANCE_NAME)
            .schemaVersion(INSTANCE_VERSION)
            .deleteRealmIfMigrationNeeded()
            .build()
    }

    fun getInstance(): Realm {
        //TODO check to implement errors
        return Realm.getInstance(realmConfiguration)
    }

    fun getRealmConfiguration(): RealmConfiguration = realmConfiguration

    fun deleteRealm() {
        Realm.deleteRealm(realmConfiguration)
    }
}
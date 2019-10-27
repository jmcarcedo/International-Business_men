package com.jmcarcedo.internationbusinessmen.transactions.data.datasource

import com.jmcarcedo.internationbusinessmen.core.data.datasource.RealmInstanceHelper
import com.jmcarcedo.internationbusinessmen.transactions.data.datasource.model.ProductRealm
import com.jmcarcedo.internationbusinessmen.transactions.data.datasource.model.RateRealm
import io.reactivex.Completable
import io.reactivex.Maybe
import javax.inject.Inject

class TransactionsRealmDataSource @Inject constructor(
    private val realmInstanceHelper: RealmInstanceHelper
) {

    fun insertProducts(products: List<ProductRealm>): Completable {
        return Completable.create { emitter ->
            try {
                realmInstanceHelper.getInstance().use { realmInstance ->
                    realmInstance.executeTransaction { realmTransaction ->
                        realmTransaction.insertOrUpdate(products)
                    }
                }
                emitter.onComplete()
            } catch (ex: IllegalArgumentException) {
                emitter.onError(ex)
            }
        }
    }

    fun getProducts(): Maybe<List<ProductRealm>> {
        return Maybe.create { emitter ->
            try {
                realmInstanceHelper.getInstance().use { realmInstance ->
                    var realmData: List<ProductRealm>? = null
                    realmInstance.executeTransaction {
                        val productsRealm = realmInstance.where(ProductRealm::class.java).findAll()

                        productsRealm?.let {
                            realmData = realmInstance.copyFromRealm(productsRealm)
                        }
                    }
                    realmData?.let { emitter.onSuccess(it) } ?: emitter.onComplete()
                }
            } catch (ex: IllegalArgumentException) {
                emitter.onError(ex)
            }
        }
    }

    fun insertRates(rates: List<RateRealm>): Completable {
        return Completable.create { emitter ->
            try {
                realmInstanceHelper.getInstance().use { realmInstance ->
                    realmInstance.executeTransaction { realmTransaction ->
                        realmTransaction.insertOrUpdate(rates)
                    }
                }
                emitter.onComplete()
            } catch (ex: IllegalArgumentException) {
                emitter.onError(ex)
            }
        }
    }

    fun getRates(): Maybe<List<RateRealm>> {
        return Maybe.create { emitter ->
            try {
                realmInstanceHelper.getInstance().use { realmInstance ->
                    var realmData: List<RateRealm>? = null
                    realmInstance.executeTransaction {
                        val ratesRealm = realmInstance.where(RateRealm::class.java).findAll()

                        ratesRealm?.let {
                            realmData = realmInstance.copyFromRealm(ratesRealm)
                        }
                    }
                    realmData?.let { emitter.onSuccess(it) } ?: emitter.onComplete()
                }
            } catch (ex: IllegalArgumentException) {
                emitter.onError(ex)
            }
        }
    }

    fun getProductDetail(product: String): Maybe<ProductRealm> {
        return Maybe.create { emitter ->
            try {
                realmInstanceHelper.getInstance().use { realmInstance ->
                    var realmData: ProductRealm? = null
                    realmInstance.executeTransaction {
                        val ratesRealm = realmInstance.where(ProductRealm::class.java)
                            .equalTo(ProductRealm.PRODUCT_SKU, product)
                            .findFirst()

                        ratesRealm?.let {
                            realmData = realmInstance.copyFromRealm(ratesRealm)
                        }
                    }
                    realmData?.let { emitter.onSuccess(it) } ?: emitter.onComplete()
                }
            } catch (ex: IllegalArgumentException) {
                emitter.onError(ex)
            }
        }
    }

    fun deleteDB(): Completable {
        return Completable.create{emitter ->
            realmInstanceHelper.deleteRealm()
            emitter.onComplete()
        }
    }
}
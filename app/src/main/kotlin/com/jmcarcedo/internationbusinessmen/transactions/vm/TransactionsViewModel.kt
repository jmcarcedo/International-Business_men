package com.jmcarcedo.internationbusinessmen.transactions.vm

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.jmcarcedo.internationbusinessmen.core.vm.BaseViewModel
import com.jmcarcedo.internationbusinessmen.transactions.domain.GetProducts
import com.jmcarcedo.internationbusinessmen.transactions.vm.sealed.ProductsStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class TransactionsViewModel @Inject constructor(private val getProducts: GetProducts) :
    BaseViewModel(), LifecycleObserver {


    private val productsLiveData: MutableLiveData<ProductsStatus> = MutableLiveData()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        getProducts()
    }

    private fun getProducts() {
        getProducts.execute()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { productsLiveData.value = ProductsStatus.Loading }
            .subscribeBy(
                onSuccess = {
                    productsLiveData.value = ProductsStatus.Products(it)
                    Log.i("product receive", it.toString())
                },
                onError = { Log.e("error retrieving product", it.toString()) }
            )
            .also(::addDisposable)
    }

    fun getProductsLiveData(): LiveData<ProductsStatus> = productsLiveData
}
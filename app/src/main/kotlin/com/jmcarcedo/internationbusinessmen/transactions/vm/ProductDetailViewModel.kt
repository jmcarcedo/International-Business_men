package com.jmcarcedo.internationbusinessmen.transactions.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jmcarcedo.internationbusinessmen.core.vm.BaseViewModel
import com.jmcarcedo.internationbusinessmen.transactions.domain.GetProductDetail
import com.jmcarcedo.internationbusinessmen.transactions.vm.sealed.ProductDetailStatus
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ProductDetailViewModel @Inject constructor(private val getProductDetail: GetProductDetail) :
    BaseViewModel() {

    private val productDetailLiveData: MutableLiveData<ProductDetailStatus> = MutableLiveData()

    fun getProductDetailLiveData(selectedProduct: String): LiveData<ProductDetailStatus>{
        getProductDetail(selectedProduct)
        return productDetailLiveData
    }

    private fun getProductDetail(selectedProduct: String) {
        getProductDetail.execute(selectedProduct)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { productDetailLiveData.value = ProductDetailStatus.Loading }
            .subscribeBy(
                onSuccess = { productDetailLiveData.value = ProductDetailStatus.ProductDetail(it) },
                onError = { Log.i("error retieving product details", it.toString()) }
            ).also(::addDisposable)
    }
}
package com.jmcarcedo.internationbusinessmen.core.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

fun <T: Any, L: LiveData<T>> BaseActivity.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}

inline fun <reified T: ViewModel> BaseActivity.getViewModel(
    viewModelFactory: ViewModelProvider.Factory
): T {
    return ViewModelProviders.of(this, viewModelFactory).get(T::class.java)
}
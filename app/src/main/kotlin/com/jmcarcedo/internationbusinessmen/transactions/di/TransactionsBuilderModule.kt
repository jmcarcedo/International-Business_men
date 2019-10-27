package com.jmcarcedo.internationbusinessmen.transactions.di

import androidx.lifecycle.ViewModel
import com.jmcarcedo.internationbusinessmen.core.di.ViewModelKey
import com.jmcarcedo.internationbusinessmen.transactions.ui.ProductDetailActivity
import com.jmcarcedo.internationbusinessmen.transactions.ui.TransactionsActivity
import com.jmcarcedo.internationbusinessmen.transactions.vm.ProductDetailViewModel
import com.jmcarcedo.internationbusinessmen.transactions.vm.TransactionsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class TransactionsBuilderModule {

    @ContributesAndroidInjector
    internal abstract fun contributeTransactionsActivity(): TransactionsActivity

    @Binds
    @IntoMap
    @ViewModelKey(TransactionsViewModel::class)
    internal abstract fun postTransactionsViewModel(viewModel: TransactionsViewModel): ViewModel

    @ContributesAndroidInjector
    internal abstract fun contributesProductDetailActivity(): ProductDetailActivity

    @Binds
    @IntoMap
    @ViewModelKey(ProductDetailViewModel::class)
    internal abstract fun postProductDetailViewModel(viewModel: ProductDetailViewModel): ViewModel
}
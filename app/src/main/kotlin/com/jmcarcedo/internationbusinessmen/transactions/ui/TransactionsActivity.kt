package com.jmcarcedo.internationbusinessmen.transactions.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmcarcedo.internationbusinessmen.R
import com.jmcarcedo.internationbusinessmen.core.ui.BaseActivity
import com.jmcarcedo.internationbusinessmen.core.ui.getViewModel
import com.jmcarcedo.internationbusinessmen.core.ui.observe
import com.jmcarcedo.internationbusinessmen.core.ui.showOrGone
import com.jmcarcedo.internationbusinessmen.transactions.ui.adapter.ProductsAdapter
import com.jmcarcedo.internationbusinessmen.transactions.vm.TransactionsViewModel
import com.jmcarcedo.internationbusinessmen.transactions.vm.sealed.ProductsStatus
import kotlinx.android.synthetic.main.activity_transactions.*
import kotlinx.android.synthetic.main.loading_progress.*
import javax.inject.Inject

class TransactionsActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val productsAdapter: ProductsAdapter by lazy {
        ProductsAdapter()
    }

    private val viewModel: TransactionsViewModel by lazy {
        getViewModel<TransactionsViewModel>(viewModelFactory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initObservers()
    }

    private fun initViews() {
        setContentView(R.layout.activity_transactions)
        products_recycler_view.apply {
            adapter = productsAdapter
            layoutManager = LinearLayoutManager(context)
            productsAdapter.onItemClick = onAdapterItemClick
        }
    }

    private val onAdapterItemClick: ((String) -> Unit)? = {
        goToDetail(it)
        Log.i("itemSelected", it)
    }

    private fun goToDetail(product: String) {
        val bundle = ProductDetailActivity.getBundle(product)
        Intent(this, ProductDetailActivity::class.java).apply {
            putExtras(bundle)
        }.also(::startActivity)
    }

    private fun initObservers() {
        observe(viewModel.getProductsLiveData(), ::handleProducts)
        lifecycle.addObserver(viewModel)
    }

    private fun handleProducts(products: ProductsStatus?) {
        when (products) {
            is ProductsStatus.Loading -> showLoading(true)
            is ProductsStatus.Products -> {
                showLoading(false)
                productsAdapter.setData(products.products)
            }
            else -> {

            }
        }
    }

    private fun showLoading(showLoading: Boolean) {
        progress_view.showOrGone(showLoading)
    }
}
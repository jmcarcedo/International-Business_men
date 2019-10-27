package com.jmcarcedo.internationbusinessmen.transactions.ui

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.jmcarcedo.internationbusinessmen.R
import com.jmcarcedo.internationbusinessmen.core.ui.BaseActivity
import com.jmcarcedo.internationbusinessmen.core.ui.getViewModel
import com.jmcarcedo.internationbusinessmen.core.ui.observe
import com.jmcarcedo.internationbusinessmen.core.ui.showOrGone
import com.jmcarcedo.internationbusinessmen.transactions.domain.model.Transaction
import com.jmcarcedo.internationbusinessmen.transactions.vm.ProductDetailViewModel
import com.jmcarcedo.internationbusinessmen.transactions.vm.sealed.ProductDetailStatus
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.loading_progress.*
import javax.inject.Inject

class ProductDetailActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: ProductDetailViewModel by lazy {
        getViewModel<ProductDetailViewModel>(viewModelFactory)
    }

    private val selectedProduct: String by lazy {
        intent.getStringExtra(EXTRA_PRODUCT_SKU)
    }

    companion object {
        private const val EXTRA_PRODUCT_SKU = "ProductDetailActivity#EXTRA_PRODUCT_SKU"

        fun getBundle(product: String): Bundle {
            return bundleOf(EXTRA_PRODUCT_SKU to product)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initObserver()
    }

    private fun initView() {
        setContentView(R.layout.activity_product_detail)
    }

    private fun initObserver() {
        observe(viewModel.getProductDetailLiveData(selectedProduct), ::handleProductDetail)
    }

    private fun handleProductDetail(productDetail: ProductDetailStatus?) {
        when (productDetail) {
            is ProductDetailStatus.Loading -> showLoading(true)
            is ProductDetailStatus.ProductDetail -> {
                showLoading(false)
                setViews(selectedProduct, productDetail.productDetail)
            }
            else -> {

            }
        }
    }

    private fun showLoading(showLoading: Boolean) {
        progress_view.showOrGone(showLoading)
    }

    private fun setViews(arguments: String, productDetail: Transaction) {
        product_detail_product_name.text = arguments
        product_detail_amount_amount.text = productDetail.amount
        product_detail_amount_currency.text = productDetail.currency
    }
}
package com.jmcarcedo.internationbusinessmen.transactions.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmcarcedo.internationbusinessmen.R
import com.jmcarcedo.internationbusinessmen.transactions.ui.adapter.ProductsAdapter.ProductsViewHolder
import kotlinx.android.synthetic.main.cell_product.view.*

class ProductsAdapter : RecyclerView.Adapter<ProductsViewHolder>() {

    private var data: List<String> = listOf()

    var onItemClick: ((String) -> Unit)? = null

    fun setData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_product, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = data[position]
        holder.itemView.cell_name.text = product
    }

    inner class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val itemClick = data[adapterPosition]
            onItemClick?.invoke(itemClick)
        }


    }
}
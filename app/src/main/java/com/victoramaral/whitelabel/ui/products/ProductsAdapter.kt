package com.victoramaral.whitelabel.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.victoramaral.whitelabel.databinding.ProductItemBinding
import com.victoramaral.whitelabel.domain.model.Product
import com.victoramaral.whitelabel.util.toCurrency

class ProductsAdapter() : ListAdapter<Product, ProductsViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {

            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class ProductsViewHolder(
    private val itemBinding: ProductItemBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(product: Product) {
        itemBinding.run {
            Glide.with(itemView)
                .load(product.imageUrl)
                .fitCenter()
                .into(productImageview)

            productDescriptionTextview.text = product.description
            productPriceTextview.text = product.price.toCurrency()
        }
    }

    companion object {
        fun create(parent: ViewGroup): ProductsViewHolder {
            val itemBinding = ProductItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return ProductsViewHolder(itemBinding)
        }
    }
}
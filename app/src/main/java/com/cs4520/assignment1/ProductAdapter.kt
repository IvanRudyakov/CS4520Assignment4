package com.cs4520.assignment1

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment1.databinding.ProductListItemBinding

class ProductAdapter() : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var products: List<Product> = listOf();

    fun setProducts(ps: List<Product>) {
        products = ps;
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size

    class ViewHolder(val binding: ProductListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.nameText.text = product.name;
            binding.nameText.visibility = if(product.name == null) View.GONE else View.VISIBLE;

            binding.priceText.text = "$ " + String.format("%.2f", product.price);
            binding.priceText.visibility = if(product.price == null) View.GONE else View.VISIBLE;

            binding.expText.text = product.expiryDate;
            binding.expText.visibility = if(product.expiryDate == null) View.GONE else View.VISIBLE;

            val backgroundColor = when (product.type) {
                "Equipment" -> R.color.light_red
                "Food" -> R.color.light_yellow
                else -> R.color.light_red
            }
            binding.root.setBackgroundColor(ContextCompat.getColor(binding.root.context, backgroundColor))

            val imageResource = when (product.type) {
                "Equipment" -> R.drawable.tools
                "Food" -> R.drawable.food
                else -> R.drawable.food
            }
            binding.image.setImageResource(imageResource)
        }
    }
}
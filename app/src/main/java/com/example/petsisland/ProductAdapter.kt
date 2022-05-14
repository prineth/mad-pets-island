package com.example.petsisland

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ProductAdapter(private var productList: MutableList<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    //onclick option ---start
//    private lateinit var mListener: onItemClickListener
//
//
//    interface onItemClickListener{
//        fun onItemClick(position: Int)
//    }
//
//    fun setOnItemClickListener(listener: onItemClickListener){
//        mListener = listener
//    }

    //onclick option ---end

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductAdapter.ProductViewHolder {

        val layoutView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.product_card_view, parent, false)
        return ProductViewHolder(layoutView)

    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {
        Picasso.get()
            .load(productList[position].img)
            .into(holder.productImage)
        holder.productTitle.text = productList[position].title
        holder.productPrice.text = productList[position].price


    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var productImage: ImageView = view.findViewById(R.id.product_image)
        var productTitle: TextView = view.findViewById(R.id.product_title)
        var productPrice: TextView = view.findViewById(R.id.product_price)
    }
}
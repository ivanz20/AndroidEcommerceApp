package com.example.pia_moviles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter2():RecyclerView.Adapter<ProductAdapter2.ProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductAdapter2.ProductViewHolder {

        val layoutView:View = LayoutInflater.from(parent.context).
            inflate(R.layout.product_card_view2, parent,false)
        return ProductViewHolder(layoutView)

    }

    override fun onBindViewHolder(holder: ProductAdapter2.ProductViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 8
    }

    class ProductViewHolder(view: View):RecyclerView.ViewHolder(view){
            var productImage: ImageView = view.findViewById(R.id.product_image)
            var productTile: TextView = view.findViewById(R.id.product_title)
            var productPrice: TextView = view.findViewById(R.id.product_price)

    }
}
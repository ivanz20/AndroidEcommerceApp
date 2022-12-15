package com.example.pia_moviles.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pia_moviles.R

class ProductAdapter():RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {

        val layoutView:View = LayoutInflater.from(parent.context).
            inflate(R.layout.product_card_view, parent,false)
        return ProductViewHolder(layoutView)

    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 4
    }

    class ProductViewHolder(view: View):RecyclerView.ViewHolder(view){
            var productImage: ImageView = view.findViewById(R.id.product_image)
            var productTile: TextView = view.findViewById(R.id.coment_card)
            var productPrice: TextView = view.findViewById(R.id.product_price)
    }
}
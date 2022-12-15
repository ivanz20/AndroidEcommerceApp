package com.example.pia_moviles.Adaptadores

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pia_moviles.Modelos.ProductoModel
import com.example.pia_moviles.R
import okio.ByteString.Companion.decodeBase64
import kotlin.properties.Delegates

class MisProductosAdapter(private val ProductList : ArrayList<ProductoModel>):RecyclerView.Adapter<MisProductosAdapter.ProductViewHolder>() {


    var prueba by Delegates.notNull<Int>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {

        val layoutView:View = LayoutInflater.from(parent.context).
            inflate(R.layout.product_card_view2, parent,false)
        return ProductViewHolder(layoutView)

    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val Producto : ProductoModel = ProductList[position]

        val img2 = Producto.imagen?.decodeBase64()?.toByteArray()
        val imageBitmap: Bitmap? = img2?.let { BitmapFactory.decodeByteArray(img2, 0, it.size) }
        holder.productTile.text = Producto.nombreproducto.toString()
        holder.productPrice.text = "$" + Producto.precio.toString() + " pesos"
        holder.productImage.setImageBitmap(imageBitmap)

    }

    override fun getItemCount(): Int {

        return ProductList.size
    }

    class ProductViewHolder(view: View):RecyclerView.ViewHolder(view){

            var productImage: ImageView = view.findViewById(R.id.product_image)
            var productTile: TextView = view.findViewById(R.id.coment_card)
            var productPrice: TextView = view.findViewById(R.id.product_price)

    }
}
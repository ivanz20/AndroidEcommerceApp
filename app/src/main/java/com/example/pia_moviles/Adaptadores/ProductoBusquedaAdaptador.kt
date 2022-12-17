package com.example.pia_moviles.Adaptadores

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.pia_moviles.*
import com.example.pia_moviles.Modelos.ProductoModel
import okio.ByteString.Companion.decodeBase64


class ProductoBusquedaAdaptador(private val ProductList : ArrayList<ProductoModel>) :RecyclerView.Adapter<ProductoBusquedaAdaptador.ProductViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {

        val layoutView:View = LayoutInflater.from(parent.context).
            inflate(R.layout.producto_busqueda, parent,false)
        return ProductViewHolder(layoutView)

    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val Producto : ProductoModel = ProductList[position]
        val img2 = Producto.imagen?.decodeBase64()?.toByteArray()
        val imageBitmap: Bitmap? = img2?.let { BitmapFactory.decodeByteArray(img2, 0, it.size) }
        holder.productTile.text = Producto.nombreproducto.toString()
        holder.productPrice.text = "$" + Producto.precio.toString() + " pesos"
        holder.productImage.setImageBitmap(imageBitmap)
        holder.idproducto.text = Producto.idproducto.toString()

        holder.verproducto.setOnClickListener(object: View.OnClickListener {

            override fun onClick(v: View?){
                val activity=v!!.context as AppCompatActivity
                val ProductoFrag = VerProducto()
                val editor = holder.preferences?.edit()
                editor?.putInt("idproducto", holder.idproducto.text.toString().toInt())
                editor?.commit()
                activity.supportFragmentManager.beginTransaction().replace(R.id.drawer_layout,ProductoFrag).addToBackStack(null).commit()

            }
        })
    }

    override fun getItemCount(): Int {
        return ProductList.size
    }

    class ProductViewHolder(view: View):RecyclerView.ViewHolder(view){

        var productImage: ImageView = view.findViewById(R.id.product_image)
        var productTile: TextView = view.findViewById(R.id.tituloproducto)
        var productPrice: TextView = view.findViewById(R.id.product_price)
        var idproducto: TextView = view.findViewById(R.id.idproductotarjeta)
        var verproducto: Button = view.findViewById(R.id.VerArtiInicio)
        val preferences = view.getContext().getSharedPreferences("producto", Context.MODE_PRIVATE)


    }
}
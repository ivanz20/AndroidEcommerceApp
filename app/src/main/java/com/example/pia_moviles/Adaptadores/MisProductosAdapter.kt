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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pia_moviles.EditarProducto
import com.example.pia_moviles.Modelos.ProductoModel
import com.example.pia_moviles.R
import com.example.pia_moviles.Servicios.ProductosServicio
import com.example.pia_moviles.Servicios.RestEngine
import com.example.pia_moviles.VerProducto
import okio.ByteString.Companion.decodeBase64
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.AccessController.getContext
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
        holder.idproducto.text = Producto.idproducto.toString()

        holder.editarboton.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?){
                val activity=v!!.context as AppCompatActivity
                val ProductoFrag = VerProducto()
                val editor = holder.preferences?.edit()
                editor?.putInt("idproducto2", holder.idproducto.text.toString().toInt())
                editor?.commit()
                activity.supportFragmentManager.beginTransaction().replace(R.id.drawer_layout,EditarProducto()).addToBackStack(null).commit()

            }
        })

        holder.eliminarboton.setOnClickListener{
            var iddeproducto = holder.idproducto.text
            val ServiciosProducto: ProductosServicio = RestEngine.getRestEngine().create(
                ProductosServicio::class.java)

            var result: Call<Int> = ServiciosProducto.EliminarById(iddeproducto.toString().toInt())

            result.enqueue(object : Callback<Int> {
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    var resp = response.body()
                    if(resp!= null){

                    }


                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                    println(t.toString())
                }

            })

        }

    }

    override fun getItemCount(): Int {

        return ProductList.size
    }

    class ProductViewHolder(view: View):RecyclerView.ViewHolder(view){

            var productImage: ImageView = view.findViewById(R.id.product_image)
            var productTile: TextView = view.findViewById(R.id.coment_card)
            var productPrice: TextView = view.findViewById(R.id.product_price)
            var idproducto: TextView = view.findViewById(R.id.idproductotarjeta)
            var editarboton: Button = view.findViewById(R.id.EditarArticulo)
            var eliminarboton: Button = view.findViewById(R.id.EliminarArticulo)
            val preferences = view.getContext().getSharedPreferences("producto", Context.MODE_PRIVATE)


    }
}
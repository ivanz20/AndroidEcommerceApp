package com.example.pia_moviles

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pia_moviles.Activity.ui.gallery.PerfilUsuarioFragment
import com.example.pia_moviles.Adaptadores.ComentariosAdapter
import com.example.pia_moviles.Adaptadores.MisProductosAdapter
import com.example.pia_moviles.Modelos.ComentarioModel
import com.example.pia_moviles.Modelos.ProductoModel
import com.example.pia_moviles.Modelos.UsuarioModel
import com.example.pia_moviles.Servicios.ComentariosServicio
import com.example.pia_moviles.Servicios.ProductosServicio
import com.example.pia_moviles.Servicios.RestEngine
import com.example.pia_moviles.Servicios.UsuarioServicio
import okio.ByteString.Companion.decodeBase64
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VerProducto.newInstance] factory method to
 * create an instance of this fragment.
 */
class VerProducto : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var ComentsList = mutableListOf<ComentarioModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_ver_producto, container, false)
        val spinner = view?.findViewById<Spinner>(R.id.spinnercalificacion)
        val calif = resources.getStringArray(R.array.calif)
        val idproducto = 3

        val SerProducto : ProductosServicio  = RestEngine.getRestEngine().create(
            ProductosServicio::class.java)
        val result: Call<ProductoModel> = SerProducto.GetProductById(idproducto)
        result.enqueue(object : Callback<ProductoModel> {
            override fun onResponse(call: Call<ProductoModel>, response: Response<ProductoModel>) {
                val item = response.body()
                if (item != null) {
                    val img2 = item.imagen?.decodeBase64()?.toByteArray()
                    val imageBitmap: Bitmap? = img2?.let { BitmapFactory.decodeByteArray(img2, 0, it.size) }
                    view.findViewById<ImageView>(R.id.fotoVerProducto).setImageBitmap(imageBitmap)
                    view.findViewById<TextView>(R.id.productoname).setText(item.nombreproducto.toString())
                    view.findViewById<TextView>(R.id.descripproduct).setText(item.descripcion.toString())
                    val calificacion = "Calificación " + item.calificacion.toString() + " de 5 estrellas"
                    view.findViewById<TextView>(R.id.califa).setText(calificacion)

                }else{
                    Toast.makeText(getContext(), "Hubo un error al consultar el producto", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProductoModel>, t: Throwable) {
                println(t.toString())
            }
        })



            val adaptador =
            getContext()?.let { ArrayAdapter(it,android.R.layout.simple_spinner_item,calif) }
        spinner?.adapter = adaptador

        var recyclerView: RecyclerView = view.findViewById(R.id.rv_comentarios)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context,
            1,
            GridLayoutManager.VERTICAL,
            false)

        val SerComentario : ComentariosServicio  = RestEngine.getRestEngine().create(
            ComentariosServicio::class.java)

        val resultado: Call<List<ComentarioModel>>  = SerComentario.GetByProductId(idproducto)

        resultado.enqueue(object : Callback<List<ComentarioModel>> {

            override fun onResponse(call: Call<List<ComentarioModel>>, response: Response<List<ComentarioModel>>) {
                val item = response.body()
                if (item != null) {
                    for(comment in item){
                        ComentsList.add(comment)
                    }
                }
            }

            override fun onFailure(call: Call<List<ComentarioModel>>, t: Throwable) {
                println(t.toString())
            }
        })

        val adapter = ComentariosAdapter(ComentsList as ArrayList<ComentarioModel>)
        recyclerView.adapter = adapter

        view.findViewById<Button>(R.id.btn_addcoment).setOnClickListener {
            val comentario = view.findViewById<EditText>(R.id.comentariotext) as EditText
            val calif = view.findViewById<Spinner>(R.id.spinnercalificacion)
            var ComentsList2 = mutableListOf<ComentarioModel>()

            val resultado3: Call<ComentarioModel>  = SerComentario.AgregarComentario(
                ComentarioModel(
                    null,
                    idproducto,
                    34,
                    comentario.text.toString(),
                    "Nombre Completo",
                    calif.selectedItem.toString().toInt()
                )
            )
            resultado3.enqueue(object : Callback<ComentarioModel> {

                override fun onResponse(call: Call<ComentarioModel>, response: Response<ComentarioModel>) {
                    val item = response.body()
                    if (item != null) {
                        Toast.makeText(getContext(), "Comentario registrado", Toast.LENGTH_SHORT).show()
                        val resultado4: Call<List<ComentarioModel>>  = SerComentario.GetByProductId(idproducto)

                        resultado4.enqueue(object : Callback<List<ComentarioModel>> {

                            override fun onResponse(call: Call<List<ComentarioModel>>, response: Response<List<ComentarioModel>>) {
                                val item = response.body()
                                if (item != null) {
                                    for(comment in item){
                                        ComentsList2.add(comment)
                                    }
                                    val adapter2 = ComentariosAdapter(ComentsList2 as ArrayList<ComentarioModel>)
                                    recyclerView.adapter = adapter2
                                    comentario.setText("")
                                }
                            }

                            override fun onFailure(call: Call<List<ComentarioModel>>, t: Throwable) {
                                println(t.toString())
                            }
                        })



                    }else{
                        Toast.makeText(getContext(), "Error al realizar tu comentario", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ComentarioModel>, t: Throwable) {
                    println(t.toString())
                }
            })


        }
        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VerProducto.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VerProducto().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
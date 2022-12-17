package com.example.pia_moviles

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.pia_moviles.Activity.ui.gallery.PerfilUsuarioFragment
import com.example.pia_moviles.Modelos.ProductoModel
import com.example.pia_moviles.Servicios.ProductosServicio
import com.example.pia_moviles.Servicios.RestEngine
import okio.ByteString.Companion.decodeBase64
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditarProducto.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditarProducto : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var  fotoproducto : ImageView

    private val responseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activityResult ->
        if(activityResult.resultCode == Activity.RESULT_OK){
            fotoproducto.setImageURI(activityResult.data?.data)
        }
    }

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

        var view = inflater.inflate(R.layout.fragment_editar_producto, container, false)
        val spinner = view?.findViewById<Spinner>(R.id.spinnercategorias)
        fotoproducto = view?.findViewById<ImageView>(R.id.imageView2editar) as ImageView
        val categorias = resources.getStringArray(R.array.categorias)
        val adaptador =
            getContext()?.let { ArrayAdapter(it,android.R.layout.simple_spinner_item,categorias) }
        spinner?.adapter = adaptador


        var pref: SharedPreferences? = null
        pref = getContext()?.getSharedPreferences("producto", AppCompatActivity.MODE_PRIVATE)
        var idproducto = pref?.getInt("idproducto2",0)

//        var idproducto = 10

        val SerProducto : ProductosServicio = RestEngine.getRestEngine().create(
            ProductosServicio::class.java)
        val result: Call<ProductoModel> = SerProducto.GetProductById(idproducto)
        result.enqueue(object : Callback<ProductoModel> {
            override fun onResponse(call: Call<ProductoModel>, response: Response<ProductoModel>) {
                val item = response.body()
                if (item != null) {
                    val img2 = item.imagen?.decodeBase64()?.toByteArray()
                    val imageBitmap: Bitmap? = img2?.let { BitmapFactory.decodeByteArray(img2, 0, it.size) }
                    view.findViewById<ImageView>(R.id.imageView2editar).setImageBitmap(imageBitmap)
                    view.findViewById<TextView>(R.id.nombreproductoeditar).setText(item.nombreproducto.toString())
                    view.findViewById<TextView>(R.id.descripcionproductoeditar).setText(item.descripcion.toString())
                    view.findViewById<TextView>(R.id.precioeditar).setText(item.precio.toString())


                }else{
                    Toast.makeText(getContext(), "Hubo un error al consultar el producto", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProductoModel>, t: Throwable) {
                println(t.toString())
            }
        })

        view.findViewById<Button>(R.id.btnimageneditar).setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            intent.putExtra("SELECT_PICTURE",200)
            responseLauncher.launch(intent)
        }

        view.findViewById<Button>(R.id.btneditarproducto).setOnClickListener{
            var nombreproducto = view?.findViewById<EditText>(R.id.nombreproductoeditar) as EditText
            var descripcion =view?.findViewById<EditText>(R.id.descripcionproductoeditar) as EditText
            var precio = view?.findViewById<EditText>(R.id.precioeditar) as EditText
            var categoria = view?.findViewById<Spinner>(R.id.spinnercategorias) as Spinner
            var fotoRegistro = view?.findViewById<ImageView>(R.id.imageView2editar) as ImageView
            fotoproducto = fotoRegistro
            val bitmap = (fotoproducto.getDrawable() as BitmapDrawable).bitmap
            val comprime = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG,50,comprime)
            var imageByteArray: ByteArray = comprime.toByteArray()
            val encodedString:String = Base64.getEncoder().encodeToString(imageByteArray)

            val ServiciosProducto: ProductosServicio = RestEngine.getRestEngine().create(ProductosServicio::class.java)
            var result: Call<ProductoModel> = ServiciosProducto.EditById(
                ProductoModel(
                    idproducto,
                    nombreproducto.text.toString(),
                    descripcion.text.toString(),
                    (precio.text.toString()).toFloat(),
                    categoria.selectedItem.toString(),
                    encodedString,
                    null,
                    null
                )
            )
            result.enqueue(object : Callback<ProductoModel> {
                override fun onResponse(call: Call<ProductoModel>, response: Response<ProductoModel>) {
                    val item = response.body()
                    if (item != null) {
                        Toast.makeText(getContext(), "Producto Editado", Toast.LENGTH_SHORT).show()
                        requireActivity().supportFragmentManager.beginTransaction().replace((requireView().parent as ViewGroup).id, PerfilUsuarioFragment()).commit()
                    }
                    else {
                        Toast.makeText(getContext(), "Hubo un error al registrar el producto", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<ProductoModel>, t: Throwable) {
                    println(t.toString())
                }
            })


        }



        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditarProducto.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditarProducto().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
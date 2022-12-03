package com.example.pia_moviles

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputBinding
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.pia_moviles.Modelos.ProductoModel
import com.example.pia_moviles.Modelos.UsuarioModel
import com.example.pia_moviles.Servicios.ProductosServicio
import com.example.pia_moviles.Servicios.RestEngine
import com.example.pia_moviles.databinding.ActivityMainBinding
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
 * Use the [RegistroProductosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistroProductosFragment : Fragment() {


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
        var view = inflater.inflate(R.layout.fragment_registro_productos, container, false)

        val spinner = view?.findViewById<Spinner>(R.id.spinnercategorias)
        val categorias = resources.getStringArray(R.array.categorias)
        fotoproducto = view?.findViewById<ImageView>(R.id.imageView2) as ImageView

        val adaptador =
            getContext()?.let { ArrayAdapter(it,android.R.layout.simple_spinner_item,categorias) }
        spinner?.adapter = adaptador

        view?.findViewById<Button>(R.id.btnproducto)?.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            intent.putExtra("SELECT_PICTURE",200)
            responseLauncher.launch(intent)
        }
        view?.findViewById<Button>(R.id.btnregistrar)?.setOnClickListener{

            var nombreproducto = view?.findViewById<EditText>(R.id.nombreproducto) as EditText
            var descripcion =view?.findViewById<EditText>(R.id.descripcionproducto) as EditText
            var precio = view?.findViewById<EditText>(R.id.precio) as EditText
            var categoria = view?.findViewById<Spinner>(R.id.spinnercategorias) as Spinner
            var fotoRegistro = view?.findViewById<ImageView>(R.id.imageView2) as ImageView
            fotoproducto = fotoRegistro

            var pref: SharedPreferences? = null
            pref = getContext()?.getSharedPreferences("usuario", AppCompatActivity.MODE_PRIVATE)

            var iduser = pref?.getInt("Id",0)

            val ServiciosProducto: ProductosServicio = RestEngine.getRestEngine().create(ProductosServicio::class.java)
            val bitmap = (fotoproducto.getDrawable() as BitmapDrawable).bitmap
            val comprime = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG,50,comprime)
            var imageByteArray: ByteArray = comprime.toByteArray()
            val encodedString:String = Base64.getEncoder().encodeToString(imageByteArray)

            var result: Call<ProductoModel> = ServiciosProducto.AgregarProducto(
                ProductoModel(
                 null,
                 nombreproducto.text.toString(),
                 descripcion.text.toString(),
                 (precio.text.toString()).toFloat(),
                 categoria.selectedItem.toString(),
                 encodedString,
                 null,
                    iduser
                )
            )
            result.enqueue(object : Callback<ProductoModel> {
                override fun onResponse(call: Call<ProductoModel>, response: Response<ProductoModel>) {
                    val item = response.body()
                    if (item != null) {
                            Toast.makeText(getContext(), "Producto registrado", Toast.LENGTH_SHORT).show()
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


        // Inflate the layout for this fragment
        return view;
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegistroProductosFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegistroProductosFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
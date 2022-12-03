package com.example.pia_moviles

import android.annotation.SuppressLint
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
import com.example.pia_moviles.Modelos.UsuarioModel
import com.example.pia_moviles.Servicios.RestEngine
import com.example.pia_moviles.Servicios.UsuarioServicio
import okio.ByteString.Companion.decodeBase64
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.regex.Pattern

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditarInformacionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditarInformacionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var fotoElegida : ByteArray
    lateinit var  fotoRegistro2 : ImageView
    lateinit var tvfoto : ImageView
    lateinit var tvname : TextView

    private val responseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activityResult ->
        if(activityResult.resultCode == Activity.RESULT_OK){
            fotoRegistro2.setImageURI(activityResult.data?.data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("WrongThread")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        var view = inflater.inflate(R.layout.fragment_editar_informacion, container, false)


         tvfoto = view?.findViewById<ImageView>(R.id.fotoEditar)!!
         tvname = view?.findViewById<TextView>(R.id.editarNombre)
         loadData()

        view?.findViewById<Button>(R.id.btn_editarfoto)?.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            intent.putExtra("SELECT_PICTURE",200)
            responseLauncher.launch(intent)
        }

        var names = view?.findViewById<EditText>(R.id.editarNombre) as EditText
        var password = view?.findViewById<EditText>(R.id.editarcontra) as EditText
        var fotoRegistro = view?.findViewById<ImageView>(R.id.fotoEditar) as ImageView
        fotoRegistro2 = fotoRegistro

        var pref: SharedPreferences? = null
        pref = getContext()?.getSharedPreferences("usuario", AppCompatActivity.MODE_PRIVATE)

        var iduser = pref?.getInt("Id",0)

        val bitmaps = (fotoRegistro2.getDrawable() as BitmapDrawable).bitmap
        val comprime = ByteArrayOutputStream()
        bitmaps.compress(Bitmap.CompressFormat.JPEG, 10, comprime)
        var imageByteArray: ByteArray = comprime.toByteArray()
        fotoElegida = imageByteArray;

        view?.findViewById<Button>(R.id.btn_editardata)?.setOnClickListener {
            if (names.text.toString().isEmpty() || password.text.toString().isEmpty()) {
                Toast.makeText(getContext(), "Llena todos los datos", Toast.LENGTH_SHORT).show()
            } else {
                if (isValidPasswordFormat(password.text.toString())) {
                    val ServicioUsuario: UsuarioServicio = RestEngine.getRestEngine().create(
                        UsuarioServicio::class.java)
                    val encodedString:String = Base64.getEncoder().encodeToString(fotoElegida)
                    val result: Call<UsuarioModel> = ServicioUsuario.EditarUsuario(
                        UsuarioModel(
                            iduser,
                            names.text.toString(),
                            null,
                           null,
                            password.text.toString(),
                            null,
                            encodedString,
                            null,
                            null,
                            null
                        )
                    )
                    result.enqueue(object : Callback<UsuarioModel> {
                        override fun onResponse(call: Call<UsuarioModel>, response: Response<UsuarioModel>) {
                            val item = response.body()
                            if (item != null) {
                                if(item.status =="exists"){ Toast.makeText(getContext(), "Ya esta registrado este email", Toast.LENGTH_SHORT).show()
                                }else{
                                    Toast.makeText(getContext(), "Usuario Editado", Toast.LENGTH_SHORT).show()
                                    requireActivity().supportFragmentManager.beginTransaction().replace((requireView().parent as ViewGroup).id, PerfilUsuarioFragment()).commit()

                                }
                            }
                            else {
                                Toast.makeText(getContext(), "Hubo un error al editar", Toast.LENGTH_SHORT).show()

                            }
                        }

                        override fun onFailure(call: Call<UsuarioModel>, t: Throwable) {
                            println(t.toString())
                        }
                    })

                } else {
                    Toast.makeText(getContext(), "Revisa tu contraseña", Toast.LENGTH_SHORT).show()
                }
            }
        }
        view?.findViewById<Button>(R.id.btn_regresarperfil)?.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace((requireView().parent as ViewGroup).id, com.example.pia_moviles.Activity.ui.gallery.PerfilUsuarioFragment()).commit()

        }

        return  view
    }

    private fun loadData() {
        var pref: SharedPreferences? = null
        pref = getContext()?.getSharedPreferences("usuario", AppCompatActivity.MODE_PRIVATE)

        var img = pref?.getString("Image","")
        var nameuser = pref?.getString("Nombre","")



        if(!img!!.isEmpty()) {
            val img2 = img.decodeBase64()?.toByteArray()
            val imageBitmap: Bitmap? = img2?.let { BitmapFactory.decodeByteArray(img2, 0, it.size) }
            tvfoto?.setImageBitmap(imageBitmap)
            tvname?.setText(nameuser)
        }


    }
    fun isValidPasswordFormat(password: String): Boolean {
        val passwordREGEX = Pattern.compile("^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,}" +               //at least 8 characters
                "$");
        return passwordREGEX.matcher(password).matches()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditarInformacionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditarInformacionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
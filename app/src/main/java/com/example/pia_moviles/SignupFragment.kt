package com.example.pia_moviles

import android.R.attr.data
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.example.pia_moviles.Modelos.UsuarioModel
import com.example.pia_moviles.Servicios.RestEngine
import com.example.pia_moviles.Servicios.UsuarioServicio
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
 * Use the [SignupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignupFragment : Fragment() {

    var pref: SharedPreferences? = null

    var IVPreviewImage: ImageView? = null

    var SELECT_PICTURE = 200

    lateinit var fotoElegida : ByteArray
    lateinit var  fotoRegistro2 : ImageView

    private val responseLauncher = registerForActivityResult(StartActivityForResult()){activityResult ->
        if(activityResult.resultCode == Activity.RESULT_OK){
            fotoRegistro2.setImageURI(activityResult.data?.data)
            println(activityResult.data?.data)
            val bitmaps = (fotoRegistro2.getDrawable() as BitmapDrawable).bitmap
            val comprime = ByteArrayOutputStream()
            bitmaps.compress(Bitmap.CompressFormat.JPEG, 10, comprime)
            var imageByteArray: ByteArray = comprime.toByteArray()
            fotoElegida = imageByteArray;
        }
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



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
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_signup, container, false)

        view.findViewById<Button>(R.id.btn_regresarinicio).setOnClickListener{
            var navRegister = activity as FragmentNavigation
            navRegister.navigateFrag(LoginFragment(),false)
        }

        view.findViewById<Button>(R.id.btn_cargarfoto).setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            intent.putExtra("SELECT_PICTURE",200)
            responseLauncher.launch(intent)
        }

        var names = view?.findViewById<EditText>(R.id.textNombres) as EditText
        var apellidos =view?.findViewById<EditText>(R.id.textApellidos) as EditText
        var correo = view?.findViewById<EditText>(R.id.textCorreo) as EditText
        var password = view?.findViewById<EditText>(R.id.textPassword) as EditText
        var telefono = view?.findViewById<EditText>(R.id.textTelefono) as EditText
        var fotoRegistro = view?.findViewById<ImageView>(R.id.fotoRegistro) as ImageView
        fotoRegistro2 = fotoRegistro

        view.findViewById<Button>(R.id.btn_registrarusuario).setOnClickListener{
            if (names.toString().length == 0 || apellidos.text.toString().length == 0 || correo.text.toString().length == 0|| password.text.toString().length == 0 || telefono.text.toString().length == 0) {
                Toast.makeText(getContext(), "Llena todos los datos", Toast.LENGTH_SHORT).show()
            }else{
                if(isValidPasswordFormat(password.text.toString())){
                    val ServicioUsuario: UsuarioServicio = RestEngine.getRestEngine().create(UsuarioServicio::class.java)
                    val encodedString:String = Base64.getEncoder().encodeToString(fotoElegida)
                    val result: Call<UsuarioModel> = ServicioUsuario.AgregarUsuario(
                        UsuarioModel(
                            null,
                            names.text.toString(),
                            apellidos.text.toString(),
                            correo.text.toString(),
                            password.text.toString(),
                            telefono.text.toString(),
                            encodedString.toString(),
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
                                    Toast.makeText(getContext(), "Estas registrado", Toast.LENGTH_SHORT).show()
                                    var navRegister = activity as FragmentNavigation
                                    navRegister.navigateFrag(LoginFragment(),false)
                                }
                            }
                            else {
                                Toast.makeText(getContext(), "Hubo un error al registrarte", Toast.LENGTH_SHORT).show()

                            }
                        }

                        override fun onFailure(call: Call<UsuarioModel>, t: Throwable) {
                            println(t.toString())
                        }
                    })

                }else{
                    Toast.makeText(getContext(), "Revisa tu contraseña", Toast.LENGTH_SHORT).show()
                }
            }

        }

        return view;

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
         * @return A new instance of fragment SignupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
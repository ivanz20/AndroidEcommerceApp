package com.example.pia_moviles

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pia_moviles.Activity.HomeFragment
import com.example.pia_moviles.Activity.NavBar
import com.example.pia_moviles.Modelos.UsuarioModel
import com.example.pia_moviles.Servicios.RestEngine
import com.example.pia_moviles.Servicios.UsuarioServicio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    var pref: SharedPreferences? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        pref = requireContext().getSharedPreferences("usuario",AppCompatActivity.MODE_PRIVATE)

    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_login, container, false)



        view.findViewById<Button>(R.id.btn_inicio).setOnClickListener{
            var correo = view?.findViewById<EditText>(R.id.editTextUsuario) as EditText
            var password = view?.findViewById<EditText>(R.id.editTextTextPassword2) as EditText

            if(correo.text.isEmpty() || password.text.isEmpty()){
                Toast.makeText(getContext(), "Llena todos los campos", Toast.LENGTH_SHORT).show()
            }else{
                val ServicioUsuario: UsuarioServicio = RestEngine.getRestEngine().create(
                    UsuarioServicio::class.java)
                val result: Call<UsuarioModel> = ServicioUsuario.InicioSesion(
                    UsuarioModel(
                        null,
                        null,
                        null,
                        correo.text.toString(),
                        password.text.toString(),
                        null,
                        null,
                        null,
                        null,
                        null
                    )
                )
                result.enqueue(object : Callback<UsuarioModel> {
                    override fun onResponse(call: Call<UsuarioModel>, response: Response<UsuarioModel>) {
                        val item = response.body()
                        if (item == null) {
                            Toast.makeText(getContext(), "Credenciales Erroneas", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            val id: Int = item.iduser.toString().toInt()
                            val nombre:  String = item.nombre.toString()
                            val apellido: String = item.apellido.toString()
                            val email: String = item.email.toString()
                            val password: String = item.contrasena.toString()
                            val telefono : String = item.telefono.toString()

                            val editor = pref?.edit()

                            editor?.putInt("Id", id)
                            editor?.putString("Image", item.imagen)
                            editor?.putString("Nombre", nombre)
                            editor?.putString("Apellido", apellido)
                            editor?.putString("Email", email)
                            editor?.putString("Password", password)
                            editor?.putString("Telefono", telefono)
                            editor?.commit()

                            Toast.makeText(getContext(), "Bienvenido", Toast.LENGTH_SHORT).show()
                            val intent = Intent(
                                getActivity(),
                                NavBar::class.java
                            )
                            startActivity(intent)
//                            var navRegister = activity as FragmentNavigation
//                            navRegister.navigateFrag(NavBar,false)
                        }
                    }

                    override fun onFailure(call: Call<UsuarioModel>, t: Throwable) {
                        println(t.toString())
                    }
                })




            }


        }

        view.findViewById<Button>(R.id.btn_registrarse).setOnClickListener{
            var navRegister = activity as FragmentNavigation
            navRegister.navigateFrag(SignupFragment(),false)
        }
        return view;
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
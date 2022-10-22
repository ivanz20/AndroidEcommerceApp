package com.example.pia_moviles

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources

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
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var nombres : EditText
    private  lateinit var apellidos : EditText
    private  lateinit var correo : EditText
    private  lateinit var password : EditText
    private  lateinit var telefono : EditText


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

        view.findViewById<Button>(R.id.btn_registrarusuario).setOnClickListener{
            validateForm()
        }

        return view;
    }

    private fun validateForm() {
        val icon = AppCompatResources.getDrawable(requireContext(),
        com.google.android.material.R.drawable.mtrl_ic_error)

        icon?.setBounds(0,0,icon.intrinsicWidth,icon.intrinsicHeight)
       when{
           TextUtils.isEmpty(nombres.toString().trim())->{
               nombres.setError("Porfavor, ingresa tu nombre.",icon)
           }
           TextUtils.isEmpty(apellidos.toString().trim())->{
               apellidos.setError("Porfavor, ingresa tus apellidos.",icon)
           }
           TextUtils.isEmpty(correo.toString().trim())->{
               correo.setError("Porfavor, ingresa tu correo.",icon)
           }
           TextUtils.isEmpty(password.toString().trim())->{
               password.setError("Porfavor, ingresa tu contraseña.",icon)
           }
           TextUtils.isEmpty(telefono.toString().trim())->{
               telefono.setError("Porfavor, ingresa tu telefono.",icon)
           }
       }
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
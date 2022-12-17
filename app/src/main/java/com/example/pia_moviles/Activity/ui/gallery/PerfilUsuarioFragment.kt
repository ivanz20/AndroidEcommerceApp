package com.example.pia_moviles.Activity.ui.gallery

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pia_moviles.*
import okio.ByteString.Companion.decodeBase64


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
const val ARG_PARAM1 = "param1"
const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PerfilUsuarioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PerfilUsuarioFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var tvfoto : ImageView
    lateinit var tvname : TextView
    lateinit var tvEmail : TextView

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

        var view = inflater.inflate(com.example.pia_moviles.R.layout.fragment_perfil_usuario, container, false)

        tvfoto = view?.findViewById<ImageView>(R.id.fotoPerfil)!!
        tvname = view?.findViewById<TextView>(R.id.txtNombre)
        tvEmail = view?.findViewById<TextView>(R.id.textView3)
        loadData()

        view?.findViewById<Button>(com.example.pia_moviles.R.id.btnEditar)?.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace((requireView().parent as ViewGroup).id, EditarInformacionFragment()).commit()
        }

        view?.findViewById<Button>(R.id.btn_registrarprod)?.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace((requireView().parent as ViewGroup).id, RegistroProductosFragment()).commit()
        }

        view?.findViewById<Button>(R.id.btnMisProductos)?.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace((requireView().parent as ViewGroup).id, MisProductosFragment()).commit()

        }

        // Inflate the layout for this fragment
        return view
    }

    private fun loadData() {
        var pref: SharedPreferences? = null
        pref = getContext()?.getSharedPreferences("usuario", AppCompatActivity.MODE_PRIVATE)

        var img = pref?.getString("Image","")
        var nameuser = pref?.getString("Nombre","")
        var apellidouser = pref?.getString("Apellido","")
        var email = pref?.getString("Email","")
        var nombrecompleto = nameuser + " "  + apellidouser
        if(!img!!.isEmpty()) {
            val img2 = img.decodeBase64()?.toByteArray()
            val imageBitmap: Bitmap? = img2?.let { BitmapFactory.decodeByteArray(img2, 0, it.size) }
            tvfoto?.setImageBitmap(imageBitmap)
            tvname?.setText(nombrecompleto)
            tvEmail?.setText(email)
        }


    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PerfilUsuarioFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PerfilUsuarioFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
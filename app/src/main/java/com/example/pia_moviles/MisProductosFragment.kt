package com.example.pia_moviles

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pia_moviles.Adaptadores.MisProductosAdapter
import com.example.pia_moviles.Adaptadores.ProductAdapter
import com.example.pia_moviles.Modelos.ProductoModel
import com.example.pia_moviles.Servicios.ProductosServicio
import com.example.pia_moviles.Servicios.RestEngine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MisProductosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MisProductosFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var ProductList = mutableListOf<ProductoModel>()



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
        var view = inflater.inflate(R.layout.fragment_mis_productos, container, false)

        var recyclerView: RecyclerView = view.findViewById(R.id.rv_misproductos)
//
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context,
            2,
            GridLayoutManager.VERTICAL,
            false)


        var pref: SharedPreferences? = null
        pref = getContext()?.getSharedPreferences("usuario", AppCompatActivity.MODE_PRIVATE)

        var iduser = pref?.getInt("Id",0)

        val ServiciosProducto: ProductosServicio = RestEngine.getRestEngine().create(
            ProductosServicio::class.java)
        var result: Call<List<ProductoModel>> = ServiciosProducto.GetProductsByUser(iduser)

        result.enqueue(object : Callback<List<ProductoModel>> {
            override fun onResponse(call: Call<List<ProductoModel>>, response: Response<List<ProductoModel>>) {
                var resp = response.body()
                if(resp!= null){

                    for(product in resp){
                        ProductList.add(product)

                    }

                    val adapter = MisProductosAdapter(ProductList as ArrayList<ProductoModel>)
                    recyclerView.adapter = adapter

                }


            }

            override fun onFailure(call: Call<List<ProductoModel>>, t: Throwable) {
                println(t.toString())
            }

        })




        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MisProductosFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MisProductosFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
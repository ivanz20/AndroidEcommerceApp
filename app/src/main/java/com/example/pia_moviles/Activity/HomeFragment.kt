package com.example.pia_moviles.Activity

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pia_moviles.Adaptadores.ProductAdapter
import com.example.pia_moviles.Adaptadores.ProductAdapter2
import com.example.pia_moviles.Modelos.ProductoModel
import com.example.pia_moviles.R
import com.example.pia_moviles.Servicios.ProductosServicio
import com.example.pia_moviles.Servicios.RestEngine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
const val ARG_PARAM1 = "param1"
const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    var pref: SharedPreferences? = null
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var ProductList = mutableListOf<ProductoModel>()
    var ProductList2 = mutableListOf<ProductoModel>()


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
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        var recyclerView: RecyclerView = view.findViewById(R.id.recyclerview)
//
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context,
        1,
        GridLayoutManager.HORIZONTAL,
        false)

        //Servicio a DB

        val ServiciosProducto: ProductosServicio = RestEngine.getRestEngine().create(
            ProductosServicio::class.java)

        var result: Call<List<ProductoModel>> = ServiciosProducto.GetAllById()

        result.enqueue(object : Callback<List<ProductoModel>> {
            override fun onResponse(call: Call<List<ProductoModel>>, response: Response<List<ProductoModel>>) {
                var resp = response.body()
                if(resp!= null){

                    for(product in resp){
                        ProductList.add(product)

                    }

                    val adapter = ProductAdapter(ProductList as ArrayList<ProductoModel>)
                    recyclerView.adapter = adapter

                }


            }

            override fun onFailure(call: Call<List<ProductoModel>>, t: Throwable) {
                println(t.toString())
            }


        })







        var recyclerViewComprados: RecyclerView = view.findViewById(R.id.recyclerviewcomprados)
        recyclerViewComprados.setHasFixedSize(true)
        recyclerViewComprados.layoutManager = GridLayoutManager(context,
            1,
            GridLayoutManager.HORIZONTAL,
            false)


        var result2: Call<List<ProductoModel>> = ServiciosProducto.GetAllByRate()

        result2.enqueue(object : Callback<List<ProductoModel>> {
            override fun onResponse(call: Call<List<ProductoModel>>, response: Response<List<ProductoModel>>) {
                var resp = response.body()
                if(resp!= null){

                    for(product in resp){
                        ProductList2.add(product)

                    }

                    val adapter2 = ProductAdapter2(ProductList2 as ArrayList<ProductoModel>)
                    recyclerViewComprados.adapter = adapter2

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
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
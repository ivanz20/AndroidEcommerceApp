package com.example.pia_moviles.Activity.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pia_moviles.Adaptadores.ProductoBusquedaAdaptador
import com.example.pia_moviles.Modelos.ProductoModel
import com.example.pia_moviles.R
import com.example.pia_moviles.Servicios.ProductosServicio
import com.example.pia_moviles.Servicios.RestEngine
import com.example.pia_moviles.databinding.FragmentSlideshowBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    var ProductList = mutableListOf<ProductoModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view = inflater.inflate(com.example.pia_moviles.R.layout.fragment_slideshow, container, false)

        var recyclerView: RecyclerView = view.findViewById(R.id.rv_busqueda)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context,
            2,
            GridLayoutManager.VERTICAL,
            false)


        var nombreabuscar: EditText = view.findViewById(R.id.search_text)

        view.findViewById<Button>(R.id.btn_buscar).setOnClickListener{

            ProductList.clear()

            val ServiciosProducto: ProductosServicio = RestEngine.getRestEngine().create(
                ProductosServicio::class.java)

            var abuscar = nombreabuscar.text.toString()

            var result: Call<List<ProductoModel>> = ServiciosProducto.SearchByName(abuscar)

            result.enqueue(object : Callback<List<ProductoModel>> {
                override fun onResponse(call: Call<List<ProductoModel>>, response: Response<List<ProductoModel>>) {
                    var resp = response.body()
                    if(resp!= null){

                        for(product in resp){
                            ProductList.add(product)

                        }

                        val adapter = ProductoBusquedaAdaptador(ProductList as ArrayList<ProductoModel>)
                        recyclerView.adapter = adapter

                    }


                }

                override fun onFailure(call: Call<List<ProductoModel>>, t: Throwable) {
                    println(t.toString())
                }


            })
        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
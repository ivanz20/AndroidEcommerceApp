package com.example.pia_moviles.Activity.ui.home

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pia_moviles.Activity.ARG_PARAM1
import com.example.pia_moviles.Activity.ARG_PARAM2
import com.example.pia_moviles.Activity.HomeFragment
import com.example.pia_moviles.Adaptadores.ProductAdapter
import com.example.pia_moviles.R
import com.example.pia_moviles.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

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

        val adapter = ProductAdapter()
        recyclerView.adapter = adapter

        var recyclerViewComprados: RecyclerView = view.findViewById(R.id.recyclerviewcomprados)
        recyclerViewComprados.setHasFixedSize(true)
        recyclerViewComprados.layoutManager = GridLayoutManager(context,
            1,
            GridLayoutManager.HORIZONTAL,
            false)

        val adapter2 = ProductAdapter()
        recyclerViewComprados.adapter = adapter2

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
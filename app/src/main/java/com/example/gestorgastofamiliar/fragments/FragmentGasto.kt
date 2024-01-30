package com.example.gestorgastofamiliar.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestorgastofamiliar.R
import com.example.gestorgastofamiliar.adapter.RecyclerViewAdapterGasto
import com.example.gestorgastofamiliar.entities.Gasto
import com.example.gestorgastofamiliar.services.DataBase

class FragmentGasto : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista_gasto, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                val dataBase: DataBase = DataBase.getDataBase(context)
                var  listaGastos: ArrayList<Gasto> = ArrayList()
                Thread {
                    listaGastos = dataBase.gastoDao().getGastos() as ArrayList<Gasto>
                    adapter = RecyclerViewAdapterGasto(listaGastos)
                }.start()


            }
        }
        return view
    }
}

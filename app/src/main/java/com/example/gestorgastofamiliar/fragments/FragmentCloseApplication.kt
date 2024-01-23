package com.example.gestorgastofamiliar.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.example.gestorgastofamiliar.LoginActivity
import com.example.gestorgastofamiliar.MainActivity
import com.example.gestorgastofamiliar.R

class FragmentCloseApplication : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_lista_gasto, container, false)
//        val intent = Intent(container?.context, LoginActivity::class.java)
//        startActivity(intent)
        activity?.finishAffinity()
        return view
    }
}
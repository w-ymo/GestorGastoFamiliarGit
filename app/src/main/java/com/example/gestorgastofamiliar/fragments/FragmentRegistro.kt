package com.example.gestorgastofamiliar.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.gestorgastofamiliar.R
import com.example.gestorgastofamiliar.databinding.FragmentRegistroBinding
import com.example.gestorgastofamiliar.providers.GastosProvider
import com.example.gestorgastofamiliar.providers.UsuariosProvider
import java.util.Calendar

class FragmentRegistro : Fragment() {

    private lateinit var binding: FragmentRegistroBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegistroBinding.inflate(layoutInflater, container, false)

        binding.tietFecha.setOnClickListener {
            showDatePicker()
        }

        var categories = arrayListOf("Comida", "Estudios", "Ocio")
        binding.spCategoria.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, categories)
        binding.spUsuario.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, UsuariosProvider.usuarios)

        return binding.root
    }

    private fun showDatePicker() {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(), { datePicker: DatePicker, year: Int, month: Int, day: Int ->
                val selectedDate = "$day/${month + 1}/$year"
                binding.tietFecha.setText(selectedDate)
            }, year, month, day
        )

        datePickerDialog.show()
    }


}
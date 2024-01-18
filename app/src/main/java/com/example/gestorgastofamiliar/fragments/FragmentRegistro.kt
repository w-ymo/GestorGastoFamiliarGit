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
import com.example.gestorgastofamiliar.providers.Gasto
import com.example.gestorgastofamiliar.providers.UsuariosProvider
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class FragmentRegistro : Fragment() {

    private lateinit var binding: FragmentRegistroBinding
    private lateinit var tietConcept: TextInputEditText
    private lateinit var tietDate: TextInputEditText
    private lateinit var tietPrice: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegistroBinding.inflate(layoutInflater, container, false)

        tietConcept = binding.tietConcepto
        tietDate = binding.tietFecha
        tietPrice = binding.tietPrecio

        var categories = arrayListOf("Comida", "Estudios", "Ocio")
        binding.spCategoria.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, categories)
        binding.spUsuario.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            UsuariosProvider.usuarios
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tietDate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.bRegistrar.setOnClickListener {

            // Concepto
            val concept = tietConcept.text.toString().also {
                showEditTextError(tietConcept)
            }

            // Fecha
            val date = tietDate.text.toString().run {
                if (!isNullOrBlank()) {
                    SimpleDateFormat("dd/MM/yyyy").parse(this)
                } else {
                    Date()
                }
            }.also {
                showEditTextError(tietDate)
            }

            // Precio
            val initialPrice = tietPrice.text.toString()
            val formattedPrice = if (initialPrice.isNotBlank()) {
                String.format("%.2f", initialPrice.toDoubleOrNull() ?: 0.00)
            } else {
                "0.00"
            }.also {
                showEditTextError(tietPrice)
                tietPrice.setText(it)
            }

            val price = formattedPrice.toDouble()

            if (concept.isNotBlank() && date != Date() && price != null) {
                val gasto = Gasto(
                    binding.spCategoria.selectedItem.toString(),
                    concept,
                    date,
                    price,
                    binding.spUsuario.selectedItem.toString()
                )

                Bundle().apply {
                    putSerializable("gasto", gasto)
                }
            }

        }
    }

    private fun showEditTextError(editText: EditText) {
        if (editText.text.isNullOrBlank()) {
            editText.setBackgroundColor(resources.getColor(R.color.error, resources.newTheme()))
        }
    }

    private fun showDatePickerDialog() {
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
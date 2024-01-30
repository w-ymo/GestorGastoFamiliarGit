package com.example.gestorgastofamiliar.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.gestorgastofamiliar.LoginActivity
import com.example.gestorgastofamiliar.R
import com.example.gestorgastofamiliar.databinding.FragmentRegistroBinding
import com.example.gestorgastofamiliar.entities.CategoriasProvider
import com.example.gestorgastofamiliar.entities.Gasto
import com.example.gestorgastofamiliar.entities.GastosProvider
import com.example.gestorgastofamiliar.entities.Usuario
import com.example.gestorgastofamiliar.entities.UsuariosProvider
import com.example.gestorgastofamiliar.services.DataBase
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class FragmentRegistro : Fragment() {

    private lateinit var binding: FragmentRegistroBinding
    private lateinit var tietConcept: TextInputEditText
    private lateinit var tietDate: TextInputEditText
    private lateinit var tietPrice: TextInputEditText
    private var userId: Int? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegistroBinding.inflate(layoutInflater, container, false)

        tietConcept = binding.tietConcepto
        tietDate = binding.tietFecha
        tietPrice = binding.tietPrecio

        val database = DataBase.getDataBase(requireContext())
        userId = arguments?.getInt("idUser")
        if (userId != null) {
            binding.tvNombreUsuario.text = database.usuarioDao().getBydId(userId!!).nombre
        }


        binding.spCategoria.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            CategoriasProvider.categorias
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tietDate.setOnClickListener {
            showDatePickerDialog()
        }

        val categoriesInputEditText = EditText(requireContext())
        binding.ibCategoria.setOnClickListener {
            val alert = AlertDialog.Builder(requireContext())
                .setTitle("Añadir Categoría")
                .setMessage("Escribe el nombre de la nueva categoría")
                .setView(categoriesInputEditText)
                .setPositiveButton("Ok") { _, _ ->
                    CategoriasProvider.categorias.add(categoriesInputEditText.text.toString())
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.dismiss()
                }
            alert.show()
        }

        binding.bRegistrar.setOnClickListener {
            val concept = tietConcept.text.toString().also {
                showEditTextError(tietConcept)
            }

            val date = formatDate(tietDate.text.toString())

            val price = formatPrice(tietPrice.text.toString())

            if (concept.isNotBlank() && date != Date() && price != null && userId != null) {
                val gasto = Gasto(
                    binding.spCategoria.selectedItem.toString(),
                    concept,
                    date,
                    price,
                    userId!!
                )

                GastosProvider.gastos.add(gasto)
                it.findNavController().navigate(R.id.action_registro_to_lista)
            }

        }
    }

    private fun formatPrice(price: String): Double {
        val formattedPrice = if (price.isNotBlank()) {
            String.format("%.2f", price.toDoubleOrNull() ?: 0.00).replace(",", ".")
        } else {
            "0.00"
        }.also {
            showEditTextError(tietPrice)
            tietPrice.setText(it)
        }
        return formattedPrice.toDouble()
    }

    private fun formatDate(date: String): Date {
        val formattedDate = date.run {
            if (!isNullOrBlank()) {
                SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(this)
            } else {
                Date()
            }
        }.also {
            showEditTextError(tietDate)
        }
        return formattedDate
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
            requireContext(), { _: DatePicker, year: Int, month: Int, day: Int ->
                val selectedDate = "$day/${month + 1}/$year"
                binding.tietFecha.setText(selectedDate)
            }, year, month, day
        )
        datePickerDialog.show()
    }
}
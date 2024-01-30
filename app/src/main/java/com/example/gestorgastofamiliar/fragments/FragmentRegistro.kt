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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.gestorgastofamiliar.R
import com.example.gestorgastofamiliar.databinding.FragmentRegistroBinding
import com.example.gestorgastofamiliar.entities.Categoria
import com.example.gestorgastofamiliar.entities.Gasto
import com.example.gestorgastofamiliar.entities.UsuariosProvider
import com.example.gestorgastofamiliar.services.DataBase
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    private lateinit var dataBase: DataBase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegistroBinding.inflate(layoutInflater, container, false)

        dataBase = DataBase.getDataBase(requireContext())

        tietConcept = binding.tietConcepto
        tietDate = binding.tietFecha
        tietPrice = binding.tietPrecio

//        userId = arguments?.getInt("idUser")
//        if (userId != null) {
//            lifecycleScope.launch(Dispatchers.IO) {
//                val nombre = dataBase.usuarioDao().getBydId(userId!!).nombre
//                withContext(Dispatchers.Main) {
//                    binding.tvNombreUsuario.text = nombre
//                }
//            }
//        }


        binding.spUsuarios.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            UsuariosProvider.usuarios
        )

        Thread {
            binding.spCategoria.adapter = dataBase.categoriaDao().getAll()?.let {
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    it.toTypedArray()
                )
            }
        }.start()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tietDate.setOnClickListener {
            showDatePickerDialog()
        }


        binding.ibCategoria.setOnClickListener {
            val categoriesInputEditText = EditText(requireContext())
            val alert = AlertDialog.Builder(requireContext())
                .setTitle("Añadir Categoría")
                .setMessage("Escribe el nombre de la nueva categoría")
                .setView(categoriesInputEditText)
                .setPositiveButton("Ok") { dialog, _ ->
                    lifecycleScope.launch(Dispatchers.IO) {
                        dataBase.categoriaDao()
                            .insert(Categoria(categoriesInputEditText.text.toString()))
                        val categorias = dataBase.categoriaDao().getAll()
                        withContext(Dispatchers.Main) {
                            binding.spCategoria.adapter = categorias.let {
                                it?.let { it1 ->
                                    ArrayAdapter(
                                        requireContext(),
                                        android.R.layout.simple_list_item_1,
                                        it1.toTypedArray()
                                    )
                                }
                            }
                        }
                    }
                    dialog.dismiss()
                }.setNegativeButton("Cancelar") { dialog, _ ->
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

            if (concept.isNotBlank() && date != Date() && price != null) {
                lifecycleScope.launch(Dispatchers.IO) {
                    dataBase.gastoDao().insert(
                        Gasto(
                            dataBase.categoriaDao()
                                .getCategoryById(binding.spCategoria.selectedItemPosition + 1).nombre,
                            concept,
                            date,
                            price,
                            dataBase.usuarioDao()
                                .getBydId(binding.spUsuarios.selectedItemPosition + 1).idUsuario
                        )
                    )
                }

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
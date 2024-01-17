package com.example.gestorgastofamiliar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gestorgastofamiliar.databinding.FragmentGastoBinding
import com.example.gestorgastofamiliar.providers.Gasto
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

class RecyclerViewAdapterGasto(
    private val gastos: List<Gasto>
) : RecyclerView.Adapter<RecyclerViewAdapterGasto.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentGastoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(gastos[position])
    }

    override fun getItemCount(): Int = gastos.size

    inner class ViewHolder(val binding: FragmentGastoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gasto: Gasto) {
            binding.gasto = gasto
        }
    }

}

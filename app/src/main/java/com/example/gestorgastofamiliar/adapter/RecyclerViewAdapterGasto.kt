
package com.example.gestorgastofamiliar.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gestorgastofamiliar.databinding.FragmentGastoBinding
import com.example.gestorgastofamiliar.providers.Gasto

class RecyclerViewAdapterGasto(
    private val gastos: ArrayList<Gasto>
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
        holder.binding.bEliminar.setOnClickListener {
            var builder = AlertDialog.Builder(holder.itemView.context)
            builder.setTitle("Confirmación")
            builder.setMessage("¿Seguro que desea borrar la entrada?")
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                gastos.removeAt(position)
                this.notifyItemRemoved(position)
            }
            builder.setNegativeButton("Cancelar") { dialog, _ ->
            }
            builder.show()
        }
    }

    override fun getItemCount(): Int = gastos.size

    inner class ViewHolder(val binding: FragmentGastoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gasto: Gasto) {
            binding.gasto = gasto

        }
    }

}

package com.example.gestorgastofamiliar.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gestorgastofamiliar.R
import com.example.gestorgastofamiliar.databinding.FragmentGastoBinding
import com.example.gestorgastofamiliar.providers.Gasto
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
        holder.bind(gastos[position], holder.itemView.getContext())
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
        fun bind(gasto: Gasto, context: Context) {
            binding.tvCategoria.text = context.getString(R.string.separador_categoria, gasto.categoria)
            binding.tvNombreUsuario.text = context.getString(R.string.separador_categoria, gasto.idUsuario)
            binding.tvConcepto.text =  gasto.concepto
            binding.tvPrecio.text = context.getString(R.string.simbolo_euro, gasto.precio)
            binding.tvFecha.text = SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH).format(gasto.fecha)
        }
    }

}

package com.example.gestorgastofamiliar.adapter

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gestorgastofamiliar.R
import com.example.gestorgastofamiliar.databinding.FragmentGastoBinding
import com.example.gestorgastofamiliar.entities.Gasto
import com.example.gestorgastofamiliar.services.DataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class RecyclerViewAdapterGasto(
    private val gastos: ArrayList<Gasto>
) : RecyclerView.Adapter<RecyclerViewAdapterGasto.ViewHolder>() {
    lateinit var dataBase: DataBase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        dataBase = DataBase.getDataBase(parent.context)
        return ViewHolder(
            FragmentGastoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gasto = gastos[position]
        holder.bind(gasto, holder.itemView.getContext())
        holder.binding.bEliminar.setOnClickListener {
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setTitle("Confirmación")
            builder.setMessage("¿Seguro que desea borrar la entrada?")
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    dataBase.gastoDao().deleteById(gasto.idGasto)
                    Log.d("dev", gastos[position].idGasto.toString())
                    withContext(Dispatchers.Main) {
                        gastos.removeAt(position)
                    }
                }
                //this.notifyItemRemoved(position)
                this.notifyDataSetChanged()
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

            binding.tvCategoria.text =
                context.getString(R.string.separador_categoria, gasto.categoria)
            CoroutineScope(Dispatchers.IO).launch {
                val nombre: String = dataBase.usuarioDao().getBydId(gasto.idUsuario).nombre
                withContext(Dispatchers.Main) {
                    binding.tvNombreUsuario.text =
                        context.getString(R.string.separador_categoria, nombre)
                }

            }
            binding.tvConcepto.text = gasto.concepto
            binding.tvPrecio.text = context.getString(R.string.simbolo_euro, gasto.precio)
            binding.tvFecha.text =
                SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH).format(gasto.fecha)

        }
    }

}

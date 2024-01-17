package com.example.gestorgastofamiliar.providers

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class Gasto(
    var categoria: String,
    var concepto: String,
    var fecha: Date,
    var precio: Double,
    var idUsuario: String,
    val formatoFecha: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
) {
    companion object {
        fun dateToString(date: Date): String {
            return SimpleDateFormat("dd/mm/yyyy").format(date)
        }
    }
}

class GastosProvider {
    companion object {
        val gastos: ArrayList<Gasto> = arrayListOf(
            Gasto("Comida", "Pizza", Date(), 100.0, "juan"),
            Gasto("Comida", "Hamburguesa", Date(), 10.0, "juan"),
            Gasto("Comida", "Tacos", Date(), 30.0, "juan"),
            Gasto("Estudios", "Libro", Date(), 200.0, "juan"),
            Gasto("Ocio", "Cine", Date(), 7.0, "juan"),
            Gasto("Ocio", "Cine", Date(), 7.0, "pedro"),
            Gasto("Comida", "Pizza", Date(), 100.0, "pedro"),
            Gasto("Comida", "Hamburguesa", Date(), 20.0, "pedro")
        )
    }
}
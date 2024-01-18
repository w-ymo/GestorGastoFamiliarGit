package com.example.gestorgastofamiliar.providers

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.util.Date

data class Gasto(
    var categoria: String,
    var concepto: String,
    var fecha: Date,
    var precio: Double,
    var idUsuario: String?
) : Serializable

class GastosProvider {
    companion object {
        val gastos: ArrayList<Gasto> = arrayListOf(
            Gasto("Comida", "Pizza", Date(), 100.0, "juan"),
            Gasto("Comida", "Hamburguesa", Date(), 10.0, "juan"),
            Gasto("Comida", "Tacos", Date(), 30.0, "juan"),
            Gasto("Estudios", "Libro", Date(), 200.0, "juan"),
            Gasto("Ocio", "Cine", Date(), 7.0, "juan"),
            Gasto("Ocio", "Cine", Date(), 7.0, "pedro"),
            Gasto("Comida", "Pizza", Date(), 100.00, "pedro"),
            Gasto("Comida", "Hamburguesa", Date(), 20.00, "pedro")
        )
    }
}
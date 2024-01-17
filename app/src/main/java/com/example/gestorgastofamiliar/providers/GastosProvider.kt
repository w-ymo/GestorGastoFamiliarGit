package com.example.gastosfamiliares

import java.util.Date

data class Gasto(var categoria: String, var conceto: String, var fecha: Date, var precio: Int, var idUsuario: String)

class GastosProvider {
    companion object {
        val gastos: ArrayList<Gasto> = arrayListOf(
            Gasto("Comida", "Pizza", Date(), 100, "juan"),
            Gasto("Comida", "Hamburguesa", Date(), 10, "juan"),
            Gasto("Comida", "Tacos", Date(), 30, "juan"),
            Gasto("Estudios", "Libro", Date(), 200, "juan"),
            Gasto("Ocio", "Cine", Date(), 7, "juan"),
            Gasto("Ocio", "Cine", Date(), 7, "pedro"),
            Gasto("Comida", "Pizza", Date(), 100, "pedro"),
            Gasto("Comida", "Hamburguesa", Date(), 20, "pedro")
        )
    }
}
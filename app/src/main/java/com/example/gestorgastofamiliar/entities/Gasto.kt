package com.example.gestorgastofamiliar.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "gasto", foreignKeys = [ForeignKey(entity = Usuario::class,
    parentColumns = ["idUsuario"],
    childColumns = ["idUsuario"],
    onDelete = ForeignKey.CASCADE)]
)
data class Gasto(
    @ColumnInfo var categoria: String,
    @ColumnInfo var concepto: String,
    @ColumnInfo var fecha: Date,
    @ColumnInfo var precio: Double,
    @ColumnInfo var idUsuario: Int
) {
    @PrimaryKey(autoGenerate = true)
    var idGasto: Int = 0
}

class GastosProvider {
    companion object {
        val gastos: ArrayList<Gasto> = arrayListOf(
            Gasto("Comida", "Pizza", Date(), 100.0, 1),
            Gasto("Comida", "Hamburguesa", Date(), 10.0, 1),
            Gasto("Comida", "Tacos", Date(), 30.0, 1),
            Gasto("Estudios", "Libro", Date(), 200.0, 1),
            Gasto("Ocio", "Cine", Date(), 7.0, 1),
            Gasto("Ocio", "Cine", Date(), 7.0, 2),
            Gasto("Comida", "Pizza", Date(), 100.0, 2),
            Gasto("Comida", "Hamburguesa", Date(), 20.0, 2)
        )
    }
}
package com.example.gestorgastofamiliar.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categoria")
data class Categoria(@ColumnInfo var nombre: String) {
    @PrimaryKey(autoGenerate = true)
    var idCategoria: Int = 0

    override fun toString(): String {
        return nombre
    }
}

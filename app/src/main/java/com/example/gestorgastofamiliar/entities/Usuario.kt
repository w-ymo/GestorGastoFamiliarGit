package com.example.gestorgastofamiliar.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class Usuario(
    @ColumnInfo var nombre: String,
    @ColumnInfo var contrasena: String) {
    @PrimaryKey(autoGenerate = true)
    var idUsuario: Int = 0
    override fun toString(): String {
        return nombre
    }
}

class UsuariosProvider {
    companion object {
        val usuarios: ArrayList<Usuario> = arrayListOf(
            Usuario("juan", "hola_mundo"),
            Usuario("pedro", "hola_mundo2"),
            Usuario("maria", "hola_mundo3")
        )
    }
}
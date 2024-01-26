package com.example.gestorgastofamiliar.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.gestorgastofamiliar.entities.Usuario


@Dao
interface UsuarioDAO {
    @Query("select * from usuario")
    fun getAll(): List<Usuario>?

    @Query("select * from usuario where idUsuario = (:idUsuario)")
    fun getBydId(vararg idUsuario: Int): Usuario

    @Query("select * from usuario where nombre = (:nombreUsuario)")
    fun getByName(vararg nombreUsuario:String):Usuario

    @Insert
    fun insert(vararg usuario: Usuario)

}
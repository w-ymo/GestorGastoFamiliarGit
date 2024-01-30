package com.example.gestorgastofamiliar.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.gestorgastofamiliar.entities.Usuario


@Dao
interface UsuarioDAO {
    @Query("select * from usuario")
    suspend fun getAll(): List<Usuario>?

    @Query("select * from usuario where idUsuario = (:idUsuario)")
    suspend fun getBydId(vararg idUsuario: Int): Usuario

    @Query("select * from usuario where nombre == (:nombreUsuario)")
    suspend fun getByName(vararg nombreUsuario:String):Usuario

    @Insert
    suspend fun insert(vararg usuario: Usuario)

}
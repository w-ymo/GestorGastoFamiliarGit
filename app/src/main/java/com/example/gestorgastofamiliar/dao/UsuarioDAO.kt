package com.example.gestorgastofamiliar.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.gestorgastofamiliar.entities.Usuario

@Dao
interface UsuarioDAO {
    @Query("select * from usuario")
    fun getAll(): ArrayList<Usuario>

    @Insert
    fun insert(vararg usuario: Usuario)

}
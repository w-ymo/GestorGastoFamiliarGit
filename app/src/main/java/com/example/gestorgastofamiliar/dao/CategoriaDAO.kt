package com.example.gestorgastofamiliar.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.gestorgastofamiliar.entities.Categoria

@Dao
interface CategoriaDAO {
    @Query("select * from categoria")
    suspend fun getAll(): List<Categoria>?

    @Insert
    suspend fun insert(vararg categoria: Categoria)

}
package com.example.gestorgastofamiliar.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.gestorgastofamiliar.entities.Categoria
import com.example.gestorgastofamiliar.entities.Gasto

@Dao
interface CategoriaDAO {
    @Query("select * from categoria")
    fun getAll(): List<Categoria>?

    @Query("select * from categoria where idCategoria = (:categoryId)")
    fun getCategoryById(vararg categoryId: Int): Categoria

    @Insert
    fun insert(vararg categoria: Categoria)

}
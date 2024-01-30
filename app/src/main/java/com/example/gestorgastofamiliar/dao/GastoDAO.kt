package com.example.gestorgastofamiliar.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.gestorgastofamiliar.entities.Gasto

@Dao
interface GastoDAO {
    @Query("select * from gasto where idUsuario = (:userId)")
    fun getGastosByUserId(vararg userId: Int): List<Gasto>?

    @Query("select * from gasto")
    fun getGastos(): List<Gasto>?

    @Insert
    fun insert(vararg gasto: Gasto)

    @Query("delete from gasto where idGasto = (:gastoId)")
    fun deleteById(vararg gastoId: Int)
}
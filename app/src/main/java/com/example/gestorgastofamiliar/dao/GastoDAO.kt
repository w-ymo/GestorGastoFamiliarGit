package com.example.gestorgastofamiliar.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.gestorgastofamiliar.entities.Gasto

@Dao
interface GastoDAO {
    @Query("select * from gasto where idUsuario = (:userId)")
    fun getGastoByUserId(vararg userId: Int): Gasto

    @Insert
    fun insert(vararg gasto: Gasto)

    @Delete
    fun delete(vararg gasto: Gasto)
}
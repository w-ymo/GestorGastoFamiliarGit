package com.example.gestorgastofamiliar.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.gestorgastofamiliar.entities.Gasto

@Dao
interface GastoDAO {
    @Query("select * from gasto where idUsuario = (:userId)")
    suspend fun getGastoByUserId(vararg userId: Int): Gasto

    @Insert
    suspend fun insert(vararg gasto: Gasto)

    @Delete
    suspend fun delete(vararg gasto: Gasto)
}
package com.example.gestorgastofamiliar.services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gestorgastofamiliar.dao.CategoriaDAO
import com.example.gestorgastofamiliar.dao.GastoDAO
import com.example.gestorgastofamiliar.dao.UsuarioDAO
import com.example.gestorgastofamiliar.entities.Categoria
import com.example.gestorgastofamiliar.entities.Gasto
import com.example.gestorgastofamiliar.entities.Usuario

@Database(entities = [Usuario::class, Gasto::class, Categoria::class], version = 1)
@TypeConverters(Converters::class)
abstract class DataBase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDAO
    abstract fun gastoDao(): GastoDAO
    abstract fun categoriaDao(): CategoriaDAO

    companion object {
        private var instance: DataBase? = null
        fun getDataBase(context: Context): DataBase {
            return instance?: synchronized(this) {
                Room.databaseBuilder(context ,DataBase::class.java,"GestorGastoFamiliarDB").build()
                    .also {
                    instance = it
                }
            }
        }
    }
}
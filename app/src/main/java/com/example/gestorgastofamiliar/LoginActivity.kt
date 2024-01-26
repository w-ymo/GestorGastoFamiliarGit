package com.example.gestorgastofamiliar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gestorgastofamiliar.databinding.ActivityLoginBinding
import com.example.gestorgastofamiliar.entities.Categoria
import com.example.gestorgastofamiliar.entities.CategoriasProvider
import com.example.gestorgastofamiliar.entities.Gasto
import com.example.gestorgastofamiliar.entities.GastosProvider
import com.example.gestorgastofamiliar.entities.Usuario
import com.example.gestorgastofamiliar.entities.UsuariosProvider
import com.example.gestorgastofamiliar.services.DataBase
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database: DataBase = DataBase.getDataBase(this)

        Thread {
            if (database.usuarioDao().getAll().isNullOrEmpty()) {

                for (i in 0..<CategoriasProvider.categorias.size) {
                    database.categoriaDao().insert(Categoria(CategoriasProvider.categorias[i]))
                }

                for (user: Usuario in UsuariosProvider.usuarios) {
                    database.usuarioDao().insert(user)
                }

                for (user: Gasto in GastosProvider.gastos) {
                    database.gastoDao().insert(user)
                }

            }
        }.start()
        val pref = getSharedPreferences("datos", MODE_PRIVATE)

        if (pref.getString("user", "") != null) binding.tilUsuario.editText?.setText(
            pref.getString(
                "user",
                ""
            )
        )

        //binding.tilPassword.setEndIconActivated(true)
        binding.bEntrar.setOnClickListener {
            //Comprobacion
            val nombre = binding.tilUsuario.editText?.text.toString()
            val password = binding.tilPassword.editText?.text.toString()
            val index = findUser(nombre)
            var msgError = ""
            if (index >= 0) {
                //comprobamos la contraseña
                if (UsuariosProvider.usuarios[index].contrasena == password) {
                    //lanzo a la siguiente ventana
                    var dato = ""
                    if (binding.cbGuardarDatos.isChecked) {
                        dato = nombre
                    }
                    val editor = pref.edit().apply {
                        putString("user", dato)
                    }.commit()
                    val intent = Intent(this, MainActivity::class.java).apply {
                        putExtra("nombre", nombre)
                    }
                    startActivity(intent)
                    finishAffinity()
                } else {
                    //mensaje de error
                    msgError = "La contraseña no es correcta."
                }
            } else {
                //mensaje de error
                msgError = "El usuario no existe en la base de datos."
            }
            Snackbar.make(binding.root, msgError, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun findUser(nombre: String): Int {
        for (i in 0..UsuariosProvider.usuarios.size) {
            if (UsuariosProvider.usuarios[i].nombre == nombre) {
                return i
            }
        }
        return -1
    }
}
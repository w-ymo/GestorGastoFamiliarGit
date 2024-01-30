package com.example.gestorgastofamiliar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestorgastofamiliar.databinding.ActivityLoginBinding
import com.example.gestorgastofamiliar.entities.Categoria
import com.example.gestorgastofamiliar.entities.CategoriasProvider
import com.example.gestorgastofamiliar.entities.Gasto
import com.example.gestorgastofamiliar.entities.GastosProvider
import com.example.gestorgastofamiliar.entities.Usuario
import com.example.gestorgastofamiliar.entities.UsuariosProvider
import com.example.gestorgastofamiliar.services.DataBase
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database: DataBase = DataBase.getDataBase(this)
        /*
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

        */

        val pref = getSharedPreferences("datos", MODE_PRIVATE)
        if (pref.getString("user", "") != null) binding.tilUsuario.editText?.setText(
            pref.getString(
                "user",
                ""
            )
        )
        binding.bEntrar.setOnClickListener {
            //Comprobacion
            val nombre = binding.tilUsuario.editText?.text.toString()
            val password = binding.tilPassword.editText?.text.toString()
            var msgError = ""
            var user:Usuario? = null
            var context = this
            lifecycleScope.launch(Dispatchers.IO) {
                Log.d("depurando", nombre)
                user = database.usuarioDao().getByName(nombre)
                Log.d("depurando", user.toString()) //este va fino

                Log.d(
                    "depurando",
                    user.toString()
                ) //este no porque no pilla bien el thread anterior
                Log.d("depurando", (user != null).toString())
                withContext(Dispatchers.Main) {
                    if (user != null) {
                        //comprobamos la contraseña
                        if (user?.contrasena == password) {
                            //lanzo a la siguiente ventana
                            var dato = ""
                            if (binding.cbGuardarDatos.isChecked) {
                                dato = nombre
                            }
                            val editor = pref.edit().apply {
                                putString("user", dato)
                            }.commit()
                            val intent = Intent(context, MainActivity::class.java).apply {
                                putExtra("idUser", user?.idUsuario)
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
        }
    }

}
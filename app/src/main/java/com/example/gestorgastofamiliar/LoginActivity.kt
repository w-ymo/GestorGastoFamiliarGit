package com.example.gestorgastofamiliar

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gestorgastofamiliar.databinding.ActivityLoginBinding
import com.example.gestorgastofamiliar.providers.UsuariosProvider
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                    val editor = pref.edit().apply{
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
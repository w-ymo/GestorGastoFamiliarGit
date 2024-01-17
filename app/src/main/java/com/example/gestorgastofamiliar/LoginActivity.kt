package com.example.gestorgastofamiliar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gestorgastofamiliar.databinding.ActivityLoginBinding
import com.example.gestorgastofamiliar.providers.UsuariosProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding.tilPassword.setEndIconActivated(true)
        binding.bEntrar.setOnClickListener{
            //Comprobacion
            val nombre = binding.tilUsuario.editText?.text.toString()
            val password = binding.tilPassword.editText?.text.toString()
            val index = findUser(nombre)
            var msgError = ""
            if(index >= 0){
                //comprobamos la contraseña
                if (UsuariosProvider.usuarios[index].contrasena == password){
                    //lanzo a la siguiente ventana
                    //meter un checkbox que te guarde el usuario al iniciar de nuevo la app??
                    startActivity(MainActivity().intent)
                }else{
                    //mensaje de error
                    msgError = "La contraseña no es correcta."
                }
            }else{
                //mensaje de error
                msgError = "El usuario no existe en la base de datos."
            }
            binding.tvError.text = msgError

        }

    }

    private fun findUser(nombre: String): Int {
        for (i in 0..UsuariosProvider.usuarios.size){
            if(UsuariosProvider.usuarios[i].nombre == nombre){
                return i
            }
        }
        return -1
    }
}
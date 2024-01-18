package com.example.gestorgastofamiliar.providers

data class Usuario(var nombre: String, var contrasena: String) {
    override fun toString(): String {
        return nombre
    }
}

class UsuariosProvider {
    companion object {
        val usuarios: ArrayList<Usuario> = arrayListOf(
            Usuario("juan", "hola_mundo"),
            Usuario("pedro", "hola_mundo2"),
            Usuario("maria", "hola_mundo3")
        )
    }
}
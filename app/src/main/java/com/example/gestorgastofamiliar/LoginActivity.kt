package com.example.gestorgastofamiliar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gestorgastofamiliar.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding.tilPassword.setEndIconActivated(true)
        binding.bEntrar.setOnClickListener{
            //lanzar al main activity
        }

    }
}
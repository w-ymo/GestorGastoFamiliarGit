package com.example.gestorgastofamiliar

import android.os.Bundle
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.FragmentResultOwner
import androidx.fragment.app.setFragmentResultListener
import com.example.gestorgastofamiliar.databinding.ActivityMainBinding
import com.example.gestorgastofamiliar.fragments.FragmentGasto
import com.example.gestorgastofamiliar.services.DataBase

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database: DataBase = DataBase.getDataBase(this)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        var tvUserName = navView.getHeaderView(0).findViewById<TextView>(R.id.tv_nombre_usuario)
        var idUser: Int = intent.getIntExtra("idUser", 0)
        Thread {
            tvUserName.text = database.usuarioDao().getBydId(idUser).nombre
        }.start()


        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragment_lista, R.id.fragment_registro, R.id.fragment_close_application
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
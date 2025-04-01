package com.cinergia.psicointegral.Bienvenida

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cinergia.psicointegral.R
import com.cinergia.psicointegral.Registro.Registro

class MainActivitybienvenida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_activitybienvenida)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnEntrarbien = findViewById<Button>(R.id.btnEntrarbien)
        val nombreEmpresa = intent.getStringExtra("nombre_empresa")
        Log.d("MainActivityBienvenida", "Nombre de la empresa: $nombreEmpresa")

        btnEntrarbien.setOnClickListener {
            if (nombreEmpresa != null) {
                gotoRegistro( nombreEmpresa )
            }
        }
    }

    private fun gotoRegistro(nombreEmpresa: String) {
        val intent = Intent(this, Registro::class.java).apply{
            putExtra("nombre_empresa", nombreEmpresa)
        }
        startActivity(intent)
    }
}
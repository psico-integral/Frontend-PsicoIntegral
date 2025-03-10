package com.cinergia.psicointegral.bienvenida

import android.content.Intent
import android.os.Bundle
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

        btnEntrarbien.setOnClickListener { gotoRegistro() }
    }

    private fun gotoRegistro() {
        val intent = Intent(this, Registro::class.java)
        startActivity(intent)
    }
}
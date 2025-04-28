package com.cinergia.psicointegral

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cinergia.psicointegral.Bienvenida.Bienvenida
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var usuarioEditText: EditText
    private lateinit var contrasenaEditText: EditText
    private lateinit var tvErrorUsuario: TextView
    private lateinit var tvErrorContraseña: TextView
    private lateinit var tvErrorCredenciales: TextView
    private lateinit var database: FirebaseDatabase
    private lateinit var empresaRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar las vistas
        usuarioEditText = findViewById(R.id.usuario)
        contrasenaEditText = findViewById(R.id.contrasena)
        tvErrorUsuario = findViewById(R.id.tvErrorUsuario)
        tvErrorContraseña = findViewById(R.id.tvErrorContraseña)
        tvErrorCredenciales = findViewById(R.id.tvErrorcredenciales)

        val btnEntrar = findViewById<Button>(R.id.btnEntrar)

        database = FirebaseDatabase.getInstance("https://psicointegral-encuestas-default-rtdb.firebaseio.com/")
        empresaRef = database.reference.child("empresa")

        btnEntrar.setOnClickListener {
            val usuario = usuarioEditText.text.toString().trim()
            val contrasena = contrasenaEditText.text.toString().trim()

            if (usuario.isNotEmpty() && contrasena.isNotEmpty()) {
                validarCredenciales(usuario, contrasena)
            } else {
                mostrarErrores(usuario, contrasena)
            }
        }
    }

    // ✅ Mostrar errores si faltan datos
    private fun mostrarErrores(usuario: String, contrasena: String) {
        if (usuario.isEmpty()) {
            tvErrorUsuario.text = "El usuario es requerido"
            tvErrorUsuario.visibility = TextView.VISIBLE
        } else {
            tvErrorUsuario.visibility = TextView.GONE
        }

        if (contrasena.isEmpty()) {
            tvErrorContraseña.text = "La contraseña es requerida"
            tvErrorContraseña.visibility = TextView.VISIBLE
        } else {
            tvErrorContraseña.visibility = TextView.GONE
        }
    }

    // ✅ Validar las credenciales en Firebase y guardar el nombre de la empresa
    private fun validarCredenciales(usuario: String, contrasena: String) {
        tvErrorCredenciales.visibility = TextView.GONE

        empresaRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val dataSnapshot = task.result
                var credencialesValidas = false
                var nombreEmpresa = ""

                dataSnapshot?.children?.forEach { empresa ->
                    val storedUsuario = empresa.child("usuario").getValue(String::class.java)
                    val storedContrasena = empresa.child("contrasena").getValue(String::class.java)
                    val storedNombre = empresa.child("nombre").getValue(String::class.java)

                    // ✅ Validación directa
                    if (storedUsuario == usuario && storedContrasena == contrasena) {
                        credencialesValidas = true
                        nombreEmpresa = storedNombre ?: ""
                    }
                }

                if (credencialesValidas) {
                    Toast.makeText(this, "Login exitoso ✅", Toast.LENGTH_SHORT).show()

                    // ✅ Guardar el nombre de la empresa correctamente
                    guardarNombreEmpresa(nombreEmpresa)

                    // ✅ Pasar el nombre a la siguiente pantalla
                    gotoBienvenida(nombreEmpresa)
                } else {
                    tvErrorCredenciales.text = "Usuario o contraseña incorrectos⚠️"
                    tvErrorCredenciales.visibility = TextView.VISIBLE
                }
            } else {
                Toast.makeText(this, "Error al consultar la base de datos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun guardarNombreEmpresa(nombreEmpresa: String) {
        val sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("nombre_empresa", nombreEmpresa)
        editor.apply()
    }

    private fun gotoBienvenida(nombreEmpresa: String) {
        val intent = Intent(this, Bienvenida::class.java).apply {
            putExtra("nombre_empresa", nombreEmpresa)
        }
        startActivity(intent)
    }
}

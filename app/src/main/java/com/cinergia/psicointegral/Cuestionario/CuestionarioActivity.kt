package com.cinergia.psicointegral.Cuestionario

import android.os.Bundle
<<<<<<< HEAD
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cinergia.psicointegral.R

class CuestionarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cuestionario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
=======
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cinergia.psicointegral.databinding.ActivityCuestionarioBinding

class CuestionarioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCuestionarioBinding
    private val viewModel: CuestionarioViewModel by viewModels()
    private lateinit var cuestionarioAdapter: CuestionarioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCuestionarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombreEmpresa = intent.getStringExtra("nombre_empresa")
        Log.d("Cuestionario", "Nombre de la empresa: $nombreEmpresa")
        val nombreEmpleado = intent.getStringExtra("nombre_empleado")
        Log.d("Cuestionario", "Nombre de la empresa: $nombreEmpleado")

        val cuestionario = obtenerCuestionarios()
        val listaPreguntas = cuestionario.cuestionario.values.flatten().flatMap { it.seccion.values }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // 🔹 Crear Adapter solo una vez
        cuestionarioAdapter = CuestionarioAdapter(
            preguntas = listaPreguntas,
            onRespuestaSeleccionada = { index, respuesta ->
                val pregunta = listaPreguntas[index]
                viewModel.guardarRespuesta(pregunta, respuesta)
            },
            respuestasGuardadas = mutableMapOf()
        )

        binding.recyclerView.adapter = cuestionarioAdapter

        // 🔹 Observar cambios en respuestas sin recrear el Adapter
        viewModel.respuestas.observe(this) { respuestasGuardadas ->
            cuestionarioAdapter.actualizarRespuestas(respuestasGuardadas)
>>>>>>> bf24675 (Cuestionario funcion 50%)
        }
    }
}
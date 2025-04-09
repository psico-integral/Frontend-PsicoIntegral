package com.cinergia.psicointegral.Cuestionario

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cinergia.psicointegral.databinding.ActivityCuestionarioBinding
import com.google.firebase.FirebaseApp

class CuestionarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCuestionarioBinding
    private val viewModel: CuestionarioViewModel by viewModels()
    private lateinit var adapter: CuestionarioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        binding = ActivityCuestionarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombreEmpresa = intent.getStringExtra("nombre_empresa") ?: "empresa_desconocida"
        val nombreEmpleado = intent.getStringExtra("nombre_empleado") ?: "empleado_desconocido"

        Log.d("Cuestionario", "Nombre de la empresa: $nombreEmpresa")
        Log.d("Cuestionario", "Nombre del empleado: $nombreEmpleado")

        viewModel.setNombreEmpresaEmpleado(nombreEmpresa, nombreEmpleado)

        // Configurar RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Observar cambios en clave del cuestionario
        viewModel.claveActual.observe(this) { clave ->
            if (clave == "fin") {
                Toast.makeText(this, "¡Has completado todos los cuestionarios!", Toast.LENGTH_LONG).show()
                return@observe
            }

            viewModel.indiceSeccion.observe(this) { indice ->
                val cuestionarios = obtenerCuestionarios().cuestionario
                val seccionActual = cuestionarios[clave]?.getOrNull(indice)

                if (seccionActual != null) {
                    val listaPreguntas = seccionActual.seccion.toList()

                    adapter = CuestionarioAdapter(
                        listaPreguntas,
                        onRespuestaSeleccionada = { id, respuesta ->
                            val tipo = seccionActual.seccion[id]?.tipo ?: "desconocido"
                            viewModel.guardarRespuesta(id, respuesta, tipo)
                        },
                        respuestasSeleccionadas = viewModel.respuestas.value ?: emptyMap(),
                        mostrarSoloPrimera = viewModel.mostrarSoloPrimeraPregunta.value == true)

                    binding.recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this, "Has terminado este cuestionario", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Observar cuando finaliza todo
        viewModel.finalizado.observe(this) { terminado ->
            if (terminado) {
                Toast.makeText(this, "Gracias por contestar el cuestionario", Toast.LENGTH_LONG).show()
            }
        }

        // Solo se inicializa si es la primera vez
        if (viewModel.indiceSeccion.value == 0) {
            viewModel.reiniciarCuestionario()
        }
    }
}

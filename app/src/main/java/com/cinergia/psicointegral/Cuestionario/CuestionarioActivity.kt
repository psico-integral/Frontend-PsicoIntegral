package com.cinergia.psicointegral.Cuestionario

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cinergia.psicointegral.databinding.ActivityCuestionarioBinding

class CuestionarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCuestionarioBinding
    private val viewModel: CuestionarioViewModel by viewModels()
    private lateinit var adapter: CuestionarioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCuestionarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombreEmpresa = intent.getStringExtra("nombre_empresa")
        Log.d("Cuestionario", "Nombre de la empresa: $nombreEmpresa")
        val nombreEmpleado = intent.getStringExtra("nombre_empleado")
        Log.d("Cuestionario", "Nombre del empleado: $nombreEmpleado")

        // Configurar RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Observar cambios en clave del cuestionario
        viewModel.claveActual.observe(this) { clave ->
            if (clave == "fin") {
                Toast.makeText(this, "¡Has completado todos los cuestionarios!", Toast.LENGTH_LONG).show()
                // Aquí puedes cerrar la actividad, mostrar resultados, etc.
                return@observe
            }

            // Observar cambios en sección dentro de este cuestionario
            viewModel.indiceSeccion.observe(this) { indice ->
                val cuestionarios = obtenerCuestionarios().cuestionario
                val seccionActual = cuestionarios[clave]?.getOrNull(indice)

                if (seccionActual != null) {
                    val listaPreguntas = seccionActual.seccion.toList()
                    adapter = CuestionarioAdapter(
                        listaPreguntas,
                        { id, respuesta ->
                            val tipo = seccionActual.seccion[id]?.tipo ?: "desconocido"
                            viewModel.guardarRespuesta(id, respuesta, tipo)
                        },
                        viewModel.respuestas.value ?: emptyMap()
                    )
                    binding.recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this, "Has terminado este cuestionario", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Inicializa solo si es la primera vez
        if (viewModel.indiceSeccion.value == 0) {
            viewModel.reiniciarCuestionario()
        }

        // Opcional: observar cambios en respuestas
        viewModel.respuestas.observe(this) {
            // Podrías actualizar UI, progreso o guardar datos
        }
    }
}

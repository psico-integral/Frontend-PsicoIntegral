package com.cinergia.psicointegral.Cuestionario

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.cinergia.psicointegral.databinding.ItemPreguntaBinding

class CuestionarioAdapter(
    private val preguntas: List<Pair<String, Pregunta>>, // 👈 Ahora la lista contiene el ID y la Pregunta
    private val onRespuestaSeleccionada: (String, String) -> Unit,
    private var respuestasGuardadas: Map<String, String>
) : RecyclerView.Adapter<CuestionarioAdapter.PreguntaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreguntaViewHolder {
        val binding = ItemPreguntaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PreguntaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PreguntaViewHolder, position: Int) {
        val (idPregunta, pregunta) = preguntas[position] // Desestructura el par
        holder.bind(idPregunta, pregunta, respuestasGuardadas[idPregunta])
    }

    override fun getItemCount(): Int = preguntas.size

    fun actualizarRespuestas(nuevasRespuestas: Map<String, String>) {
        respuestasGuardadas = nuevasRespuestas
        notifyDataSetChanged()
    }

    inner class PreguntaViewHolder(private val binding: ItemPreguntaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(idPregunta: String, pregunta: Pregunta, respuestaSeleccionada: String?) {
            binding.textPregunta.text = pregunta.pregunta
            binding.radioGroup.removeAllViews()

            val opciones = when (pregunta.tipo) {
                "si_no" -> listOf("Sí", "No")
                "frecuencia" -> listOf("Nunca", "Rara vez", "Algunas veces", "Frecuentemente", "Siempre")
                else -> emptyList()
            }

            opciones.forEachIndexed { _, opcion ->
                val radioButton = RadioButton(binding.root.context).apply {
                    text = opcion
                    id = ViewCompat.generateViewId()
                    isChecked = respuestaSeleccionada == opcion
                    setOnClickListener {
                        onRespuestaSeleccionada(idPregunta, opcion) // 👈 Ahora pasamos el ID
                    }
                }
                binding.radioGroup.addView(radioButton)
            }
        }
    }
}

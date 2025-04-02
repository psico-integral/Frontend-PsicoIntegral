package com.cinergia.psicointegral.Cuestionario

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.cinergia.psicointegral.databinding.ItemPreguntaBinding

class CuestionarioAdapter(
    private val preguntas: List<Pregunta>,
    private val onRespuestaSeleccionada: (Int, String) -> Unit,
    private var respuestasGuardadas: Map<Pregunta, String>
) : RecyclerView.Adapter<CuestionarioAdapter.PreguntaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreguntaViewHolder {
        val binding = ItemPreguntaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PreguntaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PreguntaViewHolder, position: Int) {
        val pregunta = preguntas[position]
        holder.bind(pregunta, respuestasGuardadas[pregunta])
    }

    override fun getItemCount(): Int = preguntas.size

    fun actualizarRespuestas(nuevasRespuestas: Map<Pregunta, String>) {
        respuestasGuardadas = nuevasRespuestas
        notifyDataSetChanged() // 🔹 Actualiza solo los datos sin recrear el Adapter
    }

    inner class PreguntaViewHolder(private val binding: ItemPreguntaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pregunta: Pregunta, respuestaSeleccionada: String?) {
            binding.textPregunta.text = pregunta.pregunta
            binding.radioGroup.removeAllViews()

            val opciones = when (pregunta.tipo) {
                "si_no" -> listOf("Sí", "No")
                "frecuencia" -> listOf("Nunca", "Rara vez", "Algunas veces", "Frecuentemente", "Siempre")
                else -> emptyList()
            }

            opciones.forEachIndexed { index, opcion ->
                val radioButton = RadioButton(binding.root.context).apply {
                    text = opcion
                    id = ViewCompat.generateViewId()
                    isChecked = respuestaSeleccionada == opcion
                    setOnClickListener {
                        onRespuestaSeleccionada(adapterPosition, opcion)
                    }
                }
                binding.radioGroup.addView(radioButton)
            }
        }
    }
}

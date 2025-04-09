package com.cinergia.psicointegral.Cuestionario

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.cinergia.psicointegral.databinding.ItemPreguntaBinding

class CuestionarioAdapter(
    private val preguntas: List<Pair<String, Pregunta>>,
    private val onRespuestaSeleccionada: (String, String) -> Unit,
    private val respuestasGuardadas: Map<String, String>,
    private val mostrarSoloPrimera: Boolean
) : RecyclerView.Adapter<CuestionarioAdapter.PreguntaViewHolder>() {

    private val opciones = listOf("Sí", "No", "Nunca", "Rara vez", "Algunas veces", "Frecuentemente", "Siempre")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreguntaViewHolder {
        val binding = ItemPreguntaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PreguntaViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (mostrarSoloPrimera && preguntas.isNotEmpty()) 1 else preguntas.size
    }

    override fun onBindViewHolder(holder: PreguntaViewHolder, position: Int) {
        val (id, pregunta) = preguntas[position]
        holder.bind(id, pregunta)
    }

    inner class PreguntaViewHolder(private val binding: ItemPreguntaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(id: String, pregunta: Pregunta) {
            binding.txtPregunta.text = pregunta.pregunta
            val radioGroup = binding.radioGroupRespuestas
            radioGroup.removeAllViews()

            for (opcion in opciones) {
                val radioButton = RadioButton(binding.root.context)
                radioButton.text = opcion
                radioGroup.addView(radioButton)

                if (respuestasGuardadas[id] == opcion) {
                    radioButton.isChecked = true
                }
            }

            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                val selected = group.findViewById<RadioButton>(checkedId)
                val respuesta = selected?.text?.toString() ?: ""
                onRespuestaSeleccionada(id, respuesta)
            }
        }
    }
}


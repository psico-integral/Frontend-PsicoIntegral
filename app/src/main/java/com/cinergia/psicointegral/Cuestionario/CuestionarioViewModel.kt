package com.cinergia.psicointegral.Cuestionario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CuestionarioViewModel : ViewModel() {
    private val _respuestas = MutableLiveData<Map<Pregunta, String>>(emptyMap())
    val respuestas: LiveData<Map<Pregunta, String>> get() = _respuestas

    fun guardarRespuesta(pregunta: Pregunta, respuesta: String) {
        val respuestasActualizadas = _respuestas.value?.toMutableMap() ?: mutableMapOf()
        respuestasActualizadas[pregunta] = respuesta
        _respuestas.value = respuestasActualizadas
    }

    fun obtenerRespuestas(): Map<Pregunta, String> {
        return _respuestas.value ?: emptyMap()
    }
}


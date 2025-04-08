package com.cinergia.psicointegral.Cuestionario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CuestionarioViewModel : ViewModel() {
    private val cuestionariosMap = obtenerCuestionarios().cuestionario
    private val clavesCuestionarios = cuestionariosMap.keys.toList()

    private val _claveActual = MutableLiveData(clavesCuestionarios.first())
    val claveActual: LiveData<String> get() = _claveActual

    private val _indiceSeccion = MutableLiveData(0)
    val indiceSeccion: LiveData<Int> get() = _indiceSeccion

    private val _respuestas = MutableLiveData<MutableMap<String, String>>(mutableMapOf())
    val respuestas: LiveData<MutableMap<String, String>> get() = _respuestas

    private val _mostrarSoloPrimeraPregunta = MutableLiveData(true)
    val mostrarSoloPrimeraPregunta: LiveData<Boolean> get() = _mostrarSoloPrimeraPregunta

    private val _finalizado = MutableLiveData(false)
    val finalizado: LiveData<Boolean> get() = _finalizado

    fun iniciarCuestionario(clave: String) {
        _claveActual.value = clave
        _indiceSeccion.value = 0
        _respuestas.value = mutableMapOf()
        _mostrarSoloPrimeraPregunta.value = true
        _finalizado.value = false
    }

    fun avanzarSeccion() {
        limpiarRespuestasDeSeccionActual()

        val clave = _claveActual.value ?: return
        val indiceActual = _indiceSeccion.value ?: 0
        val totalSecciones = cuestionariosMap[clave]?.size ?: 0
        if (indiceActual + 1 < totalSecciones) {
            _indiceSeccion.value = indiceActual + 1
        } else {
            avanzarCuestionario()
        }
    }

    fun avanzarCuestionario() {
        val indiceActual = clavesCuestionarios.indexOf(_claveActual.value)
        val siguienteIndice = indiceActual + 1

        if (siguienteIndice < clavesCuestionarios.size) {
            iniciarCuestionario(clavesCuestionarios[siguienteIndice])
        } else {
            _claveActual.value = "fin"
            _finalizado.value = true
        }
    }

    fun guardarRespuesta(id: String, respuesta: String, tipo: String) {
        val respuestasActuales = _respuestas.value ?: mutableMapOf()
        respuestasActuales[id] = respuesta
        _respuestas.value = respuestasActuales

        val clave = _claveActual.value ?: return
        val seccionIndex = _indiceSeccion.value ?: return

        if (clave == "cuestionario_01") {
            if (_mostrarSoloPrimeraPregunta.value == true && seccionIndex == 0 && id == "01") {
                if (respuesta.equals("no", true)) {
                    _finalizado.value = true
                    _claveActual.value = "fin"
                } else {
                    _mostrarSoloPrimeraPregunta.value = false
                }
                return
            }

            val secciones = cuestionariosMap[clave] ?: return
            val totalPreguntas = secciones[seccionIndex].seccion.size
            val acumuladasPrevias = secciones.take(seccionIndex).sumOf { it.seccion.size }
            val respondidas = respuestasActuales.size - acumuladasPrevias

            if (respondidas >= totalPreguntas) {
                avanzarSeccion()
            }
        } else {
            val secciones = cuestionariosMap[clave] ?: return
            val totalPreguntas = secciones[seccionIndex].seccion.size
            val acumuladasPrevias = secciones.take(seccionIndex).sumOf { it.seccion.size }
            val respondidas = respuestasActuales.size - acumuladasPrevias

            if (respondidas >= totalPreguntas) avanzarSeccion()
        }
    }

    private fun limpiarRespuestasDeSeccionActual() {
        val clave = _claveActual.value ?: return
        val seccionIndex = _indiceSeccion.value ?: return
        val secciones = cuestionariosMap[clave] ?: return
        val preguntasSeccionActual = secciones.getOrNull(seccionIndex)?.seccion?.keys ?: return

        preguntasSeccionActual.forEach { _respuestas.value?.remove(it) }
    }

    fun reiniciarCuestionario() {
        _indiceSeccion.value = 0
        _respuestas.value = mutableMapOf()
        _mostrarSoloPrimeraPregunta.value = true
        _finalizado.value = false
    }
}



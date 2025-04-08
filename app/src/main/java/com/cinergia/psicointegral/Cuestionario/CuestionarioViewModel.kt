package com.cinergia.psicointegral.Cuestionario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CuestionarioViewModel : ViewModel() {

    private val cuestionariosMap = obtenerCuestionarios().cuestionario
    private val clavesCuestionarios = cuestionariosMap.keys.toList()

    private var _claveActual = MutableLiveData(clavesCuestionarios.first())
    val claveActual: LiveData<String> get() = _claveActual

    private val _indiceSeccion = MutableLiveData(0)
    val indiceSeccion: LiveData<Int> get() = _indiceSeccion

    private val _respuestas = MutableLiveData<MutableMap<String, String>>(mutableMapOf())
    val respuestas: LiveData<MutableMap<String, String>> get() = _respuestas

    fun guardarRespuesta(id: String, respuesta: String, tipo: String) {
        _respuestas.value?.put(id, respuesta)
        _respuestas.postValue(_respuestas.value)

        if (tipo == "si_no" && respuesta.equals("sí", true)) {
            avanzarSeccion()
        }
    }

    fun avanzarSeccion() {
        val clave = _claveActual.value ?: return
        val secciones = cuestionariosMap[clave] ?: return
        val indiceActual = _indiceSeccion.value ?: 0

        if (indiceActual + 1 < secciones.size) {
            _indiceSeccion.value = indiceActual + 1
        } else {
            avanzarCuestionario()
        }
    }

    private fun avanzarCuestionario() {
        val indiceActual = clavesCuestionarios.indexOf(_claveActual.value)
        val siguienteIndice = indiceActual + 1

        if (siguienteIndice < clavesCuestionarios.size) {
            _claveActual.value = clavesCuestionarios[siguienteIndice]
            _indiceSeccion.value = 0
            _respuestas.value = mutableMapOf()
        } else {
            // Si ya no hay más cuestionarios, puedes manejarlo como final
            _claveActual.value = "fin"
        }
    }

    fun reiniciarCuestionario() {
        _indiceSeccion.value = 0
        _respuestas.value = mutableMapOf()
    }
}

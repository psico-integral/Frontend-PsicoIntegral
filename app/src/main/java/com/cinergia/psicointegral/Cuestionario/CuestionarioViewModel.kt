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

    fun iniciarCuestionario(clave: String) {
        _claveActual.value = clave
        _indiceSeccion.value = 0
        _respuestas.value = mutableMapOf()
    }

    fun avanzarSeccion() {
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
        }
    }

    fun guardarRespuesta(id: String, respuesta: String, tipo: String) {
        _respuestas.value?.put(id, respuesta)
        _respuestas.postValue(_respuestas.value)

        val clave = _claveActual.value ?: return
        val seccionIndex = _indiceSeccion.value ?: return

        if (clave == "cuestionario_01") {
            when (seccionIndex) {
                0 -> {
                    val totalPreguntas = cuestionariosMap[clave]?.get(0)?.seccion?.size ?: 0
                    val respuestasActuales = _respuestas.value?.size ?: 0
                    if (respuestasActuales == totalPreguntas) {
                        evaluarSeccionIAcontecimiento()
                    }
                }
                3 -> {
                    val respuestas = _respuestas.value ?: return
                    val secciones = cuestionariosMap[clave] ?: return
                    val totalPreguntas = secciones[3].seccion.size
                    val acumuladasPrevias = secciones.take(3).sumOf { it.seccion.size }
                    if ((respuestas.size - acumuladasPrevias) == totalPreguntas) {
                        evaluarNecesitaAtencion()
                    }
                }
            }
        }
    }

    private fun evaluarSeccionIAcontecimiento() {
        val respuestasSeccionI = _respuestas.value ?: return
        val algunSi = respuestasSeccionI.values.any { it.equals("sí", true) }

        if (!algunSi) {
            _claveActual.value = "fin"
        } else {
            avanzarSeccion()
        }
    }

    private fun evaluarNecesitaAtencion() {
        val respuestas = _respuestas.value ?: return
        val secciones = cuestionariosMap["cuestionario_01"] ?: return

        val respuestasSeccionII = secciones[1].seccion.keys
            .mapNotNull { respuestas[it] }
            .count { it.equals("sí", true) }

        val respuestasSeccionIII = secciones[2].seccion.keys
            .mapNotNull { respuestas[it] }
            .count { it.equals("sí", true) }

        val respuestasSeccionIV = secciones[3].seccion.keys
            .mapNotNull { respuestas[it] }
            .count { it.equals("sí", true) }

        val necesitaAtencion =
            respuestasSeccionII >= 1 || respuestasSeccionIII >= 3 || respuestasSeccionIV >= 2

        if (necesitaAtencion) {
            println("🔴 Se requiere atención clínica")
        } else {
            println("🟢 No se requiere atención clínica")
        }

        avanzarCuestionario()
    }

    fun reiniciarCuestionario() {
        _indiceSeccion.value = 0
        _respuestas.value = mutableMapOf()
    }
}

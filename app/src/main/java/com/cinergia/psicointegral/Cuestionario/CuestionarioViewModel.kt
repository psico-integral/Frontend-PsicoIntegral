package com.cinergia.psicointegral.Cuestionario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase

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

    private var nombreEmpresa: String = ""
    private var nombreEmpleado: String = ""

    fun setNombreEmpresaEmpleado(empresa: String, empleado: String) {
        nombreEmpresa = empresa
        nombreEmpleado = empleado
    }

    fun iniciarCuestionario(clave: String) {
        _claveActual.value = clave
        _indiceSeccion.value = 0
        _respuestas.value = mutableMapOf()
        _mostrarSoloPrimeraPregunta.value = clave == "cuestionario_01"
        _finalizado.value = false
    }

    fun avanzarSeccion() {
        val clave = _claveActual.value ?: return
        val indiceActual = _indiceSeccion.value ?: 0
        val totalSecciones = cuestionariosMap[clave]?.size ?: 0

        if (indiceActual + 1 < totalSecciones) {
            _indiceSeccion.value = indiceActual + 1
        } else {
            guardarRespuestasEnFirebase()
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
        val clave = _claveActual.value ?: return
        val seccionIndex = _indiceSeccion.value ?: return

        if (clave == "cuestionario_01" &&
            _mostrarSoloPrimeraPregunta.value == true &&
            seccionIndex == 0 && id == "01"
        ) {
            if (respuesta.equals("no", true)) {
                _finalizado.value = true
                _claveActual.value = "fin"
                return
            } else {
                _mostrarSoloPrimeraPregunta.value = false
                _respuestas.value = mutableMapOf() // limpia solo esa respuesta
                return
            }
        }

        val respuestasActuales = _respuestas.value ?: mutableMapOf()
        respuestasActuales[id] = respuesta
        _respuestas.value = respuestasActuales

        val secciones = cuestionariosMap[clave] ?: return
        val idsPreguntasSeccion = secciones[seccionIndex].seccion.keys
        val respondidas = idsPreguntasSeccion.count { respuestasActuales.containsKey(it) }

        if (respondidas >= idsPreguntasSeccion.size) {
            avanzarSeccion()
        }
    }

    private fun guardarRespuestasEnFirebase() {
        val clave = _claveActual.value ?: return
        val respuestasMap = _respuestas.value ?: return
        val secciones = cuestionariosMap[clave] ?: return

        val estructura = mutableMapOf<String, MutableMap<String, Int>>()

        var seccionIndex = 1
        for (seccion in secciones) {
            val idSeccion = "seccion_$seccionIndex"
            val respuestasSeccion = mutableMapOf<String, Int>()

            for ((idPregunta, pregunta) in seccion.seccion) {
                val respuestaTexto = respuestasMap[idPregunta] ?: "No contestó"
                val valorNumerico = convertirRespuestaANumero(respuestaTexto)
                respuestasSeccion[idPregunta] = valorNumerico
            }

            estructura[idSeccion] = respuestasSeccion
            seccionIndex++
        }

        val database = FirebaseDatabase.getInstance("https://psicointegral-usuariorespuesta-default-rtdb.firebaseio.com/")
        val ref = database.reference
            .child(nombreEmpresa)
            .child(nombreEmpleado)
            .child("cuestionarios")
            .child(clave)

        ref.setValue(estructura)
    }

    private fun convertirRespuestaANumero(respuesta: String): Int {
        return when (respuesta.lowercase()) {
            "sí" -> 1
            "no" -> 2
            "nunca" -> 3
            "rara vez" -> 4
            "algunas veces" -> 5
            "frecuentemente" -> 6
            "siempre" -> 7
            "no contestó" -> 0
            else -> 0
        }
    }

    fun reiniciarCuestionario() {
        _indiceSeccion.value = 0
        _respuestas.value = mutableMapOf()
        _mostrarSoloPrimeraPregunta.value = _claveActual.value == "cuestionario_01"
        _finalizado.value = false
    }
}

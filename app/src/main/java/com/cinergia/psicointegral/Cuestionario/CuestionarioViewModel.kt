package com.cinergia.psicointegral.Cuestionario

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase

class CuestionarioViewModel : ViewModel() {

  private var intentoAvanzarConPreguntasFaltantes = false
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

  private val _mensajeAdvertencia = MutableLiveData<String?>()
  val mensajeAdvertencia: LiveData<String?> get() = _mensajeAdvertencia

  private val _preguntasFaltantes = MutableLiveData<List<String>>(emptyList())
  val preguntasFaltantes: LiveData<List<String>> get() = _preguntasFaltantes

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
    _mensajeAdvertencia.value = null
  }

  fun guardarRespuesta(id: String, respuesta: String, tipo: String, evaluarAvance: Boolean = true) {
    val clave = _claveActual.value ?: return
    val seccionIndex = _indiceSeccion.value ?: return

    if (clave == "cuestionario_01" && _mostrarSoloPrimeraPregunta.value == true &&
      seccionIndex == 0 && id == "01") {
      if (respuesta.lowercase() == "no") {
        _respuestas.value = mutableMapOf("01" to "no")
        guardarSinContestar()
        _claveActual.value = "fin"
        _finalizado.value = true
        return
      } else {
        _mostrarSoloPrimeraPregunta.value = false
        _respuestas.value = mutableMapOf()
        return
      }
    }

    val respuestasActuales = _respuestas.value ?: mutableMapOf()
    respuestasActuales[id] = respuesta
    _respuestas.value = respuestasActuales

    val faltantesActuales = _preguntasFaltantes.value?.toMutableList() ?: mutableListOf()
    if (respuesta.isNotBlank()) {
      faltantesActuales.remove(id)
    } else if (!faltantesActuales.contains(id)) {
      faltantesActuales.add(id)
    }
    _preguntasFaltantes.value = faltantesActuales

    val secciones = cuestionariosMap[clave] ?: return
    val idsPreguntasSeccion = secciones[seccionIndex].seccion.keys.toList()
    val respondidas = idsPreguntasSeccion.count { respuestasActuales.containsKey(it) }
    val total = idsPreguntasSeccion.size
    val esUltima = id == idsPreguntasSeccion.last()

    if (evaluarAvance && esUltima && respondidas < total) {
      _mensajeAdvertencia.value = "Falta responder una o más preguntas de esta sección."
      intentoAvanzarConPreguntasFaltantes = true
      _preguntasFaltantes.value = idsPreguntasSeccion.filter { !respuestasActuales.containsKey(it) }
      return
    }

    if (evaluarAvance && esUltima && respondidas == total) {
      _mensajeAdvertencia.value = null
      intentoAvanzarConPreguntasFaltantes = false
      _preguntasFaltantes.value = emptyList()
      when (clave) {
        "cuestionario_02" -> manejarFlujoCuestionario02(seccionIndex)
        "cuestionario_03" -> manejarFlujoCuestionario03(seccionIndex)
        else -> avanzarSeccion()
      }
      return
    }

    if (intentoAvanzarConPreguntasFaltantes && respondidas == total) {
      _mensajeAdvertencia.value = null
      intentoAvanzarConPreguntasFaltantes = false
      _preguntasFaltantes.value = emptyList()
      when (clave) {
        "cuestionario_02" -> manejarFlujoCuestionario02(seccionIndex)
        "cuestionario_03" -> manejarFlujoCuestionario03(seccionIndex)
        else -> avanzarSeccion()
      }
    }
  }

  private fun manejarFlujoCuestionario02(seccionIndex: Int) { /* ... */ }
  private fun manejarFlujoCuestionario03(seccionIndex: Int) { /* ... */ }
  fun avanzarSeccion() { /* ... */ }
  fun avanzarCuestionario() { guardarRespuestasEnFirebase(); /* ... */ }
  private fun limpiarRespuestasDeSeccion(seccionIndex: Int) { /* ... */ }
  private fun guardarSinContestar() { /* ... */ }
  fun guardarRespuestasEnFirebase() {
    val clave = _claveActual.value ?: return
    val respuestasMap = _respuestas.value ?: return
    val secciones = cuestionariosMap[clave] ?: return

    val estructura = mutableMapOf<String, MutableMap<String, Int>>()
    var seccionIndex = 1
    for (seccion in secciones) {
      val idSeccion = "seccion_$seccionIndex"
      val respuestasSeccion = mutableMapOf<String, Int>()
      for ((idPregunta, _) in seccion.seccion) {
        val respuestaTexto = respuestasMap[idPregunta] ?: "No contestó"
        respuestasSeccion[idPregunta] = convertirRespuestaANumero(respuestaTexto)
      }
      estructura[idSeccion] = respuestasSeccion
      seccionIndex++
    }

    val database = FirebaseDatabase.getInstance("https://psicointegral-usuariorespuesta-default-rtdb.firebaseio.com/")
    val ref = database.reference
      .child(nombreEmpresa)
      .child(nombreEmpleado)
      .child("respuestas")
      .child(clave)

    ref.setValue(estructura)
  }

  private fun convertirRespuestaANumero(respuesta: String): Int {
    return when (respuesta.lowercase()) {
      "sí", "si" -> 1
      "no" -> 2
      "nunca" -> 3
      "rara vez" -> 4
      "algunas veces" -> 5
      "frecuentemente" -> 6
      "siempre" -> 7
      else -> 0
    }
  }

  fun reiniciarCuestionario() {
    _indiceSeccion.value = 0
    _respuestas.value = mutableMapOf()
    _mostrarSoloPrimeraPregunta.value = _claveActual.value == "cuestionario_01"
    _finalizado.value = false
    _mensajeAdvertencia.value = null
    _preguntasFaltantes.value = emptyList()
  }
}

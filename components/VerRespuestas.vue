<template>
  <div class="ver-respuestas-container">
    <h1>Ver Respuestas del Empleado</h1>

    <!-- Selección del empleado -->
    <div class="empleado-selector">
      <label for="empleado">Seleccionar Empleado:</label>
      <select v-model="empleadoSeleccionado" @change="obtenerRespuestasEmpleado">
        <option v-for="empleado in empleados" :key="empleado.id" :value="empleado.id">
          {{ empleado.nombre }}
        </option>
      </select>
    </div>

    <!-- Mostrar las respuestas si existen -->
    <div v-if="empleadoRespuestas && empleadoRespuestas.length > 0">
      <table class="tabla-respuestas">
        <thead>
          <tr>
            <th>Pregunta</th>
            <th>Respuesta</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(respuesta, index) in empleadoRespuestas" :key="index">
            <td>{{ respuesta.pregunta }}</td>
            <td>{{ obtenerRespuesta(respuesta.valor) }}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Mensaje cuando no hay respuestas -->
    <div v-else>
      <p>No se han encontrado respuestas para este empleado.</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data () {
    return {
      empleados: [], // Lista de empleados
      empleadoSeleccionado: null, // ID del empleado seleccionado
      empleadoRespuestas: null // Respuestas del empleado seleccionado
    }
  },
  created () {
    this.obtenerEmpleados()
  },
  methods: {
    // Obtener la lista de empleados desde el backend
    obtenerEmpleados () {
      axios.get('/api/empleados') // Asegúrate de que el endpoint sea correcto
        .then((response) => {
          this.empleados = response.data
        })
        .catch((error) => {
          console.error('Error al obtener empleados:', error)
        })
    },

    // Obtener las respuestas de un empleado específico
    obtenerRespuestasEmpleado () {
      if (!this.empleadoSeleccionado) { return }

      axios.get(`/api/respuestas/${this.empleadoSeleccionado}`) // Endpoint para obtener respuestas
        .then((response) => {
          this.empleadoRespuestas = response.data.respuestas // Asegúrate de que la estructura de datos sea correcta
        })
        .catch((error) => {
          console.error('Error al obtener respuestas:', error)
          this.empleadoRespuestas = null // En caso de error, no mostrar respuestas
        })
    },

    // Convertir el valor numérico en su correspondiente texto
    obtenerRespuesta (valor) {
      switch (valor) {
        case 1: return 'Sí'
        case 2: return 'No'
        case 3: return 'Nunca'
        case 4: return 'Rara vez'
        case 5: return 'Algunas veces'
        case 6: return 'Frecuentemente'
        case 7: return 'Siempre'
        case 0: return 'No contestó'
        default: return 'Respuesta no válida'
      }
    }
  }
}
</script>

<style scoped>
.ver-respuestas-container {
  margin: 20px;
}
.tabla-respuestas {
  width: 100%;
  border-collapse: collapse;
}
.tabla-respuestas th,
.tabla-respuestas td {
  padding: 10px;
  border: 1px solid #ccc;
  text-align: left;
}
.empleado-selector {
  margin-bottom: 20px;
}
</style>

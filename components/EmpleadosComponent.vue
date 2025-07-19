<template>
  <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
    <div
      v-for="(respuestas, nombre) in empleados"
      :key="nombre"
      :class="['card-empleado', { 'selected': seleccionado === nombre }]"
      @click="mostrarRespuestas(nombre, respuestas)"
    >
      <div class="flex items-center justify-between mb-3">
        <h4 class="font-semibold text-gray-800 text-lg">
          {{ nombre }}
        </h4>
        <i class="fas fa-user text-blue-500 text-xl" />
      </div>
      <p class="text-sm text-gray-500 mt-1">
        {{ Object.keys(respuestas).length }} bloque(s) de respuestas
      </p>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    empleados: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      seleccionado: '' // Para almacenar el empleado seleccionado
    }
  },
  methods: {
    mostrarRespuestas (nombreEmpleado, respuestas) {
      this.seleccionado = nombreEmpleado // Actualiza el empleado seleccionado
      this.$emit('empleadoSeleccionado', { nombreEmpleado, respuestas })
    }
  }
}
</script>

<style scoped>
/* Estilo para las tarjetas de los empleados */
.card-empleado {
  transition: all 0.3s ease;
  padding: 1.5rem; /* Aumenta el padding para más espacio */
  margin-top: 1rem;
  border-radius: 1rem; /* Bordes redondeados */
  cursor: pointer;
  background-color: #ffffff; /* Fondo blanco por defecto */
  border: 1px solid #e2e8f0; /* Color de borde suave */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Sombra suave */
}

/* Efecto al pasar el ratón sobre la tarjeta */
.card-empleado:hover {
  background-color: #ebf8ff; /* Fondo suave al pasar el ratón */
  transform: scale(1.05); /* Aumenta ligeramente el tamaño */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Sombra más pronunciada */
}

/* Icono de usuario */
.card-empleado i {
  color: #4299e1; /* Color azul para el icono */
  font-size: 1.5rem; /* Aumenta el tamaño del icono */
}

/* Título de la tarjeta (nombre del empleado) */
.card-empleado h4 {
  font-size: 1.125rem; /* Tamaño de fuente del nombre */
  font-weight: 600; /* Fuente semi-negrita */
  color: #2d3748; /* Color gris oscuro */
  margin-bottom: 0.5rem; /* Separación del icono */
}

/* Texto con el número de bloques de respuestas */
.card-empleado p {
  font-size: 0.875rem; /* Tamaño de fuente más pequeño */
  color: #718096; /* Gris claro */
  margin-top: 0.25rem; /* Separación superior */
}

/* Fondo y borde de la tarjeta seleccionada */
.card-empleado.selected {
  background-color: #ebf8ff; /* Fondo azul claro */
  border-color: #4299e1; /* Borde azul */
  box-shadow: 0 4px 10px rgba(66, 153, 225, 0.3); /* Sombra azul más pronunciada */
}

/* Efecto hover para el icono (cambio de color) */
.card-empleado:hover i {
  color: #3182ce; /* Cambia el color del icono al pasar el ratón */
}
</style>

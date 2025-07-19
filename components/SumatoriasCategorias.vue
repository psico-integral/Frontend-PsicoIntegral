<template>
  <div class="bg-white rounded-xl p-8 shadow-lg">
    <h3 class="mb-4 titulo-con-padding">
      Puntaje por Categoria 
    </h3>

    <div class="tarjetas-contenedor">
      <div
        v-for="(puntaje, categoria) in sumatorias"
        :key="categoria"
        :class="['puntaje-tarjeta', obtenerColor(puntaje, categoria)]"
      >
        <h4 class="puntaje-titulo">
          {{ categoria }}
        </h4>
        <p class="puntaje-valor">
          {{ puntaje }}
        </p>
        <p>
          Nivel: {{ obtenerNivelTexto(puntaje, categoria) }}
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import { obtenerNivelRiesgo } from '@/utils/nivelRiesgo.js'

export default {
  name: 'SumatoriasCategorias',
  props: {
    sumatorias: {
      type: Object,
      required: true
    },
    cuestionario: {
      type: Number,
      required: true
    }
  },
  methods: {
    obtenerNivelTexto(puntaje, categoria) {
      const resultado = obtenerNivelRiesgo(puntaje, categoria, 'categoria', this.cuestionario)
      return resultado.nivel
    },
    obtenerColor(puntaje, categoria) {
      const resultado = obtenerNivelRiesgo(puntaje, categoria, 'categoria', this.cuestionario)
      const colores = {
        'verde': 'bg-green-500',
        'amarillo': 'bg-yellow-400 text-black',
        'naranja': 'bg-orange-400',
        'rojo': 'bg-red-500',
        'rojo-oscuro': 'bg-red-800',
        'gris': 'bg-gray-400'
      }
      return colores[resultado.color] || 'bg-gray-400'
    }
  }
}
</script>

<style>
.titulo-con-padding {
  padding-top: 1rem;
  font-weight: 700;
  font-size: 1.125rem;
}

.tarjetas-contenedor {
  display: flex;
  flex-direction: row;
  overflow-x: auto;
  gap: 1rem;
  padding-bottom: 1rem;
  scroll-snap-type: x mandatory;
  scroll-behavior: smooth;
  -webkit-overflow-scrolling: touch;
}

.tarjetas-contenedor::-webkit-scrollbar {
  display: none;
}

.tarjetas-contenedor {
  -ms-overflow-style: none;
  scrollbar-width: none;
}

.puntaje-tarjeta {
  color: white;
  border-radius: 1rem;
  padding: 1.5rem;
  box-shadow: 0 2px 8px rgba(114, 82, 188, 0.5);
  transition: box-shadow 0.3s ease;
  flex-shrink: 0;
  scroll-snap-align: start;
}


.puntaje-tarjeta:hover {
  box-shadow: 0 4px 16px rgba(107, 70, 193, 0.8);
}

.puntaje-titulo {
  font-weight: 700;
  font-size: 1.125rem;
}

.puntaje-valor {
  font-weight: 600;
  font-size: 1.5rem;
  margin-top: 0.5rem;
}
</style>

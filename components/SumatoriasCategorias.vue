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
      console.log('DEBUG COLOR', { puntaje, categoria, resultado })
      const colores = {
        'verde': 'color-verde',
        'amarillo': 'color-amarillo',
        'naranja': 'color-naranja',
        'rojo': 'color-rojo',
        'rojo-oscuro': 'color-rojo-oscuro',
        'gris': 'color-gris'
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
.color-verde {
  background-color: #22c55e; /* verde */
  color: white;
}

.color-amarillo {
  background-color: #eab308; /* amarillo */
  color: black;
}

.color-naranja {
  background-color: #f97316; /* naranja */
  color: white;
}

.color-rojo {
  background-color: #ef4444; /* rojo */
  color: white;
}

.color-rojo-oscuro {
  background-color: #991b1b; /* rojo oscuro */
  color: white;
}

.color-gris {
  background-color: #9ca3af; /* gris */
  color: black;
}
</style>

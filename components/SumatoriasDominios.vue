<template>
  <div class="bg-white rounded-xl p-8 shadow-lg">
    <h3 class="mb-4 titulo-con-padding">
      Puntaje por Dominio
    </h3>

    <div class="tarjetas-contenedor">
      <div
        v-for="(puntaje, dominio) in sumatorias"
        :key="dominio"
        class="puntaje-tarjeta"
        :class="obtenerColor(puntaje, dominio)"
      >
        <h4 class="puntaje-titulo">
          {{ dominio }}
        </h4>
        <p class="puntaje-valor">
          {{ puntaje }}
        </p>
        <p class="puntaje-nivel">
          Nivel: {{ obtenerNivelTexto(puntaje, dominio) }}
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import { obtenerNivelRiesgo } from '@/utils/nivelRiesgo.js'

export default {
  name: 'SumatoriasDominios',
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
    obtenerNivelTexto (puntaje, dominio) {
      const resultado = obtenerNivelRiesgo(puntaje, dominio, 'dominio', this.cuestionario)
      return resultado.nivel
    },
    obtenerColor (puntaje, dominio) {
      const resultado = obtenerNivelRiesgo(puntaje, dominio, 'dominio', this.cuestionario)
      const colores = {
        verde: 'color-verde',
        amarillo: 'color-amarillo',
        naranja: 'color-naranja',
        rojo: 'color-rojo',
        'rojo-oscuro': 'color-rojo-oscuro',
        gris: 'color-gris'
      }
      return colores[resultado.color] || 'bg-gray-400'
    }
  }
}
</script>

<style scoped>
.titulo-con-padding {
  padding-top: 1rem;
  font-weight: 700;
  font-size: 1.125rem;
}

.tarjetas-contenedor {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;         
  gap: 1rem;               
  padding-bottom: 1rem; 
}

.puntaje-tarjeta {
  min-width: 100px;
  border-radius: 1rem;
  padding: 0.5rem;
  box-shadow: 0 2px 8px rgba(114, 82, 188, 0.5);
  transition: box-shadow 0.3s ease;
  flex-shrink: 0;
}

.puntaje-tarjeta:hover {
  box-shadow: 0 4px 16px rgba(107, 70, 193, 0.8);
}

.puntaje-titulo {
  font-weight: 700;
  font-size: 1rem;
  margin-top: 0.5rem;
}

.puntaje-valor {
  font-weight: 600;
  font-size: 1.5rem;
  margin-top: 0.5rem;
}

.puntaje-nivel {
  font-weight: 700;
  font-size: 1rem;
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
  background-color: #1652f9; /* naranja */
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

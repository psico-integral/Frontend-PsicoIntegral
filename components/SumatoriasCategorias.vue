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
        <p class="puntaje-nivel">
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
    obtenerNivelTexto (puntaje, categoria) {
      const resultado = obtenerNivelRiesgo(puntaje, categoria, 'categoria', this.cuestionario)
      return resultado.nivel
    },
    obtenerColor (puntaje, categoria) {
      const resultado = obtenerNivelRiesgo(puntaje, categoria, 'categoria', this.cuestionario)
      console.log('DEBUG COLOR', { puntaje, categoria, resultado })
      const colores = {
        azul: 'color-azul',
        verde: 'color-verde',
        amarillo: 'color-amarillo',
        naranja: 'color-naranja',
        rojo: 'color-rojo',
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
  margin-left: 0.5rem;
  margin-right: 0.5rem;
}

.puntaje-valor {
  font-weight: 600;
  font-size: 1.5rem;
  margin-top: 0.5rem;
  margin-left: 0.5rem;
  margin-right: 0.5rem;
}

.puntaje-nivel {
  font-weight: 700;
  font-size: 1rem;
  margin-top: 0.5rem;
  margin-left: 0.5rem;
  margin-right: 0.5rem;
}

.color-azul {
  background-color: #07BDEB; 
  color: white;
}

.color-verde {
  background-color: #28A832; 
  color: white;
}

.color-amarillo {
  background-color: #F5DC11; 
  color: white;
}

.color-naranja{
  background-color: #F28F05; 
  color: white;
}

.color-rojo {
  background-color: #C90E0E; 
  color: white;
}

.color-gris {
  background-color: #9ca3af; 
  color: white;
}
</style>

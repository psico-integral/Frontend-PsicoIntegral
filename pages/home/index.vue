<template>
  <SidebarLayout>
    <v-row class="d-flex justify-center" spacing="4">
      <!-- Tarjetas de Tabla -->
      <v-col cols="12" sm="4">
        <v-card class="custom-card" height="500px">
          <v-card-title>
            <h1 class="title-card">Guia II</h1>
          </v-card-title>
          <v-card-text style="max-height: 400px; overflow-y: auto;">
            <v-simple-table dense class="tabla-transparente">
              <thead>
                <tr>
                  <th>Nivel de riesgo</th>
                  <th class="text-left text-white font-weight-bold">Necesidad de acción</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td class="text-red-accent-2 font-weight-bold">Muy alto</td>
                  <td>
                    Se requiere realizar el análisis de cada categoría y dominio para establecer
                    las acciones de intervención apropiadas, mediante un Programa de
                    intervención que deberá incluir evaluaciones específicas1, y contemplar
                    campañas de sensibilización, revisar la política de prevención de riesgos
                    psicosociales y programas para la prevención de los factores de riesgo
                    psicosocial, la promoción de un entorno organizacional favorable y la
                    prevención de la violencia laboral, así como reforzar su aplicación y difusión.
                  </td>
                </tr>
                <tr>
                  <td class="text-orange-darken-2 font-weight-bold">Alto</td>
                  <td>
                    Se requiere realizar un análisis de cada categoría y dominio, de manera que
                    se puedan determinar las acciones de intervención apropiadas a través de un
                    Programa de intervención, que podrá incluir una evaluación específica1 y
                    deberá incluir una campaña de sensibilización, revisar la política de
                    prevención de riesgos psicosociales y programas para la prevención de los
                    factores de riesgo psicosocial, la promoción de un entorno organizacional
                    favorable y la prevención de la violencia laboral, así como reforzar su
                    aplicación y difusión.
                  </td>
                </tr>
                <tr>
                  <td class="text-yellow-darken-2 font-weight-bold">Medio</td>
                  <td>
                    Se requiere revisar la política de prevención de riesgos psicosociales y
                    programas para la prevención de los factores de riesgo psicosocial, la
                    promoción de un entorno organizacional favorable y la prevención de la
                    violencia laboral, así como reforzar su aplicación y difusión, mediante un
                    Programa de intervención.
                  </td>
                </tr>
                <tr>
                  <td class="text-green-darken-2 font-weight-bold">Bajo</td>
                  <td>
                    Es necesario una mayor difusión de la política de prevención de riesgos
                    psicosociales y programas para: la prevención de los factores de riesgo
                    psicosocial, la promoción de un entorno organizacional favorable y la
                    prevención de la violencia laboral.
                  </td>
                </tr>
                <tr>
                  <td class="text-white font-weight-bold">Nulo</td>
                  <td>
                    El riesgo resulta despreciable por lo que no se requiere medidas adicionales.
                  </td>
                </tr>
              </tbody>
            </v-simple-table>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col cols="12" sm="4">
        <v-card class="custom-card" height="500px">
          <v-card-title>
            <h1 class="title-card">Guia III</h1>
          </v-card-title>
          <v-card-text style="max-height: 400px; overflow-y: auto;">
            <v-simple-table dense class="tabla-transparente">
              <thead>
                <tr>
                  <th class="text-left text-white font-weight-bold">Nivel de riesgo</th>
                  <th class="text-left text-white font-weight-bold">Necesidad de acción</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td class="text-red-accent-2 font-weight-bold">Muy alto</td>
                  <td>
                    Se requiere realizar el análisis de cada categoría y dominio para establecer
                    las acciones de intervención apropiadas, mediante un Programa de
                    intervención que deberá incluir evaluaciones específicas1, y contemplar
                    campañas de sensibilización, revisar la política de prevención de riesgos
                    psicosociales y programas para la prevención de los factores de riesgo
                    psicosocial, la promoción de un entorno organizacional favorable y la
                    prevención de la violencia laboral, así como reforzar su aplicación y difusión.
                  </td>
                </tr>
                <tr>
                  <td class="text-orange-darken-2 font-weight-bold">Alto</td>
                  <td>
                    Se requiere realizar un análisis de cada categoría y dominio, de manera que
                    se puedan determinar las acciones de intervención apropiadas a través de un
                    Programa de intervención, que podrá incluir una evaluación específica1 y
                    deberá incluir una campaña de sensibilización, revisar la política de
                    prevención de riesgos psicosociales y programas para la prevención de los
                    factores de riesgo psicosocial, la promoción de un entorno organizacional
                    favorable y la prevención de la violencia laboral, así como reforzar su
                    aplicación y difusión.
                  </td>
                </tr>
                <tr>
                  <td class="text-yellow-darken-2 font-weight-bold">Medio</td>
                  <td>
                    Se requiere revisar la política de prevención de riesgos psicosociales y
                    programas para la prevención de los factores de riesgo psicosocial, la
                    promoción de un entorno organizacional favorable y la prevención de la
                    violencia laboral, así como reforzar su aplicación y difusión, mediante un
                    Programa de intervención.
                  </td>
                </tr>
                <tr>
                  <td class="text-green-darken-2 font-weight-bold">Bajo</td>
                  <td>
                    Es necesario una mayor difusión de la política de prevención de riesgos
                    psicosociales y programas para: la prevención de los factores de riesgo
                    psicosocial, la promoción de un entorno organizacional favorable y la
                    prevención de la violencia laboral.
                  </td>
                </tr>
                <tr>
                  <td class="text-white font-weight-bold">Nulo</td>
                  <td>
                    El riesgo resulta despreciable por lo que no se requiere medidas adicionales
                  </td>
                </tr>
              </tbody>
            </v-simple-table>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </SidebarLayout>
</template>

<script>
import SidebarLayout from '@/components/SidebarLayout.vue'

export default {
  components: {
    SidebarLayout
  },
  mounted() {
    const token = localStorage.getItem('token')
    if (!token) {
      this.$router.push('/')
    }
  }

}

</script>

<style scoped>
/* Personalizar la tarjeta */
.custom-card {
  background-color: #4040407e;
  border-radius: 16px;
  /* Bordes redondeados */
  box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.2);
  /* Sombra */
  color: white;
  /* Color de texto blanco */
  padding: 16px;
  /* Espaciado dentro de la tarjeta */
}

.title-card {
  color: white;
  font-size: 2rem;
  /* Tamaño de fuente más grande para el título */
  font-weight: bold;
  text-align: center;
}

/* Espaciado entre tarjetas */
.v-row {
  margin-top: 20px;
  margin-bottom: 20px;
}

.tabla-transparente {
  background-color: transparent !important;
  color: white;
}
.tabla-transparente th {
  background-color: transparent !important;
  border-bottom: none;
  color: white !important;
  font-size: 2rem; /* Aumenta el tamaño de fuente */
  font-weight: 700; /* Refuerza el grosor */
  padding: 12px 8px;
}

</style>

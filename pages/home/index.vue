<template>
  <SidebarLayout>
    <v-row class="d-flex justify-center" spacing="4">
      <!-- Tarjeta de Visión -->
      <v-col cols="12" sm="4">
        <v-card class="custom-card" height="500px">
          <v-card-title>
            <h1 class="title-card">
              Visión
            </h1>
          </v-card-title>
          <v-card-text>
            asdfjlñasjfñlsfj
          </v-card-text>
        </v-card>
      </v-col>

      <!-- Tarjeta de Misión -->
      <v-col cols="12" sm="4">
        <v-card class="custom-card" height="500px">
          <v-card-title>
            <h1 class="title-card">
              Misión
            </h1>
          </v-card-title>
          <v-card-text>
            asdjkfhsahfsa
          </v-card-text>
        </v-card>
      </v-col>

      <!-- Tarjeta de Valores -->
      <v-col cols="12" sm="4">
        <v-card class="custom-card" height="500px">
          <v-card-title>
            <h1 class="title-card">
              Valores
            </h1>
          </v-card-title>
          <v-card-text>
            hasdflasñhasdlaskhjfsa
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
  }
}
</script>

<style scoped>
/* Personalizar la tarjeta */
.custom-card {
  background-color: #5A1B86;
  border-radius: 16px;  /* Bordes redondeados */
  box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.2); /* Sombra */
  color: white; /* Color de texto blanco */
  padding: 16px; /* Espaciado dentro de la tarjeta */
}

.title-card {
  color: white;
  font-size: 2rem; /* Tamaño de fuente más grande para el título */
  font-weight: bold;
  text-align: center;
}

/* Espaciado entre tarjetas */
.v-row {
  margin-top: 20px;
  margin-bottom: 20px;
}
</style>

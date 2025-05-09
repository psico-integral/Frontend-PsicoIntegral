<template>
  <v-app dark>
    <!-- Barra superior -->
    <v-app-bar app :style="{ backgroundColor: appBarColor, height: '90px' }">
      <v-btn icon class="mr-5" @click="drawer = !drawer">
        <v-icon>mdi-menu</v-icon>
      </v-btn>
      <v-spacer />

      <!-- Imagen clickeable para créditos -->
      <div
        style="
          background-color: white;
          padding: 8px;
          border-radius: 8px;
          display: flex;
          align-items: center;
          height: 60px;
          cursor: pointer;
        "
        @click="dialogCreditos = true"
      >
        <img
          src="@/assets/logo.png"
          alt="Logo"
          style="max-height: 100%; max-width: 100px; object-fit: contain;"
        >
      </div>
    </v-app-bar>

    <!-- Menú lateral -->
    <v-navigation-drawer
      v-model="drawer"
      temporary
      fixed
      class="custom-drawer"
    >
      <v-list>
        <v-list-item
          v-for="item in items"
          :key="item.title"
          :to="item.to"
          router
        >
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>

        <v-spacer />

        <!-- Botón Salir -->
        <v-list-item @click="logout">
          <v-list-item-icon>
            <v-icon color="red">
              mdi-logout
            </v-icon>
          </v-list-item-icon>
          <v-list-item-title>Salir</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <!-- Contenido principal -->
    <v-main>
      <v-container fluid>
        <slot />
      </v-container>
    </v-main>

    <!-- Diálogo de créditos -->
    <v-dialog v-model="dialogCreditos" max-width="400">
      <v-card>
        <v-card-title class="headline grey lighten-2">
          Créditos
        </v-card-title>
        <v-card-text>
          Esta aplicación fue desarrollada por <strong>Javier Yépez Ramírez</strong> para <strong>Psico-Integral</strong> <strong>, junto con la app</strong>.
        </v-card-text>
        <v-card-actions>
          <v-spacer />
          <v-btn color="grey" text @click="dialogCreditos = false">
            Cerrar
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-app>
</template>

<script>
export default {
  data () {
    return {
      drawer: false,
      dialogCreditos: false,
      appBarColor: '#5A1B86',
      items: [
        { icon: 'mdi-home', title: 'Home', to: '/home' },
        { icon: 'mdi-domain', title: 'Empresa', to: '/empresa' },
        { icon: 'mdi-file', title: 'Encuestas', to: '/encuestas' }
      ]
    }
  },
  methods: {
    logout () {
      this.$router.push('/')
      console.log('Usuario cerró sesión')
      localStorage.removeItem('token')
    }
  }
}
</script>

<style scoped>
.custom-drawer {
  background-color: #5A1B86;
  color: white;
}

.v-img {
  max-width: 100px;
}

.custom-footer {
  background-color: #404404;
  color: #404040;
}
</style>

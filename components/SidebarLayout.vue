<template>
  <v-app dark>
    <v-app-bar app :style="{ backgroundColor: appBarColor, height: '90px' }">
      <v-btn icon class="mr-5" @click="drawer = !drawer">
        <v-icon>mdi-menu</v-icon>
      </v-btn>
      <v-spacer />

      <v-img
        :src="require('@/assets/logo.png')"
        max-width="95"
        class="ml-4"
      />
    </v-app-bar>

    <v-navigation-drawer
      v-model="drawer"
      temporary
      fixed
      class="custom-drawer"
    >
      <v-list>
        <v-list-item v-for="item in items" :key="item.title" :to="item.to" router>
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>

        <v-spacer />

        <v-list-item @click="logout">
          <v-list-item-icon>
            <v-icon>mdi-logout</v-icon>
          </v-list-item-icon>
          <v-list-item-title>Salir</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-main>
      <v-container fluid>
        <slot />
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
export default {
  data () {
    return {
      drawer: false,
      appBarColor: '#e6b2ff',
      items: [
        {
          icon: 'mdi-home',
          title: 'Home',
          to: '/home'
        },
        {
          icon: 'mdi-domain',
          title: 'Empresa',
          to: '/empresa'
        },
        {
          icon: 'mdi-file',
          title: 'Encuestas',
          to: '/encuestas'
        }
      ]
    }
  },
  methods: {
    logout () {
      this.$router.push('/')
      console.log('Usuario cerrado sesión')
      localStorage.removeItem('token')
    }
  }
}
</script>

<style scoped>
.custom-drawer {
  background-color: #5A1B86;
  color: #5A1B86;
}

.v-img {
  max-width: 100px;
}

.custom-footer {
  background-color: #5A1B86;
  color: #5A1B86;
}
</style>

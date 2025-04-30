<template>
  <v-app dark>
    <v-main>
      <v-container fluid>
        <Nuxt />
      </v-container>
    </v-main>
    <v-navigation-drawer
      v-model="rightDrawer"
      :right="right"
      temporary
      fixed
      class="custom-drawer"
    >
      <v-list>
        <v-list-item @click.native="right = !right">
          <v-list-item-action>
            <v-icon light>
              mdi-repeat
            </v-icon>
          </v-list-item-action>
          <v-list-item-title>Switch drawer (click me)</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-footer :absolute="!fixed" app class="custom-footer">
      <span>&copy; {{ new Date().getFullYear() }} Psico-Integral </span>
    </v-footer>
  </v-app>
</template>

<script>
export default {
  data () {
    return {
      clipped: false,
      drawer: false,
      fixed: false,
      items: [
        {
          icon: 'mdi-apps',
          title: 'Welcome',
          to: '/'
        },
        {
          icon: 'mdi-chart-bubble',
          title: 'Inspire',
          to: '/inspire'
        }
      ],
      miniVariant: false,
      right: true,
      rightDrawer: false,
      title: 'Vuetify.js'
    }
  }
}
</script>

<style scoped>
.custom-footer {
  background-color: #5A1B86; /* Color del pie de página */
  color: rgb(255, 255, 255); /* Color del texto del pie de página */
}

.v-application {
  background-image: url('@/assets/fondo.png');
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
  color: white;
}
.v-navigation-drawer {
  background-color: #135d73; /* Color del cajón de navegación */
}
</style>

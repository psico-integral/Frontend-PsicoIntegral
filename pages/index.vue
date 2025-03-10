<template>
  <v-container>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="4">
        <v-card class="pa-5" color="#5A1B86">
          <v-card-title class="tect-center">
            Iniciar sesión
          </v-card-title>
          <v-card-text>
            <v-form @submit.prevent="login">
              <v-text-field
                v-model="usuario"
                label="Usuario"
                outlined
                required
              />
              <v-text-field
                v-model="contrasena"
                lable="Contraseña"
                type="password"
                outlined
                required
              />
              <v-btn color="#9163CB" block @click="login">
                Ingresar
              </v-btn>
              <v-alert v-if="error" type="error" dismissible>
                {{ error }}
              </v-alert>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data () {
    return {
      usuario: '',
      contrasena: '',
      error: ''
    }
  },
  methods: {
    login () {
      this.$router.push('/home')
    }
  }
}
</script>

<style scoped>
</style>

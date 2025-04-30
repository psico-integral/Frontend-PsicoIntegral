<template>
  <v-container>
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="4">
        <v-card class="card pa-5" color="#4040407e">
          <div class="mb-4" style="display: flex; justify-content: center;">
            <div
              style="background-color: white; border-radius: 8px; padding: 10px; width: 150px; display: flex; justify-content: center;"
            >
              <v-img
                :src="require('@/assets/logo.png')"
                alt="Logo"
                max-width="120"
                contain
              />
            </div>
          </div>

          <v-card-title class="text-center">
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
                :type="mostrarContrasena ? 'text' : 'password'"
                label="Contraseña"
                outlined
                required
                :append-icon="mostrarContrasena ? 'mdi-eye-off' : 'mdi-eye'"
                @click:append="mostrarContrasena = !mostrarContrasena"
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
      mostrarContrasena: false,
      error: ''
    }
  },
  methods: {
    async login () {
      this.error = ''
      try {
        const response = await this.$axios.post('auth/login1', {
          usuario: this.usuario,
          contrasena: this.contrasena
        })
        localStorage.setItem('token', response.data.token)
        this.$router.push('/home')
      } catch (error) {
        this.error = 'Credenciales Invalidas'
      }
    }
  }
}
</script>

<style scoped>
</style>

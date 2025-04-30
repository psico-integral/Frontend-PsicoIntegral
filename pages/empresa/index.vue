<template>
  <SidebarLayout>
    <v-container>
      <v-row class="mb-4">
        <v-btn color="#9163CB" @click="openAddDialog">
          <v-icon left>
            mdi-plus
          </v-icon>
          Agregar Empresa
        </v-btn>
      </v-row>

      <v-data-table
        :headers="headers"
        :items="empresas"
        item-key="id"
        class="elevation-1"
        :items-per-page="30"
        :search="search"
      >
        <template #[`item.actions`]="{ item }">
          <v-icon small color="blue" @click="openEditDialog(item)">
            mdi-pencil
          </v-icon>
          <v-icon small color="red" @click="deleteEmpresa(item.id)">
            mdi-delete
          </v-icon>
        </template>
      </v-data-table>

      <v-dialog v-model="dialog" max-width="500px">
        <v-card color="#9163CB">
          <v-card-title>
            {{ isEdit ? 'Editar Empresa' : 'Agregar Empresa' }}
          </v-card-title>
          <v-card-text>
            <v-text-field
              v-model="empresaForm.nombre"
              label="Nombre de la empresa"
              required
            />
            <v-text-field
              v-model="empresaForm.usuario"
              label="Usuario"
              required
            />
            <v-text-field
              v-model="empresaForm.contrasena"
              label="Contraseña"
              type="password"
              required
            />
          </v-card-text>
          <v-card-actions>
            <v-btn color="red" @click="dialog = false">
              Cancelar
            </v-btn>
            <v-btn color="green" @click="isEdit ? editEmpresa() : addEmpresa()">
              Guardar
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-container>
  </SidebarLayout>
</template>

<script>
import SidebarLayout from '@/components/SidebarLayout.vue'

export default {
  components: {
    SidebarLayout
  },
  data () {
    return {
      empresas: [],
      search: '',
      headers: [
        { text: 'Nombre de la empresa', align: 'start', value: 'nombre', sortable: true },
        { text: 'Usuario', align: 'start', value: 'usuario', sortable: true },
        { text: 'Contraseña', align: 'start', value: 'contrasena', sortable: false },
        { text: 'Acciones', value: 'actions', sortable: false }
      ],
      empresaForm: {
        nombre: '',
        usuario: '',
        contrasena: ''
      },
      dialog: false,
      isEdit: false,
      currentEmpresaId: null
    }
  },
  created () {
    if (process.client) {
      const token = localStorage.getItem('token')
      if (!token) {
        this.$router.push('/')
        return
      }

      this.fetchEmpresas()
    }
  },
  methods: {
    openAddDialog () {
      this.isEdit = false
      this.empresaForm = { nombre: '', usuario: '', contrasena: '' }
      this.dialog = true
    },

    openEditDialog (item) {
      this.isEdit = true
      this.currentEmpresaId = item.id
      this.empresaForm = { ...item }
      this.dialog = true
    },
    async addEmpresa () {
      try {
        if (process.client) {
          const token = localStorage.getItem('token')
          if (!token) {
            this.$router.push('/')
            return
          }

          const response = await this.$axios.post('/auth/signup', this.empresaForm, {
            headers: {
              Authorization: `Bearer ${token}`
            }
          })

          if (response.data) {
            this.empresas.push(response.data)
          }

          this.fetchEmpresas()
          this.dialog = false
        }
      } catch (error) {
        console.error('Error al agregar empresa:', error)
      }
    },
    async editEmpresa () {
      if (!this.currentEmpresaId) { return }

      try {
        if (process.client) {
          const token = localStorage.getItem('token')
          if (!token) {
            this.$router.push('/')
            return
          }

          const response = await this.$axios.put(`/auth/empresas/${this.currentEmpresaId}`, this.empresaForm, {
            headers: {
              Authorization: `Bearer ${token}`
            }
          })

          const index = this.empresas.findIndex(e => e.id === this.currentEmpresaId)
          if (index !== -1) {
            this.$set(this.empresas, index, response.data)
          }

          this.fetchEmpresas()
          this.dialog = false
        }
      } catch (error) {
        console.error('Error al editar empresa:', error)
      }
    },
    async deleteEmpresa (id) {
      try {
        if (process.client) {
          const token = localStorage.getItem('token')
          if (!token) {
            this.$router.push('/')
            return
          }

          await this.$axios.delete(`/auth/empresas/${id}`, {
            headers: {
              Authorization: `Bearer ${token}`
            }
          })

          this.empresas = this.empresas.filter(e => e.id !== id)
          this.fetchEmpresas()
        }
      } catch (error) {
        console.error('Error al eliminar empresa:', error)
      }
    },
    async fetchEmpresas () {
      try {
        if (process.client) {
          const token = localStorage.getItem('token')
          if (!token) {
            this.$router.push('/')
            return
          }

          const response = await this.$axios.get('auth/empresas', {
            headers: {
              Authorization: `Bearer ${token}`
            }
          })

          if (response.data && typeof response.data === 'object') {
            this.empresas = Object.values(response.data).map((empresa, index) => ({
              ...empresa,
              id: Object.keys(response.data)[index]
            }))
          } else {
            console.error('El backend no devolvió un objeto:', response.data)
            this.empresas = []
          }
        }
      } catch (error) {
        console.error('Error al obtener empresas:', error)
        this.empresas = []
      }
    }
  }
}
</script>

<style scoped>

.v-data-table {
  background-color: #4040407e;
}

.v-data-table th {
  background-color: #2196F3;
  color: white;
}

.v-data-table td {
  text-align: left;
}

.v-btn {
  font-size: 14px;
}

.v-icon {
  cursor: pointer;
  margin-left: 10px;
}

.v-dialog {
  font-size: 16px;
}

.v-icon:hover {
  opacity: 0.7;
}

.v-card-title {
  background-color: #2196F3;
  color: white;
}

.v-card-actions {
  justify-content: flex-end;
}
</style>

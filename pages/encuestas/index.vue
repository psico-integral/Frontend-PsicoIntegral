<template>
  <SidebarLayout>
    <div class="p-6">
      <h2 class="text-2xl font-bold mb-6">
        Selecciona una Empresa
      </h2>

      <!-- Tarjetas de Empresas -->
      <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6 mb-8">
        <div
          v-for="(empleados, nombre) in empresas"
          :key="nombre"
          class="card-empresa max-w-sm mx-auto border rounded-xl p-4 cursor-pointer transition-all duration-300 transform hover:scale-105"
          :class="{
            'bg-blue-100 border-blue-400 ring-2 ring-blue-500': empresaSeleccionada === nombre,
            'hover:shadow-md hover:bg-blue-50': empresaSeleccionada !== nombre
          }"
          @click="toggleEmpresa(nombre)"
        >
          <div class="flex justify-between items-center mb-2">
            <h3 class="text-lg font-semibold text-gray-800">
              {{ nombre }}
            </h3>
          </div>
          <p class="text-sm text-gray-600">
            {{ Object.keys(empleados).length }} empleado(s)
          </p>
          <ul class="mt-4 space-y-2">
            <li
              class="flex items-center text-red-600 hover:text-red-800 cursor-pointer"
              @click.stop="eliminarEmpresa(nombre)"
            >
              <span class="mdi mdi-delete icono-eliminar mr-2" /> Eliminar
            </li>
          </ul>
        </div>
      </div>

      <!-- Selección de Empleado -->
      <div v-if="empresaSeleccionada && empleadosSeleccionados">
        <h3 class="text-xl font-semibold mb-4">
          Selecciona un Empleado
        </h3>
        <EmpleadosComponent
          :empleados="empresas[empresaSeleccionada]"
          :empresa="empresaSeleccionada"
          @empleadoSeleccionado="verRespuestas"
        />
      </div>

      <!-- Mostrar Respuestas de un Empleado -->
      <div v-if="empleadoSeleccionado && respuestasTabla && Object.keys(respuestasTabla).length">
        <h3 class="text-xl font-semibold mt-6">
          Respuestas de {{ empleadoSeleccionado }}
        </h3>
        <div class="flex space-x-4 mb-4">
          <v-button
            class="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600 transition"
            @click="exportarPDF"
          >
            Exportar a PDF 📄
          </v-button>
        </div>
        <div
          v-for="(items, cuestionario) in respuestasTabla"
          :key="cuestionario"
          class="mb-6"
        >
          <h4 class="text-lg font-bold mb-2">
            {{ cuestionario.replace('_', ' ').toUpperCase() }}
          </h4>
          <div class="bg-gray-100 p-4 rounded overflow-x-auto">
            <table class="min-w-full table-auto bg-white border border-gray-300 rounded shadow-sm">
              <thead class="bg-gray-200">
                <tr>
                  <th class="border px-4 py-2 text-left text-sm font-semibold text-gray-700">
                    Pregunta
                  </th>
                  <th class="border px-4 py-2 text-left text-sm font-semibold text-gray-700">
                    Respuesta
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(item, index) in items" :key="index" class="hover:bg-gray-50">
                  <td class="border px-4 py-2 text-sm text-gray-800">
                    {{ item.pregunta }}
                  </td>
                  <td class="border px-4 py-2 text-sm text-gray-800">
                    {{ item.respuesta }}
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </SidebarLayout>
</template>

<script>
import { jsPDF } from 'jspdf'
import autoTable from 'jspdf-autotable'
import EmpleadosComponent from '@/components/EmpleadosComponent.vue'
import SidebarLayout from '@/components/SidebarLayout.vue'

export default {
  components: {
    SidebarLayout,
    EmpleadosComponent
  },
  data () {
    return {
      empresas: {},
      empresaSeleccionada: '',
      respuestasEmpleado: null,
      respuestasTabla: {},
      empleadoSeleccionado: '',
      empleadosSeleccionados: false,
      preguntasCuestionarios: {
        cuestionario_01: {
          seccion_1: {
            '01': '¿Accidente que tenga como consecuencia la muerte, la pérdida de un miembro o una lesión grave?',
            '02': '¿Asaltos?',
            '03': '¿Actos violentos que derivaron en lesiones graves?',
            '04': '¿Secuestro?',
            '05': '¿Amenazas?',
            '06': '¿Cualquier otro que ponga en riesgo su vida o salud, y/o la de otras personas?'
          },
          seccion_2: {
            '01': '¿Ha tenido recuerdos recurrentes sobre el acontecimiento que le provocan malestares?',
            '02': '¿Ha tenido sueños de carácter recurrente sobre el acontecimiento, que le producen malestar?'
          },
          seccion_3: {
            '01': '¿Se ha esforzado por evitar todo tipo de sentimientos, conversaciones o situaciones que le puedan recordar el acontecimiento?',
            '02': '¿Ha tenido sueños de carácter recurrente sobre el acontecimiento, que le producen malestar?',
            '03': '¿Ha tenido dificultad para recordar alguna parte importante del evento?',
            '04': '¿Ha disminuido su interés en sus actividades cotidianas?',
            '05': '¿Se ha sentido usted alejado o distante de los demás?',
            '06': '¿Ha notado que tiene dificultad para expresar sus sentimientos?',
            '07': '¿Ha tenido la impresión de que su vida se va a acortar, que va a morir antes que otras personas o que tiene un futuro limitado?'
          },
          seccion_4: {
            '01': '¿Ha tenido usted dificultades para dormir?',
            '02': '¿Ha estado particularmente irritable o le han dado arranques de coraje?',
            '03': '¿Ha tenido dificultad para concentrarse?',
            '04': '¿Ha estado nervioso o constantemente en alerta?',
            '05': '¿Se ha sobresaltado fácilmente por cualquier cosa?'
          }
        },
        cuestionario_02: {
          seccion_1: {
            '01': 'Mi trabajo me exige hacer mucho esfuerzo físico',
            '02': 'Me preocupa sufrir un accidente en mi trabajo',
            '03': 'Considero que las actividades que realizo son peligrosas',
            '04': 'Por la cantidad de trabajo que tengo debo darme tiempo adicional a mi turno',
            '05': 'Por la cantidad de trabajo que tengo debo trabajar sin parar',
            '06': 'Considero que es necesario mantener un ritmo de trabajo acelerado',
            '07': 'Mi trabajo exige que esté muy concentrado',
            '08': 'Mi trabajo requiere que memorice mucha información',
            '09': 'Mi trabajo exige que atienda varios asuntos al mismo tiempo',
            10: 'En mi trabajo soy responsable de cosas de mucho valor',
            11: 'Respondo ante mi jefe por los resultados de toda mi área de trabajo',
            12: 'En mi trabajo me dan órdenes contradictorias',
            13: 'Considero que en mi trabajo me piden hacer cosas innecesarias',
            14: 'Trabajo horas extras más de tres veces a la semana',
            15: 'Mi trabajo me exige laborar en días de descanso, festivos o fines de semana',
            16: 'Considero que el tiempo en el trabajo es mucho y perjudica mis actividades familiares o personales',
            17: 'Pienso en las actividades familiares o personales cuando estoy en mi trabajo',
            18: 'Mi trabajo permite que desarrolle nuevas habilidades',
            19: 'En mi trabajo puedo aspirar a un mejor puesto',
            20: 'Durante mi jornada de trabajo puedo tomar pausas cuando las necesito',
            21: 'Puedo decidir la velocidad a la que realizo mis actividades en mi trabajo',
            22: 'Puedo cambiar el orden de las actividades que realizo en mi trabajo',
            23: 'Me informan con claridad cuáles son mis funciones',
            24: 'Me explican claramente los resultados que debo obtener en mi trabajo',
            25: 'Me informan con quién puedo resolver problemas o asuntos de trabajo',
            26: 'Me permiten asistir a capacitaciones relacionadas con mi trabajo',
            27: 'Recibo capacitación útil para hacer mi trabajo',
            28: 'Mi jefe tiene en cuenta mis puntos de vista y opiniones',
            29: 'Mi jefe ayuda a solucionar los problemas que se presentan en el trabajo',
            30: 'Puedo confiar en mis compañeros de trabajo',
            31: 'Cuando tenemos que realizar trabajo de equipo los compañeros colaboran',
            32: 'Mis compañeros de trabajo me ayudan cuando tengo dificultades',
            33: 'En mi trabajo puedo expresarme libremente sin interrupciones',
            34: 'Recibo críticas constantes a mi persona y/o trabajo',
            35: 'Recibo burlas, calumnias, difamaciones, humillaciones o ridiculizaciones',
            36: 'Se ignora mi presencia o se me excluye de las reuniones de trabajo y en la toma de decisiones',
            37: 'Se manipulan las situaciones de trabajo para hacerme parecer un mal trabajador',
            38: 'Se ignoran mis éxitos laborales y se atribuyen a otros trabajadores',
            39: 'Me bloquean o impiden las oportunidades que tengo para obtener ascenso o mejora en mi trabajo',
            40: 'He presenciado actos de violencia en mi centro de trabajo'
          },
          seccion_2: {
            '01': 'En mi trabajo debo brindar servicio a clientes o usuarios'
          },
          seccion_3: {
            41: 'Atiendo clientes o usuarios muy enojados',
            42: 'Mi trabajo me exige atender personas muy necesitadas de ayuda o enfermas',
            43: 'Para hacer mi trabajo debo demostrar sentimientos distintos a los míos'
          },
          seccion_4: {
            '01': 'Soy jefe de otros trabajadores"'
          },
          seccion_5: {
            44: 'Comunican tarde los asuntos de trabajo',
            45: 'Dificultan el logro de los resultados del trabajo',
            46: 'Ignoran las sugerencias para mejorar su trabajo'
          }
        },
        cuestionario_03: {
          seccion_1: {
            '01': 'El espacio donde trabajo me permite realizar mis actividades de manera segura e higiénica',
            '02': 'Mi trabajo me exige hacer mucho esfuerzo físico',
            '03': 'Me preocupa sufrir un accidente en mi trabajo',
            '04': 'Considero que en mi trabajo se aplican las normas de seguridad y salud en el trabajo',
            '05': 'Considero que las actividades que realizo son peligrosas',
            '06': 'Por la cantidad de trabajo que tengo debo quedarme tiempo adicional a mi turno',
            '07': 'Por la cantidad de trabajo que tengo debo trabajar sin parar',
            '08': 'Considero que es necesario mantener un ritmo de trabajo acelerado',
            '09': 'Mi trabajo exige que esté muy concentrado',
            10: 'Mi trabajo requiere que memorice mucha información',
            11: 'En mi trabajo tengo que tomar decisiones difíciles muy rápido',
            12: 'Mi trabajo exige que atienda varios asuntos al mismo tiempo',
            13: 'En mi trabajo soy responsable de cosas de mucho valor',
            14: 'Respondo ante mi jefe por los resultados de toda mi área de trabajo',
            15: 'En el trabajo me dan órdenes contradictorias',
            16: 'Considero que en mi trabajo me piden hacer cosas innecesarias',
            17: 'Trabajo horas extras más de tres veces a la semana',
            18: 'Mi trabajo me exige laborar en días de descanso, festivos o fines de semana',
            19: 'Considero que el tiempo en el trabajo es mucho y perjudica mis actividades familiares o personales',
            20: 'Debo atender asuntos de trabajo cuando estoy en casa',
            21: 'Pienso en las actividades familiares o personales cuando estoy en mi trabajo',
            22: 'Pienso que mis responsabilidades familiares afectan mi trabajo',
            23: 'Mi trabajo permite que desarrolle nuevas habilidades',
            24: 'En mi trabajo puedo aspirar a un mejor puesto',
            25: 'Durante mi jornada de trabajo puedo tomar pausas cuando las necesito',
            26: 'Puedo decidir cuánto trabajo realizo durante la jornada laboral',
            27: 'Puedo decidir la velocidad a la que realizo mis actividades en mi trabajo',
            28: 'Puedo cambiar el orden de las actividades que realizo en mi trabajo',
            29: 'Los cambios que se presentan en mi trabajo dificultan mi labor',
            30: 'Cuando se presentan cambios en mi trabajo se tienen en cuenta mis ideas o aportaciones',
            31: 'Me informan con claridad cuáles son mis funciones',
            32: 'Me explican claramente los resultados que debo obtener en mi trabajo',
            33: 'Me explican claramente los objetivos de mi trabajo',
            34: 'Me informan con quién puedo resolver problemas o asuntos de trabajo',
            35: 'Me permiten asistir a capacitaciones relacionadas con mi trabajo',
            36: 'Recibo capacitación útil para hacer mi trabajo',
            37: 'Mi jefe ayuda a organizar mejor el trabajo',
            38: 'Mi jefe tiene en cuenta mis puntos de vista y opiniones',
            39: 'Mi jefe me comunica a tiempo la información relacionada con el trabajo',
            40: 'La orientación que me da mi jefe me ayuda a realizar mejor mi trabajo',
            41: 'Mi jefe ayuda a solucionar los problemas que se presentan en el trabajo',
            42: 'Puedo confiar en mis compañeros de trabajo',
            43: 'Entre compañeros solucionamos los problemas de trabajo de forma respetuosa',
            44: 'En mi trabajo me hacen sentir parte del grupo',
            45: 'Cuando tenemos que realizar trabajo de equipo los compañeros colaboran',
            46: 'Mis compañeros de trabajo me ayudan cuando tengo dificultades',
            47: 'Me informan sobre lo que hago bien en mi trabajo',
            48: 'La forma como evalúan mi trabajo en mi centro de trabajo me ayuda a mejorar mi desempeño',
            49: 'En mi centro de trabajo me pagan a tiempo mi salario',
            50: 'El pago que recibo es el que merezco por el trabajo que realizo',
            51: 'Si obtengo los resultados esperados en mi trabajo me recompensan o reconocen',
            52: 'Las personas que hacen bien el trabajo pueden crecer laboralmente',
            53: 'Considero que mi trabajo es estable',
            54: 'En mi trabajo existe continua rotación de personal',
            55: 'Siento orgullo de laborar en este centro de trabajo',
            56: 'Me siento comprometido con mi trabajo',
            57: 'En mi trabajo puedo expresarme libremente sin interrupciones',
            58: 'Recibo críticas constantes a mi persona y/o trabajo',
            59: 'Recibo burlas, calumnias, difamaciones, humillaciones o ridiculizaciones',
            60: 'Se ignora mi presencia o se me excluye de las reuniones de trabajo y en la toma de decisiones',
            61: 'Se manipulan las situaciones de trabajo para hacerme parecer un mal trabajador',
            62: 'Se ignoran mis éxitos laborales y se atribuyen a otros trabajadores',
            63: 'Me bloquean o impiden las oportunidades que tengo para obtener ascenso o mejora en mi trabajo',
            64: 'He presenciado actos de violencia en mi centro de trabajo'
          }
        }
      }
    }
  },
  computed: {
    preguntasPorId () {
      const flat = {}
      for (const cuestionario in this.preguntasCuestionarios) {
        for (const seccion in this.preguntasCuestionarios[cuestionario]) {
          for (const idPregunta in this.preguntasCuestionarios[cuestionario][seccion]) {
            const id = `${cuestionario}/${seccion}/${idPregunta}`
            flat[id] = this.preguntasCuestionarios[cuestionario][seccion][idPregunta]
          }
        }
      }
      return flat
    }
  },
  mounted () {
    this.cargarEmpresas()
  },
  methods: {
    convertImageToBase64 (src, callback) {
      const img = new Image()
      img.crossOrigin = 'anonymous'
      img.onload = () => {
        const canvas = document.createElement('canvas')
        canvas.width = img.width
        canvas.height = img.height
        canvas.getContext('2d').drawImage(img, 0, 0)
        const dataURL = canvas.toDataURL('@/assets/logo.png')
        callback(dataURL)
      }
      img.src = src
    },
    exportarPDF () {
      if (!this.empresaSeleccionada || !this.empleadoSeleccionado) {
        alert('Falta seleccionar empresa o empleado')
        return
      }

      this.convertImageToBase64(require('@/assets/logo.png'), (logoBase64) => {
        // eslint-disable-next-line new-cap
        const doc = new jsPDF()

        doc.addImage(logoBase64, 'PNG', 14, 10, 30, 15)

        doc.setFontSize(16)
        doc.text(`Empresa: ${this.empresaSeleccionada}`, 50, 20)
        doc.text(`Empleado: ${this.empleadoSeleccionado}`, 50, 30)

        let y = 40

        Object.entries(this.respuestasTabla).forEach(([cuestionario, items]) => {
          const preguntas = this.preguntasCuestionarios[cuestionario]
          if (!preguntas) { return }

          doc.setFontSize(14)
          doc.text(cuestionario.replace('_', ' ').toUpperCase(), 14, y)
          y += 6

          const tableData = items.map(({ pregunta, respuesta }) => [
            pregunta,
            respuesta
          ])

          autoTable(doc, {
            head: [['Pregunta', 'Respuesta']],
            body: tableData,
            startY: y,
            margin: { left: 14, right: 14 },
            styles: { fontSize: 10, cellPadding: 3 },
            headStyles: { fillColor: [230, 230, 230] },
            theme: 'grid',
            didDrawPage: (data) => {
              y = data.cursor.y + 10
            }
          })
        })

        doc.save(`${this.empresaSeleccionada}_${this.empleadoSeleccionado}.pdf`)
      })
    },
    async cargarEmpresas () {
      try {
        const res = await this.$axios.get('/respuestas')
        this.empresas = res.data || {}
      } catch (error) {
        console.error('Error al cargar empresas:', error)
      }
    },
    toggleEmpresa (nombre) {
      this.empresaSeleccionada = this.empresaSeleccionada === nombre ? '' : nombre
      this.empleadosSeleccionados = !!this.empresaSeleccionada
    },
    async verRespuestas ({ nombreEmpleado }) {
      this.empleadoSeleccionado = nombreEmpleado
      this.respuestasTabla = {}

      try {
        const res = await this.$axios.get(`/respuestas/${this.empresaSeleccionada}/${nombreEmpleado}`)
        console.log('Datos recibidos:', res.data)
        const respuestas = res.data?.respuestas || {}

        for (const cuestionario in respuestas) {
          const secciones = respuestas[cuestionario]
          const preguntasCuestionario = this.preguntasCuestionarios[cuestionario] || {}
          const respuestasLista = []

          for (const seccion in secciones) {
            const preguntas = secciones[seccion]
            const preguntasSeccion = preguntasCuestionario[seccion] || {}

            for (const idPregunta in preguntas) {
              const valor = preguntas[idPregunta]
              const textoPregunta = preguntasSeccion[idPregunta] || `Pregunta ${idPregunta}`
              const textoRespuesta = this.obtenerTextoRespuesta(valor)

              respuestasLista.push({
                pregunta: textoPregunta,
                respuesta: textoRespuesta
              })
            }
          }
          console.log('Respuestas generadas para tabla:', this.respuestasTabla)

          this.$set(this.respuestasTabla, cuestionario, respuestasLista)
          console.log('Respuestas del cuestionario:', this.respuestasTabla[cuestionario]) // Añadir un log para verificar
        }
      } catch (error) {
        console.error('Error al cargar respuestas:', error)
      }
    },
    async eliminarEmpresa (nombre) {
      if (confirm(`¿Seguro que deseas eliminar la empresa "${nombre}"?`)) {
        try {
          await this.$axios.delete(`/respuestas/${nombre}`)
          this.empresaSeleccionada = ''
          this.respuestasEmpleado = null
          this.respuestasTabla = []
          this.empleadoSeleccionado = ''
          this.cargarEmpresas()
        } catch (error) {
          console.error('Error al eliminar empresa:', error)
        }
      }
    },
    obtenerTextoRespuesta (valor) {
      const respuestas = {
        0: '---',
        1: 'Sí',
        2: 'No',
        3: 'Nunca',
        4: 'Rara vez',
        5: 'Algunas veces',
        6: 'Frecuentemente',
        7: 'Siempre'
      }
      return respuestas[valor] !== undefined ? respuestas[valor] : `Respuesta inválida (${valor})`
    }
  }
}
</script>

<style scoped>
/* Estilo CSS personalizado */
.icono-eliminar {
  font-size: 2rem;
}

/* Estilos para las tarjetas de Empresa */
.card-empresa {
  transition: all 0.3s ease;
  padding: 1rem;
  border-radius: 1rem;
  cursor: pointer;
  border: 1px solid #e2e8f0;
  max-width: 20rem;
  width: 100%;
  margin: 0 auto;
}

.card-empresa:hover {
  transform: scale(1.05);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.card-empresa.selected {
  background-color: #ebf8ff;
  border-color: #4299e1;
  box-shadow: 0 4px 10px rgba(66, 153, 225, 0.3);
}

.card-empresa ul {
  list-style-type: none;
  padding: 0;
}

.card-empresa li {
  font-size: 1rem;
  cursor: pointer;
  color: #e53e3e;
  transition: color 0.3s ease;
  display: flex;
  align-items: center;
}

.card-empresa li:hover {
  color: #c53030;
}

.card-empresa li i {
  margin-right: 8px;
}

/* Estilos para los items de empleados */
.card-empleado {
  display: inline-block;
  background-color: #edf2f7;
  padding: 1rem;
  border-radius: 0.75rem;
  margin: 0.5rem;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
  max-width: 18rem;
  width: 100%;
}

.card-empleado:hover {
  background-color: #e2e8f0;
  transform: scale(1.05);
}

/* Botón de Eliminar Respuestas */
.btn-eliminar-respuestas {
  background-color: #e53e3e;
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 0.75rem;
  transition: background-color 0.3s ease;
}

.btn-eliminar-respuestas:hover {
  background-color: #c53030;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

/* Sombra suave para tarjetas */
.card-empresa,
.card-empleado {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

/* Tabla */
table {
  border-collapse: collapse;
  width: 100%;
}

th, td {
  border: 1px solid #d1d5db;
  padding: 0.75rem;
}

thead {
  background-color: #e5e7eb;
}

tbody tr:hover {
  background-color: #f9fafb;
}
</style>

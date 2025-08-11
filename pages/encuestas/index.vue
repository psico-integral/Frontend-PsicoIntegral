<template>
  <SidebarLayout>
    <div class="container mx-auto p-8">
      <!-- Título Principal -->
      <h2 class="text-4xl font-semibold text-gray-800 mb-8">
        Selecciona una Empresa
      </h2>

      <!-- Tarjetas de Empresas -->
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
        <div
          v-for="(empleados, nombre) in empresas"
          :key="nombre"
          class="empresa-card p-6 rounded-lg shadow-lg hover:shadow-xl transition-all duration-300 ease-in-out transform hover:scale-105 cursor-pointer"
          :class="{
            'bg-blue-600 text-white': empresaSeleccionada === nombre,
            'bg-white text-gray-900': empresaSeleccionada !== nombre
          }"
          @click="toggleEmpresa(nombre)"
        >
          <div class="flex justify-between items-center">
            <h3 class="text-xl font-semibold">
              {{ nombre }}
            </h3>
            <span class="text-sm font-medium text-gray-600">{{ Object.keys(empleados).length }} empleados</span>
          </div>
          <div class="mt-4">
            <p class="text-sm text-gray-600">
              <span class="font-semibold">Número de empleados: ({{ Object.keys(empleados).length }})</span>
            </p>
            <ul class="mt-4 space-y-2">
              <v-btn
                color="red"
                class="text-red-600 hover:text-red-700 cursor-pointer transition-colors"
                @click.stop="eliminarEmpresa(nombre)"
              >
                <i class="mdi mdi-delete-outline mr-2" /> Eliminar Respuestas De Empleados
              </v-btn>
              <v-btn
                color="#4CAF50"
                class="text-red-600 hover:text-red-700 cursor-pointer transition-colors"
                @click="exportarTodoPDF"
              >
                <i class="mdi mdi-download" /> Descargar todas las respuestas
              </v-btn>
            </ul>
          </div>
        </div>
      </div>

      <!-- Selección de Empleado -->
      <div v-if="empresaSeleccionada && empleadosSeleccionados">
        <h3 class="text-3xl font-semibold mt-12 text-gray-800">
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
        <h3 class="text-3xl font-semibold mt-12 text-gray-800">
          Respuestas de {{ empleadoSeleccionado }}
        </h3>
        <div class="mt-6 flex justify-start space-x-6">
          <v-btn
            color="#9163CB"
            @click="exportarPDF"
          >
            Exportar a PDF 📄
          </v-btn>
        </div>
        <div
          v-for="(items, cuestionario) in respuestasTabla"
          :key="cuestionario"
          class="mt-8"
        >
          <h4 class="text-3xl font-semibold text-gray-900 mb-4">
            {{ cuestionario.replace('_', ' ').toUpperCase() }}
          </h4>
          <h4 class="text-2xl font-semibold text-gray-900 mb-4">
            Puntaje total: {{ sumatoriasPorCuestionario[cuestionario] }}
          </h4>

          <RespuestasTabla :items="items" />

          <SumatoriasCategorias
            v-if="sumatoriasPorCategoria[cuestionario]"
            :sumatorias="sumatoriasPorCategoria[cuestionario]"
            :cuestionario="parseInt(cuestionario.split('_')[1])"
            class="mb-6"
          />

          <SumatoriasDominios
            v-if="sumatoriasPorDominio[cuestionario]"
            :sumatorias="sumatoriasPorDominio[cuestionario]"
            :cuestionario="parseInt(cuestionario.split('_')[1])"
            class="mb-6 mt-8"
          />
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
import RespuestasTabla from '@/components/RespuestasTabla.vue'
import SumatoriasCategorias from '@/components/SumatoriasCategorias.vue'
import SumatoriasDominios from '@/components/SumatoriasDominios.vue'

export default {
  components: {
    SidebarLayout,
    RespuestasTabla,
    SumatoriasCategorias,
    SumatoriasDominios,
    EmpleadosComponent
  },
  data () {
    return {
      empresas: {},
      empresaSeleccionada: '',
      respuestasEmpleado: null,
      respuestasTabla: {},
      empleadoSeleccionado: '',
      sumatoriasPorCuestionario: {},
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
            '01': 'Soy jefe de otros trabajadores'
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
          },
          seccion_2: {
            '01': 'En mi trabajo debo brindar servicio a clientes o usuarios'
          },
          seccion_3: {
            65: 'Atiendo clientes o usuarios muy enojados',
            66: 'Mi trabajo me exige atender personas muy necesitadas de ayuda o enfermas',
            67: 'Para hacer mi trabajo debo demostrar sentimientos distintos a los míos',
            68: 'Mi trabajo me exige atender situaciones de violencia'
          },
          seccion_4: {
            '01': 'Soy jefe de otros trabajadores'
          },
          seccion_5: {
            69: 'Comunican tarde los asuntos de trabajo',
            70: 'Dificultan el logro de los resultados del trabajo',
            71: 'Cooperan poco cuando se necesita',
            72: 'Ignoran las sugerencias para mejorar su trabajo'
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
    const token = localStorage.getItem('token')
    if (!token) {
      this.$router.push('/')
      return
    }

    this.cargarEmpresas()
  },
  methods: {
    exportarTodoPDF () {
      // eslint-disable-next-line new-cap
      const doc = new jsPDF()
      let y = 10

      // Insertar logo
      const img = new Image()
      img.src = require('@/assets/logo.png')
      doc.addImage(img, 'PNG', 10, 10, 30, 30)
      y = 45

      doc.setFontSize(18)
      doc.text('Reporte de Respuestas por Empresa', 50, 30)

      for (const [nombreEmpresa, empleados] of Object.entries(this.empresas)) {
        if (y > 250) {
          doc.addPage()
          y = 10
        }

        doc.setFontSize(16)
        doc.text(`Empresa: ${nombreEmpresa}`, 10, y)
        y += 10

        for (const [nombreEmpleado, datosEmpleado] of Object.entries(empleados)) {
          if (y > 250) {
            doc.addPage()
            y = 10
          }

          doc.setFontSize(14)
          doc.text(`Empleado: ${nombreEmpleado}`, 15, y)
          y += 8

          const respuestas = datosEmpleado.respuestas

          if (!respuestas || Object.keys(respuestas).length === 0) {
            doc.setFontSize(10)
            doc.text('No hay respuestas.', 20, y)
            y += 10
            continue
          }

          for (const [cuestionario, secciones] of Object.entries(respuestas)) {
            doc.setFontSize(12)
            doc.text(`Cuestionario: ${cuestionario}`, 20, y)
            y += 6

            const rows = []

            for (const [seccion, preguntas] of Object.entries(secciones)) {
              for (const [numeroPregunta, valorRespuesta] of Object.entries(preguntas)) {
                const textoPregunta = this.preguntasCuestionarios?.[cuestionario]?.[seccion]?.[numeroPregunta] || `Sección ${seccion} - Pregunta ${numeroPregunta}`
                const textoRespuesta = this.obtenerTextoRespuesta(valorRespuesta)

                rows.push([
                  textoPregunta,
                  textoRespuesta
                ])
              }
            }

            if (rows.length > 0) {
              autoTable(doc, {
                head: [['Pregunta', 'Respuesta']],
                body: rows,
                startY: y,
                margin: { horizontal: 15 },
                styles: { fontSize: 8 },
                theme: 'striped',
                headStyles: { fillColor: [0, 102, 204] }
              })

              y = doc.lastAutoTable.finalY + 10
            } else {
              doc.setFontSize(10)
              doc.text('No hay respuestas en este cuestionario.', 20, y)
              y += 10
            }
          }
        }
      }

      doc.save('respuestas_todas_empresas.pdf')
    },

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

          const tableData = items.map(({ pregunta, respuesta }, index) => [
            index + 1,
            pregunta,
            respuesta
          ])

          autoTable(doc, {
            head: [['No. Pregunta', 'Pregunta', 'Respuesta']],
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
        const token = localStorage.getItem('token')
        if (!token) {
          this.$router.push('/')
          return
        }

        const res = await this.$axios.get('/respuestas', {
          headers: {
            Authorization: `Bearer ${token}`
          }
        })

        this.empresas = res.data || {}
      } catch (error) {
        console.error('Error al cargar empresas:', error)
      }
    },
    toggleEmpresa (nombre) {
      this.empresaSeleccionada = this.empresaSeleccionada === nombre ? '' : nombre
      this.empleadosSeleccionados = !!this.empresaSeleccionada
    },
    obtenerValorPonderado (cuestionario, numeroPregunta, valorCrudo) {
      if (valorCrudo === 0) { return '---' }

      const tipoBC2 = [18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33]
      const tipoAC2 = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46]

      const tipoBC3 = [1, 4, 23, 24, 25, 26, 27, 28, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 55, 56, 57]
      const tipoAC3 = [2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 29, 54, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72]

      const mapaDirecto = {
        0: 0,
        1: 0,
        2: 0,
        3: 0,
        4: 1,
        5: 2,
        6: 3,
        7: 4
      }

      const mapaInvertido = {
        7: 0,
        6: 1,
        5: 2,
        4: 3,
        3: 4,
        2: 0,
        1: 0,
        0: 0,
      }

      if (cuestionario === 'cuestionario_01' && 'cuestionario_02' && 'cuestionario_03') {
        const mapa = {
          1: 0,
          2: 0
        }
        return mapa[valorCrudo] ?? '---'
      }

      if (cuestionario === 'cuestionario_02') {
        if (tipoBC2.includes(numeroPregunta)) { return mapaInvertido[valorCrudo] ?? '---' }
        if (tipoAC2.includes(numeroPregunta)) { return mapaDirecto[valorCrudo] ?? '---' }
      }

      if (cuestionario === 'cuestionario_03') {
        if (tipoBC3.includes(numeroPregunta)) { return mapaInvertido[valorCrudo] ?? '---' }
        if (tipoAC3.includes(numeroPregunta)) { return mapaDirecto[valorCrudo] ?? '---' }
      }

      return valorCrudo
    },
    async verRespuestas ({ nombreEmpleado }) {
      this.empleadoSeleccionado = nombreEmpleado
      this.respuestasTabla = {}
      this.sumatoriasPorCuestionario = {}
      this.sumatoriasPorCategoria = {}
      this.sumatoriasPorDominio = {}

      try {
        const token = localStorage.getItem('token')
        if (!token) {
          this.$router.push('/')
          return
        }

        const res = await this.$axios.get(
      `/respuestas/${this.empresaSeleccionada}/${nombreEmpleado}`,
      {
        headers: {
          Authorization: `Bearer ${token}`
        }
      }
        )

        const respuestas = res.data?.respuestas || {}

        for (const cuestionario in respuestas) {
          const secciones = respuestas[cuestionario]
          const preguntasCuestionario = this.preguntasCuestionarios[cuestionario] || {}
          const respuestasLista = []

          let sumaPonderada = 0

          // Inicializa sumatoria por categoría si es cuestionario 2
          const sumatoriaPorCategoria = {}
          const sumatoriaPorDominio = {}

          const categoriasC2 = {
            'Ambiente de trabajo': [1, 2, 3],
            'Factores propios de la actividad': [4, 5, 6, 7, 8, 9, 41, 42, 43],
            'Organización del tiempo de trabajo': [14, 15, 16, 17],
            'Liderazgo y relaciones en el trabajo': [23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 44, 45, 46]
          }
          const dominiosC2 = {
            'Condiciones en el ambiente de trabajo': [1, 2, 3],
            'Carga de trabajo': [4, 5, 6, 7, 8, 9, 41, 42, 43],
            'Falta de control sobre el trabajo': [18, 19, 20, 21, 22, 26, 27],
            'Jornada de trabajo': [14, 15],
            'Interferencia en la relación trabajo-familia': [16, 17],
            Liderazgo: [23, 24, 25, 28, 29],
            'Relaciones en el trabajo': [30, 31, 32, 44, 45, 46],
            Violencia: [33, 34, 35, 36, 37, 38, 39, 40]
          }
          const categoriasC3 = {
            'Ambiente de trabajo': [1, 3],
            'Factores propios de la actividad': [6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 23, 24, 25, 26, 27, 28, 29, 30, 35, 36, 65, 66, 67, 68],
            'Organización del tiempo de trabajo': [17, 18, 19, 20, 21, 22],
            'Liderazgo y relaciones en el trabajo': [31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 57, 58, 59, 60, 61, 62, 63, 64, 69, 70, 71, 72],
            'Entorno organizacional': [47, 48, 49, 50, 51, 52, 53, 54, 55, 56]
          }
          const dominiosC3 = {
            'Condiciones en el ambiente de trabajo': [1, 3],
            'Carga de trabajo': [6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 65, 66, 67, 68],
            'Falta de control sobre el trabajo': [23, 24, 25, 26, 27, 28, 29, 30, 35, 36],
            'Jornada de trabajo': [17, 18],
            'Interferencia en la relación trabajo-familia': [19, 20, 21, 22],
            Liderazgo: [31, 32, 33, 34, 37, 38, 39, 40, 41],
            'Relaciones en el trabajo': [42, 43, 44, 45, 46, 69, 70, 71],
            Violencia: [57, 58, 59, 60, 61, 62, 63, 64],
            'Reconocimiento del desempeño': [47, 48, 49, 50, 51, 52],
            'Insuficiente sentido de pertenencia e, inestabilidad': [53, 54, 55, 56]
          }

          if (cuestionario === 'cuestionario_02') {
            for (const cat in categoriasC2) {
              sumatoriaPorCategoria[cat] = 0
            }
            for (const dom in dominiosC2) {
              sumatoriaPorDominio[dom] = 0
            }
          }
          if (cuestionario === 'cuestionario_03') {
            for (const cat in categoriasC3) {
              sumatoriaPorCategoria[cat] = 0
            }
            for (const dom in dominiosC3) {
              sumatoriaPorDominio[dom] = 0
            }
          }

          for (const seccion in secciones) {
            const preguntas = secciones[seccion]
            const preguntasSeccion = preguntasCuestionario[seccion] || {}

            const idsOrdenados = Object.keys(preguntas).sort((a, b) => parseInt(a, 10) - parseInt(b, 10))

            for (const idPregunta of idsOrdenados) {
              const valor = preguntas[idPregunta]
              let textoPregunta = preguntasSeccion[idPregunta]

              if (!textoPregunta) {
                const idCompuesto = `${cuestionario}/${seccion}/${idPregunta}`
                textoPregunta = this.preguntasPorId[idCompuesto] || `Pregunta ${idPregunta}`
              }

              const numeroPregunta = parseInt(idPregunta)
              const valorPonderado = this.obtenerValorPonderado(cuestionario, numeroPregunta, valor)
              const textoRespuesta = this.obtenerTextoRespuesta(valor)

              if (typeof valorPonderado === 'number') {
                sumaPonderada += valorPonderado

                if (cuestionario === 'cuestionario_02') {
                  for (const [cat, preguntasCat] of Object.entries(categoriasC2)) {
                    if (preguntasCat.includes(numeroPregunta)) {
                      sumatoriaPorCategoria[cat] += valorPonderado
                    }
                  }
                  for (const [dom, preguntasDom] of Object.entries(dominiosC2)) {
                    if (preguntasDom.includes(numeroPregunta)) {
                      sumatoriaPorDominio[dom] += valorPonderado
                    }
                  }
                }
                if (cuestionario === 'cuestionario_03') {
                  for (const [cat, preguntasCat] of Object.entries(categoriasC3)) {
                    if (preguntasCat.includes(numeroPregunta)) {
                      sumatoriaPorCategoria[cat] += valorPonderado
                    }
                  }
                  for (const [dom, preguntasDom] of Object.entries(dominiosC3)) {
                    if (preguntasDom.includes(numeroPregunta)) {
                      sumatoriaPorDominio[dom] += valorPonderado
                    }
                  }
                }
              }

              respuestasLista.push({
                pregunta: textoPregunta,
                respuesta: textoRespuesta
              })
            }
          }

          this.$set(this.respuestasTabla, cuestionario, respuestasLista)
          this.$set(this.sumatoriasPorCuestionario, cuestionario, sumaPonderada)

          if (cuestionario === 'cuestionario_02') {
            this.$set(this.sumatoriasPorCategoria, cuestionario, sumatoriaPorCategoria)
            this.$set(this.sumatoriasPorDominio, cuestionario, sumatoriaPorDominio)
          }
          if (cuestionario === 'cuestionario_03') {
            this.$set(this.sumatoriasPorCategoria, cuestionario, sumatoriaPorCategoria)
            this.$set(this.sumatoriasPorDominio, cuestionario, sumatoriaPorDominio)
          }
        }
      } catch (error) {
        console.error('Error al cargar respuestas:', error)
      }
    },
    async eliminarEmpresa (nombre) {
      if (confirm(`¿Seguro que deseas eliminar la empresa "${nombre}"?`)) {
        try {
          const token = localStorage.getItem('token')
          if (!token) {
            this.$router.push('/')
            return
          }

          await this.$axios.delete(`/respuestas/${nombre}`, {
            headers: {
              Authorization: `Bearer ${token}`
            }
          })

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
        1: 'Si',
        2: 'No',
        3: 'Nunca',
        4: 'Rara vez',
        5: 'Algunas veces',
        6: 'Frecuentemente',
        7: 'Siempre'
      }
      return respuestas[valor] !== undefined ? respuestas[valor] : `Respuesta inválida (${valor})`
    },
    obtenerRespuestasEmpleado (empresa, empleado) {
      if (
        this.empresas &&
    this.empresas[empresa] &&
    this.empresas[empresa][empleado] &&
    this.empresas[empresa][empleado].respuestas
      ) {
        return this.empresas[empresa][empleado].respuestas
      }
      return null
    }
  }
}
</script>

<style scoped>
/* Contenedor Principal */
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding-top: 40px;
}

/* Estilos de la tarjeta de empresa */
.empresa-card {
  background-color: #ffffff;
  border-radius: 1rem;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
  margin-top: 1rem;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  padding: 1.5rem;
}

.empresa-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 6px 24px rgba(0, 0, 0, 0.12);
}

.empresa-card.bg-blue-600 {
  background-color: #9163CB;
}

.empresa-card.bg-blue-600.text-white {
  color: white;
}

.empresa-card.bg-white {
  background-color: #5A1B86;
}

.empresa-card.text-gray-900 {
  color: rgb(255, 255, 255)
}

/* Eliminar Empresa */
.empresa-card ul {
  list-style-type: none;
}

.empresa-card li {
  font-size: 1.1rem;
  color: #413ee5;
  cursor: pointer;
  transition: color 0.3s ease;
}

.empresa-card li:hover {
  color: #304bc5;
}

.empresa-card li i {
  font-size: 1.2rem;
  margin-right: 0.5rem;
}

/* Botón de Exportar */
.v-button {
  border-radius: 9999px;
  padding: 0.75rem 2rem;
  font-size: 1.1rem;
  transition: transform 0.3s ease, background-color 0.3s ease;
}

.v-button:hover {
  transform: translateY(-2px);
  background-color: #2b6cb0;
}
</style>

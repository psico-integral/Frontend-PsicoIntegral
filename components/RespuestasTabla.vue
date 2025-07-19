<template>
  <div class="bg-white rounded-xl shadow-lg p-6">
    <table class="min-w-full table-auto border-separate border-spacing-0">
      <thead class="bg-gray-200">
        <tr>
          <th class="px-6 py-4 text-left text-lg font-semibold text-gray-800">No. Pregunta</th>
          <th class="px-6 py-4 text-left text-lg font-semibold text-gray-800">Pregunta</th>
          <th class="px-6 py-4 text-left text-lg font-semibold text-gray-800">Respuesta</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in items" :key="index" class="hover:bg-gray-50 transition-colors">
          <td class="px-6 py-4 text-md text-gray-500 border-b">{{ index + 1 }}</td>
          <td class="px-6 py-4 text-md text-gray-800 border-b">{{ item.pregunta }}</td>
          <td class="px-6 py-4 text-md text-gray-700 border-b">{{ item.respuesta }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: 'RespuestasTabla',
  props: {
    items: {
      type: Array,
      required: true
    }
  }
}
</script>

<style scoped>
/* Estilos de la tabla */
table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  padding: 1.2rem;
  text-align: left;
  border-bottom: 2px solid #e2e8f0;
}

th {
  background-color: #5A1B86;
  color: #ffffff;
}

tbody tr {
  background-color: white;
}

tbody tr:hover {
  background-color: #f9fafb;
}

tbody td {
  color: #000000;
}

tbody td:first-child {
  font-weight: 500;
}
</style>

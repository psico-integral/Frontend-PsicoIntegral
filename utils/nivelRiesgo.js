export function obtenerNivelRiesgo(puntaje, nombre, tipo, cuestionario) {
  const niveles = {
    2: {
      categoria: {
        'Ambiente de trabajo': [
          { min: 0, max: 2, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 3, max: 4, nivel: 'Bajo', color: 'amarillo' },
          { min: 5, max: 6, nivel: 'Medio', color: 'naranja' },
          { min: 7, max: 8, nivel: 'Alto', color: 'rojo' },
          { min: 9, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Factores propios de la actividad': [
          { min: 0, max: 9, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 10, max: 19, nivel: 'Bajo', color: 'amarillo' },
          { min: 20, max: 29, nivel: 'Medio', color: 'naranja' },
          { min: 30, max: 39, nivel: 'Alto', color: 'rojo' },
          { min: 40, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Organización del tiempo de trabajo': [
          { min: 0, max: 3, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 4, max: 5, nivel: 'Bajo', color: 'amarillo' },
          { min: 6, max: 8, nivel: 'Medio', color: 'naranja' },
          { min: 9, max: 11, nivel: 'Alto', color: 'rojo' },
          { min: 12, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Liderazgo y relaciones en el trabajo': [
          { min: 0, max: 9, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 10, max: 17, nivel: 'Bajo', color: 'amarillo' },
          { min: 18, max: 27, nivel: 'Medio', color: 'naranja' },
          { min: 28, max: 37, nivel: 'Alto', color: 'rojo' },
          { min: 38, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ]
      },
      dominio: {
        'Condiciones en el ambiente de trabajo': [
          { min: 0, max: 2, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 3, max: 4, nivel: 'Bajo', color: 'amarillo' },
          { min: 5, max: 6, nivel: 'Medio', color: 'naranja' },
          { min: 7, max: 8, nivel: 'Alto', color: 'rojo' },
          { min: 9, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Carga de trabajo': [
          { min: 0, max: 11, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 12, max: 15, nivel: 'Bajo', color: 'amarillo' },
          { min: 16, max: 19, nivel: 'Medio', color: 'naranja' },
          { min: 20, max: 23, nivel: 'Alto', color: 'rojo' },
          { min: 24, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Falta de control sobre el trabajo': [
          { min: 0, max: 4, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 5, max: 7, nivel: 'Bajo', color: 'amarillo' },
          { min: 8, max: 10, nivel: 'Medio', color: 'naranja' },
          { min: 11, max: 13, nivel: 'Alto', color: 'rojo' },
          { min: 14, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Jornada de trabajo': [
          { min: 0, max: 0, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 1, max: 1, nivel: 'Bajo', color: 'amarillo' },
          { min: 2, max: 3, nivel: 'Medio', color: 'naranja' },
          { min: 4, max: 5, nivel: 'Alto', color: 'rojo' },
          { min: 6, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Interferencia en la relación trabajo-familia': [
          { min: 0, max: 0, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 1, max: 1, nivel: 'Bajo', color: 'amarillo' },
          { min: 2, max: 3, nivel: 'Medio', color: 'naranja' },
          { min: 4, max: 5, nivel: 'Alto', color: 'rojo' },
          { min: 6, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Liderazgo': [
          { min: 0, max: 2, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 3, max: 4, nivel: 'Bajo', color: 'amarillo' },
          { min: 5, max: 7, nivel: 'Medio', color: 'naranja' },
          { min: 8, max: 10, nivel: 'Alto', color: 'rojo' },
          { min: 11, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Relaciones en el trabajo': [
          { min: 0, max: 4, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 5, max: 7, nivel: 'Bajo', color: 'amarillo' },
          { min: 8, max: 10, nivel: 'Medio', color: 'naranja' },
          { min: 11, max: 13, nivel: 'Alto', color: 'rojo' },
          { min: 14, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Violencia': [
          { min: 0, max: 6, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 7, max: 9, nivel: 'Bajo', color: 'amarillo' },
          { min: 10, max: 12, nivel: 'Medio', color: 'naranja' },
          { min: 13, max: 15, nivel: 'Alto', color: 'rojo' },
          { min: 16, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ]
      }
    },
    3: {
      categoria: {
        'Ambiente de trabajo': [
          { min: 0, max: 4, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 5, max: 8, nivel: 'Bajo', color: 'amarillo' },
          { min: 9, max: 10, nivel: 'Medio', color: 'naranja' },
          { min: 11, max: 13, nivel: 'Alto', color: 'rojo' },
          { min: 14, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Factores propios de la actividad': [
          { min: 0, max: 14, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 15, max: 29, nivel: 'Bajo', color: 'amarillo' },
          { min: 30, max: 44, nivel: 'Medio', color: 'naranja' },
          { min: 45, max: 59, nivel: 'Alto', color: 'rojo' },
          { min: 60, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Organización del tiempo de trabajo': [
          { min: 0, max: 4, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 5, max: 6, nivel: 'Bajo', color: 'amarillo' },
          { min: 7, max: 9, nivel: 'Medio', color: 'naranja' },
          { min: 10, max: 12, nivel: 'Alto', color: 'rojo' },
          { min: 13, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Liderazgo y relaciones en el trabajo': [
          { min: 0, max: 13, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 14, max: 28, nivel: 'Bajo', color: 'amarillo' },
          { min: 29, max: 41, nivel: 'Medio', color: 'naranja' },
          { min: 42, max: 57, nivel: 'Alto', color: 'rojo' },
          { min: 58, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Entorno organizacional': [
          { min: 0, max: 9, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 10, max: 13, nivel: 'Bajo', color: 'amarillo' },
          { min: 14, max: 17, nivel: 'Medio', color: 'naranja' },
          { min: 18, max: 22, nivel: 'Alto', color: 'rojo' },
          { min: 23, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ]
      },
      dominio: {
        'Condiciones en el ambiente de trabajo': [
          { min: 0, max: 4, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 5, max: 8, nivel: 'Bajo', color: 'amarillo' },
          { min: 9, max: 10, nivel: 'Medio', color: 'naranja' },
          { min: 11, max: 13, nivel: 'Alto', color: 'rojo' },
          { min: 14, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Carga de trabajo': [
          { min: 0, max: 14, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 15, max: 20, nivel: 'Bajo', color: 'amarillo' },
          { min: 21, max: 26, nivel: 'Medio', color: 'naranja' },
          { min: 27, max: 36, nivel: 'Alto', color: 'rojo' },
          { min: 37, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Falta de control sobre el trabajo': [
          { min: 0, max: 10, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 11, max: 15, nivel: 'Bajo', color: 'amarillo' },
          { min: 16, max: 20, nivel: 'Medio', color: 'naranja' },
          { min: 21, max: 24, nivel: 'Alto', color: 'rojo' },
          { min: 25, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Jornada de trabajo': [
          { min: 0, max: 0, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 1, max: 1, nivel: 'Bajo', color: 'amarillo' },
          { min: 2, max: 3, nivel: 'Medio', color: 'naranja' },
          { min: 4, max: 5, nivel: 'Alto', color: 'rojo' },
          { min: 6, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Interferencia en la relación trabajo-familia': [
          { min: 0, max: 3, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 4, max: 5, nivel: 'Bajo', color: 'amarillo' },
          { min: 6, max: 7, nivel: 'Medio', color: 'naranja' },
          { min: 8, max: 9, nivel: 'Alto', color: 'rojo' },
          { min: 10, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Liderazgo': [
          { min: 0, max: 8, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 9, max: 11, nivel: 'Bajo', color: 'amarillo' },
          { min: 12, max: 15, nivel: 'Medio', color: 'naranja' },
          { min: 16, max: 19, nivel: 'Alto', color: 'rojo' },
          { min: 20, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Relaciones en el trabajo': [
          { min: 0, max: 9, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 10, max: 12, nivel: 'Bajo', color: 'amarillo' },
          { min: 13, max: 16, nivel: 'Medio', color: 'naranja' },
          { min: 17, max: 20, nivel: 'Alto', color: 'rojo' },
          { min: 21, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Violencia': [
          { min: 0, max: 6, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 7, max: 9, nivel: 'Bajo', color: 'amarillo' },
          { min: 10, max: 12, nivel: 'Medio', color: 'naranja' },
          { min: 13, max: 15, nivel: 'Alto', color: 'rojo' },
          { min: 16, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Reconocimiento del desempeño': [
          { min: 0, max: 5, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 6, max: 9, nivel: 'Bajo', color: 'amarillo' },
          { min: 10, max: 13, nivel: 'Medio', color: 'naranja' },
          { min: 14, max: 17, nivel: 'Alto', color: 'rojo' },
          { min: 18, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ],
        'Insuficiente sentido de pertenencia e, inestabilidad': [
          { min: 0, max: 3, nivel: 'Nulo o despreciable', color: 'verde' },
          { min: 4, max: 5, nivel: 'Bajo', color: 'amarillo' },
          { min: 6, max: 7, nivel: 'Medio', color: 'naranja' },
          { min: 8, max: 9, nivel: 'Alto', color: 'rojo' },
          { min: 10, max: Infinity, nivel: 'Muy alto', color: 'rojo-oscuro' }
        ]
      }
    }
  };

  const reglas =
    niveles[cuestionario] &&
    niveles[cuestionario][tipo] &&
    niveles[cuestionario][tipo][nombre];

  if (!reglas) {
    return { nivel: 'No definido', color: 'gris' };
  }

  return reglas.find(r => puntaje <= r.max) || { nivel: 'No definido', color: 'gris' };
}

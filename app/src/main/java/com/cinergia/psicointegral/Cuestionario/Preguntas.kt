package com.cinergia.psicointegral.Cuestionario

data class Pregunta(val pregunta: String, val tipo: String)

data class Seccion(val seccion: Map<String, Pregunta>)

data class Cuestionario(val cuestionario: Map<String, List<Seccion>>)

fun obtenerCuestionarios(): Cuestionario {
    return Cuestionario(
        mapOf(
            "cuestionario_01" to listOf(
                Seccion(
                    mapOf(
                        "01" to Pregunta("¿Accidente que tenga como consecuencia la muerte, la pérdida de un miembro o una lesión grave?", "si_no"),
                        "02" to Pregunta("¿Asaltos?", "si_no"),
                        "03" to Pregunta("¿Actos violentos que derivaron en lesiones graves?", "si_no"),
                        "04" to Pregunta("¿Secuestro?", "si_no"),
                        "05" to Pregunta("¿Amenazas?", "si_no"),
                        "06" to Pregunta("¿Cualquier otro que ponga en riesgo su vida o salud, y/o la de otras personas?", "si_no")
                    )
                ),
                Seccion(
                    mapOf(
                        "01" to Pregunta("¿Ha tenido recuerdos recurrentes sobre el acontecimiento que le provocan malestares?", "frecuencia"),
                        "02" to Pregunta("¿Ha tenido sueños de carácter recurrente sobre el acontecimiento, que le producen malestar?", "frecuencia")
                    )
                ),
                Seccion(
                    mapOf(
                        "01" to Pregunta("¿Se ha esforzado por evitar todo tipo de sentimientos, conversaciones o situaciones que le puedan recordar el acontecimiento?", "frecuencia"),
                        "02" to Pregunta("¿Ha tenido sueños de carácter recurrente sobre el acontecimiento, que le producen malestar?", "frecuencia"),
                        "03" to Pregunta("¿Ha tenido dificultad para recordar alguna parte importante del evento?", "frecuencia"),
                        "04" to Pregunta("¿Ha disminuido su interés en sus actividades cotidianas?", "frecuencia"),
                        "05" to Pregunta("¿Se ha sentido usted alejado o distante de los demás?", "frecuencia"),
                        "06" to Pregunta("¿Ha notado que tiene dificultad para expresar sus sentimientos?", "frecuencia"),
                        "07" to Pregunta("¿Ha tenido la impresión de que su vida se va a acortar, que va a morir antes que otras personas o que tiene un futuro limitado?", "frecuencia")
                    )
                ),
                Seccion(
                    mapOf(
                        "01" to Pregunta("¿Ha tenido usted dificultades para dormir?", "frecuencia"),
                        "02" to Pregunta("¿Ha estado particularmente irritable o le han dado arranques de coraje?", "frecuencia"),
                        "03" to Pregunta("¿Ha tenido dificultad para concentrarse?", "frecuencia"),
                        "04" to Pregunta("¿Ha estado nervioso o constantemente en alerta?", "frecuencia"),
                        "05" to Pregunta("¿Se ha sobresaltado fácilmente por cualquier cosa?", "frecuencia")
                    )
                )
            ),
            "cuestionario_02" to listOf(
                Seccion(
                    mapOf(
                        "01" to Pregunta("Mi trabajo me exige hacer mucho esfuerzo físico", "frecuencia"),
                        "02" to Pregunta("Me preocupa sufrir un accidente en mi trabajo", "si_no"),
                        "03" to Pregunta("Considero que las actividades que realizo son peligrosas", "si_no"),
                        "04" to Pregunta("Por la cantidad de trabajo que tengo debo darme tiempo adicional a mi turno", "frecuencia"),
                        "05" to Pregunta("Por la cantidad de trabajo que tengo debo trabajar sin parar", "frecuencia"),
                        "06" to Pregunta("Considero que es necesario mantener un ritmo de trabajo acelerado", "frecuencia"),
                        "07" to Pregunta("Mi trabajo exige que esté muy concentrado", "frecuencia"),
                        "08" to Pregunta("Mi trabajo requiere que memorice mucha información", "frecuencia"),
                        "09" to Pregunta("Mi trabajo exige que atienda varios asuntos al mismo tiempo", "frecuencia")
                    )
                ),
                Seccion(
                    mapOf(
                        "01" to Pregunta("Comunican tarde los asuntos de trabajo", "frecuencia"),
                        "02" to Pregunta("Dificultan el logro de los resultados del trabajo", "frecuencia"),
                        "03" to Pregunta("Ignoran las sugerencias para mejorar su trabajo", "frecuencia")
                    )
                ),
                Seccion(
                    mapOf(
                        "01" to Pregunta("En mi trabajo soy responsable de cosas de mucho valor", "si_no"),
                        "02" to Pregunta("Respondo ante mi jefe por los resultados de toda mi área de trabajo", "si_no"),
                        "03" to Pregunta("En mi trabajo me dan órdenes contradictorias", "si_no"),
                        "04" to Pregunta("Considero que en mi trabajo me piden hacer cosas innecesarias", "si_no")
                    )
                ),
                Seccion(
                    mapOf(
                        "01" to Pregunta("Trabajo horas extras más de tres veces a la semana", "frecuencia"),
                        "02" to Pregunta("Mi trabajo me exige laborar en días de descanso, festivos o fines de semana", "frecuencia"),
                        "03" to Pregunta("Considero que el tiempo en el trabajo es mucho y perjudica mis actividades familiares o personales", "frecuencia"),
                        "04" to Pregunta("Pienso en las actividades familiares o personales cuando estoy en mi trabajo", "frecuencia")
                    )
                ),
                Seccion(
                    mapOf(
                        "01" to Pregunta("Mi trabajo permite que desarrolle nuevas habilidades", "si_no"),
                        "02" to Pregunta("En mi trabajo puedo aspirar a un mejor puesto", "si_no"),
                        "03" to Pregunta("Durante mi jornada de trabajo puedo tomar pausas cuando las necesito", "si_no"),
                        "04" to Pregunta("Puedo decidir la velocidad a la que realizo mis actividades en mi trabajo", "si_no"),
                        "05" to Pregunta("Puedo cambiar el orden de las actividades que realizo en mi trabajo", "si_no")
                    )
                ),
                Seccion(
                    mapOf(
                        "01" to Pregunta("Me informan con claridad cuáles son mis funciones", "si_no"),
                        "02" to Pregunta("Me explican claramente los resultados que debo obtener en mi trabajo", "si_no"),
                        "03" to Pregunta("Me informan con quién puedo resolver problemas o asuntos de trabajo", "si_no"),
                        "04" to Pregunta("Me permiten asistir a capacitaciones relacionadas con mi trabajo", "si_no"),
                        "05" to Pregunta("Recibo capacitación útil para hacer mi trabajo", "si_no")
                    )
                ),
                Seccion(
                    mapOf(
                        "10" to Pregunta("Se manipulan las situaciones de trabajo para hacerme parecer un mal trabajador", "si_no"),
                        "11" to Pregunta("Se ignoran mis éxitos laborales y se atribuyen a otros trabajadores", "si_no"),
                        "12" to Pregunta("Me bloquean o impiden las oportunidades que tengo para obtener ascenso o mejora en mi trabajo", "si_no"),
                        "13" to Pregunta("He presenciado actos de violencia en mi centro de trabajo", "si_no")
                    )
                ),
                Seccion(
                    mapOf(
                        "01" to Pregunta("En mi trabajo debo brindar servicio a clientes o usuarios", "si_no")
                    )
                ),
                Seccion(
                    mapOf(
                        "01" to Pregunta("Atiendo clientes o usuarios muy enojados", "frecuencia"),
                        "02" to Pregunta("Mi trabajo me exige atender personas muy necesitadas de ayuda o enfermas", "frecuencia"),
                        "03" to Pregunta("Para hacer mi trabajo debo demostrar sentimientos distintos a los míos", "frecuencia")
                    )
                ),
                Seccion(
                    mapOf(
                        "01" to Pregunta("Soy jefe de otros trabajadores", "si_no")
                    )
                )
            ),
            "cuestionario_03" to listOf(
                Seccion(
                    mapOf(
                        "01" to Pregunta("El espacio donde trabajo me permite realizar mis actividades de manera segura e higiénica", "si_no"),
                        "02" to Pregunta("Mi trabajo me exige hacer mucho esfuerzo físico", "frecuencia"),
                        "03" to Pregunta("Me preocupa sufrir un accidente en mi trabajo", "si_no"),
                        "04" to Pregunta("Considero que en mi trabajo se aplican las normas de seguridad y salud en el trabajo", "si_no"),
                        "05" to Pregunta("Considero que las actividades que realizo son peligrosas", "si_no")
                    )
                ),
                Seccion(
                    mapOf(
                        "01" to Pregunta("Por la cantidad de trabajo que tengo debo quedarme tiempo adicional a mi turno", "frecuencia"),
                        "02" to Pregunta("Por la cantidad de trabajo que tengo debo trabajar sin parar", "frecuencia"),
                        "03" to Pregunta("Considero que es necesario mantener un ritmo de trabajo acelerado", "frecuencia")
                    )
                ),
                Seccion(
                    mapOf(
                        "01" to Pregunta("Mi trabajo exige que esté muy concentrado", "frecuencia"),
                        "02" to Pregunta("Mi trabajo requiere que memorice mucha información", "frecuencia"),
                        "03" to Pregunta("Mi trabajo exige que atienda varios asuntos al mismo tiempo", "frecuencia")
                    )
                ),
                Seccion(
                    mapOf(
                        "01" to Pregunta("Mi trabajo me exige realizar mucho esfuerzo físico", "frecuencia"),
                        "02" to Pregunta("Mi trabajo me exige estar mucho tiempo de pie", "frecuencia"),
                        "03" to Pregunta("Mi trabajo me exige estar mucho tiempo sentado", "frecuencia"),
                        "04" to Pregunta("Mi trabajo me exige hacer movimientos repetitivos", "frecuencia"),
                        "05" to Pregunta("Mi trabajo me exige hacer movimientos muy rápidos", "frecuencia"),
                        "06" to Pregunta("Mi trabajo me exige cargar peso", "frecuencia")
                    )
                )
            )
        )
    )
}

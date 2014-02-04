import com.cdrossi.Encuesta
import com.cdrossi.Especialidad
import com.cdrossi.EstadisticasService
import com.cdrossi.EstadisticasEncuesta
import com.cdrossi.EstadoEncuesta
import com.cdrossi.Medico
import com.cdrossi.Role
import com.cdrossi.Servicio;
import com.cdrossi.Sexo
import com.cdrossi.Supervisor
import com.cdrossi.Telefono
import com.cdrossi.TipoTelefono
import com.cdrossi.Usuario
import com.cdrossi.UsuarioRole
import com.cdrossi.Visitador
import com.cdrossi.EncuestaAsignada
import com.cdrossi.TipoEncuestaAsignada
import com.cdrossi.Pregunta
import com.cdrossi.Respuesta
import com.cdrossi.TipoPregunta
import com.cdrossi.Derivaciones
import static java.util.Calendar.*

class BootStrap {

    def init = { servletContext ->
		environments {
			development {
				
				def enc1FI = new Date()
				enc1FI[YEAR] = 2012
				enc1FI[MONTH] = APRIL
				enc1FI[DATE] = 1
				
				def enc1FF = new Date()
				enc1FF[YEAR] = 2012
				enc1FF[MONTH] = JUNE
				enc1FF[DATE] = 1
				
				def enc2FI = new Date()
				enc2FI[YEAR] = 2012
				enc2FI[MONTH] = JUNE
				enc2FI[DATE] = 1
				
				def enc2FF = new Date()
				enc2FF[YEAR] = 2012
				enc2FF[MONTH] = AUGUST
				enc2FF[DATE] = 1
				
				/* Encuesta */
				def Encuesta enc1 = new Encuesta(nombre: 'Encuesta numero 1', fechaInicio: enc1FI, fechaFin: enc1FF, estadoEncuesta: EstadoEncuesta.BORRADOR).save()
				def Encuesta enc2 = new Encuesta(nombre: 'Encuesta numero 2', fechaInicio: enc2FI, fechaFin: enc2FF, estadoEncuesta: EstadoEncuesta.ACTIVO).save()
				def Encuesta enc3 = new Encuesta(nombre: 'Encuesta numero 3', fechaInicio: enc1FI, fechaFin: enc1FF, estadoEncuesta: EstadoEncuesta.BORRADOR).save()
				
				/* Preguntas y Respuestas*/
				def Pregunta p1 = new Pregunta(enunciado: "¿Como calificaria la atención general que se brinda en el Centro?", orden: 1, tipoPregunta: TipoPregunta.SINGLE_CHOICE, encuesta: enc1).save()
				def Pregunta p2 = new Pregunta(enunciado: "¿Fue atendido por algun colaborador del Centro?. ¿Cómo calificaria la atención recibida?", orden: 2, tipoPregunta: TipoPregunta.SINGLE_CHOICE, encuesta: enc1).save()
				def Pregunta p3 = new Pregunta(enunciado: "¿Cómo nos calificaría ante un colega?", orden: 3, tipoPregunta: TipoPregunta.SINGLE_CHOICE, encuesta: enc1).save()
				
				def Respuesta p1r1 = new Respuesta(pregunta: p1, orden: 1, valor: "Excelente").save()
				def Respuesta p1r2 = new Respuesta(pregunta: p1, orden: 2, valor: "Muy Bueno").save()
				def Respuesta p1r3 = new Respuesta(pregunta: p1, orden: 3, valor: "Regular").save()
				
				def Respuesta p2r1 = new Respuesta(pregunta: p2, orden: 1, valor: "Excelente").save()
				def Respuesta p2r2 = new Respuesta(pregunta: p2, orden: 2, valor: "Muy Bueno").save()
				def Respuesta p2r3 = new Respuesta(pregunta: p2, orden: 3, valor: "Regular").save()
				
				def Respuesta p3r1 = new Respuesta(pregunta: p3, orden: 1, valor: "Muy Bien").save()
				def Respuesta p3r2 = new Respuesta(pregunta: p3, orden: 2, valor: "Mal").save()
				def Respuesta p3r3 = new Respuesta(pregunta: p3, orden: 3, valor: "Sin Comentarios").save()
				
				p1.addToRespuestas(p1r1)
				p1.addToRespuestas(p1r2)
				p1.addToRespuestas(p1r3)
				p1.save()
				
				p2.addToRespuestas(p2r1)
				p2.addToRespuestas(p2r2)
				p2.addToRespuestas(p2r3)
				p2.save()
				
				p3.addToRespuestas(p3r1)
				p3.addToRespuestas(p3r2)
				p3.addToRespuestas(p3r3)
				p3.save()
				
				enc1.addToPreguntas(p1)
				enc1.addToPreguntas(p2)
				enc1.addToPreguntas(p3)
				enc1.save()
				
				def Especialidad e1 = new Especialidad(nombre:'Ginecologia').save()
				def Especialidad e2 = new Especialidad(nombre:'Oftalmologia').save()
				def Especialidad e3 = new Especialidad(nombre:'Traumatologia').save()
				
				def Telefono t1 = new Telefono(tipoTelefono: TipoTelefono.CELULAR, numero: '+54115666777').save()
				def Telefono t2 = new Telefono(tipoTelefono: TipoTelefono.CONSULTORIO, numero: '+54115644455').save()
				def Telefono t3 = new Telefono(tipoTelefono: TipoTelefono.CELULAR, numero: '+54115666777').save()
				def Telefono t4 = new Telefono(tipoTelefono: TipoTelefono.CONSULTORIO, numero: '+54115644455').save()
				
				def Medico m1 = new Medico(nombreCompleto:'Valente, Silvina', especialidad:e1, sexo: Sexo.FEMENINO, matriculaNacional:'M.N 6.015', nota: "Esto es una nota para probar").save()
				def Medico m2 = new Medico(nombreCompleto:'Nisenbaum, Veronica', especialidad:e2, sexo: Sexo.FEMENINO, matriculaProvincial:'M.P 18.066', nota: "Esto es una nota para probar2").save()
				def Medico m3 = new Medico(nombreCompleto:'Paez, Marcia', especialidad:e2, sexo: Sexo.FEMENINO, matriculaEspecial:'M.E 21.988', nota: "Esto es una nota para probar33").save()
				def Medico m4 = new Medico(nombreCompleto:'Rojo, Santiago', especialidad:e3, sexo: Sexo.MASCULINO).save()
				def Medico m5 = new Medico(nombreCompleto:'Rodriguez, Hernan', especialidad:e1, sexo: Sexo.MASCULINO, nota: "Esto es una nota para probar555").save()
				
				def ea1 = new EncuestaAsignada(tipoEncuestaAsignada: TipoEncuestaAsignada.ENCUESTADA, encuesta: enc1, medico: m1, minuta: "Prueba de minuta de medico 1 de encuesta1", respuestas: [p1r1, p2r1, p3r2]).save()
				def ea2 = new EncuestaAsignada(tipoEncuestaAsignada: TipoEncuestaAsignada.ENCUESTADA, encuesta: enc2, medico: m1, minuta: "Prueba de minuta2 de medico 1 de encuesta2").save()
				def ea3 = new EncuestaAsignada(tipoEncuestaAsignada: TipoEncuestaAsignada.CANCELADA, encuesta: enc3, medico: m1).save()
				
				def ea4 = new EncuestaAsignada(tipoEncuestaAsignada: TipoEncuestaAsignada.ENCUESTADA, encuesta: enc1, medico: m2, minuta: "Prueba de minuta de medico 2 de encuesta1", respuestas: [p1r1, p2r1, p3r3]).save()
				def ea5 = new EncuestaAsignada(tipoEncuestaAsignada: TipoEncuestaAsignada.PENDIENTE, encuesta: enc2, medico: m2).save()
				def ea6 = new EncuestaAsignada(tipoEncuestaAsignada: TipoEncuestaAsignada.CANCELADA, encuesta: enc3, medico: m2).save()
				
				def ea7 = new EncuestaAsignada(tipoEncuestaAsignada: TipoEncuestaAsignada.ENCUESTADA, encuesta: enc1, medico: m3, minuta: "Prueba de minuta de medico 3 de encuesta1", respuestas: [p1r1, p2r1, p3r1]).save()
				def ea8 = new EncuestaAsignada(tipoEncuestaAsignada: TipoEncuestaAsignada.PENDIENTE, encuesta: enc2, medico: m3).save()
				def ea9 = new EncuestaAsignada(tipoEncuestaAsignada: TipoEncuestaAsignada.CANCELADA, encuesta: enc3, medico: m3).save()
				
				def ea10 = new EncuestaAsignada(tipoEncuestaAsignada: TipoEncuestaAsignada.ENCUESTADA, encuesta: enc1, medico: m4, minuta: "Prueba de minuta de medico 4 de encuesta1", respuestas: [p1r3, p2r1, p3r3]).save()
				def ea11 = new EncuestaAsignada(tipoEncuestaAsignada: TipoEncuestaAsignada.ENCUESTADA, encuesta: enc2, medico: m4, minuta: "Prueba de minuta2 de medico 4 de encuesta2").save()
				def ea12 = new EncuestaAsignada(tipoEncuestaAsignada: TipoEncuestaAsignada.CANCELADA, encuesta: enc3, medico: m4).save()
				
				m1.addToTelefonos(t1)
				m1.addToTelefonos(t2)
				m1.addToMedicoEncuestasAsignadas(ea1)
				m1.addToMedicoEncuestasAsignadas(ea2)
				m1.addToMedicoEncuestasAsignadas(ea3)
				m1.save()
				
				m2.addToMedicoEncuestasAsignadas(ea4)
				m2.addToMedicoEncuestasAsignadas(ea5)
				m2.addToMedicoEncuestasAsignadas(ea6)
				m2.save()
				
				m3.addToMedicoEncuestasAsignadas(ea7)
				m3.addToMedicoEncuestasAsignadas(ea8)
				m3.addToMedicoEncuestasAsignadas(ea9)
				m3.save()
				
				m4.addToTelefonos(t3)
				m4.addToTelefonos(t4)
				m4.addToMedicoEncuestasAsignadas(ea10)
				m4.addToMedicoEncuestasAsignadas(ea11)
				m4.addToMedicoEncuestasAsignadas(ea12)
				m4.save()
				
				enc1.addToEncuestaEncuestasAsignadas(ea1)
				enc1.addToEncuestaEncuestasAsignadas(ea4)
				enc1.addToEncuestaEncuestasAsignadas(ea7)
				enc1.addToEncuestaEncuestasAsignadas(ea10)
				enc1.save()
				
				enc2.addToEncuestaEncuestasAsignadas(ea2)
				enc2.addToEncuestaEncuestasAsignadas(ea5)
				enc2.addToEncuestaEncuestasAsignadas(ea8)
				enc2.addToEncuestaEncuestasAsignadas(ea11)
				enc2.save()
				
				enc3.addToEncuestaEncuestasAsignadas(ea3)
				enc3.addToEncuestaEncuestasAsignadas(ea6)
				enc3.addToEncuestaEncuestasAsignadas(ea9)
				enc3.addToEncuestaEncuestasAsignadas(ea12)
				enc3.save()
				
				/* DERIVACIONES */
				def serv1 = new Servicio(nombre: "Ecografía").save()
				def serv2 = new Servicio(nombre: "Ginecología").save()
				def serv3 = new Servicio(nombre: "Radiología").save()
				def serv4 = new Servicio(nombre: "Mamografía").save()
				def serv5 = new Servicio(nombre: "Densitometría").save()
				def serv6 = new Servicio(nombre: "Medicina Nuclear").save()
				def serv7 = new Servicio(nombre: "Resonancia Magnética").save()
				
				def der21 = new Derivaciones(medico: m1, servicio: serv1, cantEstudios: 1, cantPacientes: 8, monto: 73.12, month: 3, year: 2012).save()
				def der11 = new Derivaciones(medico: m1, servicio: serv2, cantEstudios: 1, cantPacientes: 6, monto: 20.97, month: 3, year: 2012).save()
				def der13 = new Derivaciones(medico: m1, servicio: serv3, cantEstudios: 8, cantPacientes: 5, monto: 1594.53, month: 3, year: 2012).save()
				def der22 = new Derivaciones(medico: m2, servicio: serv1, cantEstudios: 8, cantPacientes: 2, monto: 67.2, month: 3, year: 2012).save()
				def der23 = new Derivaciones(medico: m3, servicio: serv1, cantEstudios: 8, cantPacientes: 5, monto: 1562.73, month: 3, year: 2012).save()
				def der24 = new Derivaciones(medico: m4, servicio: serv1, cantEstudios: 7, cantPacientes: 69, monto: 606.66, month: 3, year: 2012).save()
				def der25 = new Derivaciones(medico: m5, servicio: serv1, cantEstudios: 16, cantPacientes: 89, monto: 710.58, month: 3, year: 2012).save()
				
				def der16 = new Derivaciones(medico: m1, servicio: serv1, cantEstudios: 1, cantPacientes: 1, monto: 20.97, month: 4, year: 2012).save()
				def der12 = new Derivaciones(medico: m2, servicio: serv1, cantEstudios: 8, cantPacientes: 2, monto: 89.52, month: 5, year: 2012).save()
				def der17 = new Derivaciones(medico: m2, servicio: serv2, cantEstudios: 8, cantPacientes: 8, monto: 15.52, month: 4, year: 2012).save()
				def der18 = new Derivaciones(medico: m3, servicio: serv1, cantEstudios: 8, cantPacientes: 5, monto: 1594.53, month: 4, year: 2012).save()
				def der19 = new Derivaciones(medico: m4, servicio: serv1, cantEstudios: 7, cantPacientes: 69, monto: 1594.53, month: 4, year: 2012).save()
				def der20 = new Derivaciones(medico: m5, servicio: serv1, cantEstudios: 16, cantPacientes: 89, monto: 44.89, month: 4, year: 2012).save()
				
				def der1 = new Derivaciones(medico: m1, servicio: serv1, cantEstudios: 1, cantPacientes: 5, monto: 142.57, month: 5, year: 2012).save()
				def der14 = new Derivaciones(medico: m1, servicio: serv2, cantEstudios: 7, cantPacientes: 100, monto: 880.53, month: 5, year: 2012).save()
				def der15 = new Derivaciones(medico: m1, servicio: serv3, cantEstudios: 16, cantPacientes: 76, monto: 44.89, month: 5, year: 2012).save()
				def der2 = new Derivaciones(medico: m2, servicio: serv1, cantEstudios: 8, cantPacientes: 2, monto: 59.94, month: 5, year: 2012).save()
				def der3 = new Derivaciones(medico: m3, servicio: serv1, cantEstudios: 8, cantPacientes: 5, monto: 322.29, month: 5, year: 2012).save()
				def der4 = new Derivaciones(medico: m4, servicio: serv1, cantEstudios: 7, cantPacientes: 69, monto: 606.66, month: 5, year: 2012).save()
				def der5 = new Derivaciones(medico: m5, servicio: serv1, cantEstudios: 16, cantPacientes: 89, monto: 606.66, month: 5, year: 2012).save()
				
				def der6 = new Derivaciones(medico: m1, servicio: serv1, cantEstudios: 1, cantPacientes: 1, monto: 73.12, month: 6, year: 2012).save()
				def der7 = new Derivaciones(medico: m2, servicio: serv1, cantEstudios: 8, cantPacientes: 2, monto: 67.2, month: 6, year: 2012).save()
				def der8 = new Derivaciones(medico: m3, servicio: serv1, cantEstudios: 8, cantPacientes: 5, monto: 1562.73, month: 6, year: 2012).save()
				def der26 = new Derivaciones(medico: m3, servicio: serv1, cantEstudios: 8, cantPacientes: 5, monto: 1562.73, month: 6, year: 2012).save()
				def der27 = new Derivaciones(medico: m3, servicio: serv2, cantEstudios: 8, cantPacientes: 15, monto: 1562.73, month: 6, year: 2012).save()
				def der28 = new Derivaciones(medico: m3, servicio: serv3, cantEstudios: 8, cantPacientes: 23, monto: 1562.73, month: 6, year: 2012).save()
				def der29 = new Derivaciones(medico: m3, servicio: serv4, cantEstudios: 8, cantPacientes: 58, monto: 1562.73, month: 6, year: 2012).save()
				def der30 = new Derivaciones(medico: m3, servicio: serv5, cantEstudios: 8, cantPacientes: 65, monto: 1562.73, month: 6, year: 2012).save()
				def der31 = new Derivaciones(medico: m3, servicio: serv6, cantEstudios: 8, cantPacientes: 43, monto: 1562.73, month: 6, year: 2012).save()
				def der32 = new Derivaciones(medico: m3, servicio: serv7, cantEstudios: 8, cantPacientes: 19, monto: 1562.73, month: 6, year: 2012).save()
				def der9 = new Derivaciones(medico: m4, servicio: serv1, cantEstudios: 7, cantPacientes: 69, monto: 606.66, month: 6, year: 2012).save()
				def der10 = new Derivaciones(medico: m5, servicio: serv1, cantEstudios: 16, cantPacientes: 89, monto: 710.58, month: 6, year: 2012).save()
				
				
				
				
				/* LOGIN - USUARIOS */
				def Usuario s = new Supervisor(nombre:'VeronicaSupervisor', apellido:'NisenbaumSupervisor', contrasenia:'123456', email:'sup123@gmail.com')
				def Usuario s2 = new Supervisor(nombre:'VeronicaSupervisor2', apellido:'NisenbaumSupervisor2', contrasenia:'123456', email:'sup456@gmail.com')
				
				def Usuario v1 = new Visitador(nombre:'VeronicaVisitador1', apellido:'NisenbaumVisitador1', contrasenia:'123456', email:'vis123@gmail.com')
				v1.addToMedicosAsignados(m1)
				v1.addToMedicosAsignados(m2)
				v1.addToMedicosAsignados(m3)
				
				def Usuario v2 = new Visitador(nombre:'VeronicaVisitador2', apellido:'NisenbaumVisitador2', contrasenia:'123456', email:'vis1234@gmail.com')
				v2.addToMedicosAsignados(m4)
				v2.addToMedicosAsignados(m5)
				
				s.save()
				s2.save()
				v1.save()
				v2.save()
				
				def roleSupervisor = new Role(authority: s.role).save(flush: true)
				def roleVisitador = new Role(authority: v1.role).save(flush: true)
		
				UsuarioRole.create s, roleSupervisor, true
				UsuarioRole.create v1, roleVisitador, true
					
				//UserRole.remove user, role
			}
		} }
    def destroy = {
    }
}


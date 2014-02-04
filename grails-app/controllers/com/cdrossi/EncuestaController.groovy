package com.cdrossi

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

import java.util.List;
import java.util.Map;

import static org.codehaus.groovy.grails.commons.ConfigurationHolder.config as Config
import org.joda.time.DateTime
import org.springframework.dao.DataIntegrityViolationException

@Secured(['ROLE_ADMIN'])
class EncuestaController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def asignacionMedicosService
	def filterPaneService
	def grailsApplication
	def fileUploadService
	def utilsService
	def excelExporterService
	def estadisticasService
	
    def index() {
		//TODO REMOVE ...
		estadisticasService.procesarEstadisticasEncuestas()
		redirect(action: "list", params: params)
    }

    def list() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		def encuestaList = Encuesta.findAll(params){
			estadoEncuesta != EstadoEncuesta.NO_ACTIVO	
		}
        [encuestaInstanceList: encuestaList, encuestaInstanceTotal: Encuesta.count()]
    }
	
	def create() {
        [encuestaInstance: new Encuesta(params)]
    }

    def save() {
        def encuestaInstance = new Encuesta(params)
		def EstadisticasEncuesta estadisticasEncuesta = new EstadisticasEncuesta( porcentajeEncuestado: 0, porcentajeCancelado: 0, porcentajePendiente: 100)
		estadisticasEncuesta.save()
		encuestaInstance.estadisticasEncuesta = estadisticasEncuesta
		
        if (!encuestaInstance.save(flush: true)) {
            render(view: "create", model: [encuestaInstance: encuestaInstance])
            return
        }

		redirect(controller: 'pregunta', action: "create",  params: [idEncuesta: encuestaInstance.id])
    }

    def show() {
        def encuestaInstance = Encuesta.get(params.id)
		
		if (!encuestaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'encuesta.label', default: 'Encuesta'), params.id])
            redirect(action: "list")
            return
        }

        [encuestaInstance: encuestaInstance]
    }

    def edit() {
        def encuestaInstance = Encuesta.get(params.id)
		
		if ( encuestaInstance.estadoEncuesta.equals(EstadoEncuesta.ACTIVO) || encuestaInstance.estadoEncuesta.equals(EstadoEncuesta.CANCELADA) || encuestaInstance.estadoEncuesta.equals(EstadoEncuesta.FINALIZADA))
		{
			flash.message = message(code: 'encuesta.no.editar.error', args: [encuestaInstance.estadoEncuesta])
			redirect(action: "show", id: encuestaInstance.id)
			return
		}
		
        if (!encuestaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'encuesta.label', default: 'Encuesta'), params.id])
            redirect(action: "list")
            return
        }

        [encuestaInstance: encuestaInstance]
    }

    def update() {
        def encuestaInstance = Encuesta.get(params.id)
        if (!encuestaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'encuesta.label', default: 'Encuesta'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (encuestaInstance.version > version) {
                encuestaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'encuesta.label', default: 'Encuesta')] as Object[],
                          "Another user has updated this Encuesta while you were editing")
                render(view: "edit", model: [encuestaInstance: encuestaInstance])
                return
            }
        }

        encuestaInstance.properties = params

        if (!encuestaInstance.save(flush: true)) {
            render(view: "edit", model: [encuestaInstance: encuestaInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'encuesta.label', default: 'Encuesta'), encuestaInstance.id])
        redirect(action: "show", id: encuestaInstance.id)
    }	
	
	
	def delete() {
		def encuestaInstance = Encuesta.get(params.id)
		if (!encuestaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'encuesta.label', default: 'Encuesta'), params.id])
			redirect(action: "list")
			return
		}

		try {
			encuestaInstance.estadoEncuesta = EstadoEncuesta.NO_ACTIVO
			encuestaInstance.save()
			
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'encuesta.label', default: 'Encuesta'), params.id])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'encuesta.label', default: 'Encuesta'), params.id])
			redirect(action: "show", id: params.id)
		}
	}
	
	def asignarMedicos()
	{
		def idEncuesta = params.idEncuesta
		def encuestaInstance = Encuesta.get(idEncuesta)
		if ( !encuestaInstance.estadoEncuesta.equals(EstadoEncuesta.BORRADOR) )
		{
			flash.message = message(code: 'encuesta.no.editar.error', args: [encuestaInstance.estadoEncuesta])
			redirect(action: "show", id: encuestaInstance.id)
			return
		}
		
		[idEncuesta:idEncuesta]
	}
	
	def procesarArchivoAsignacionMedicos()
	{
		def app_path = request.getSession().getServletContext().getRealPath("/")
		//def filename = request.getHeader("X-File-Name")
		def file = request.getFile("qqFile")
		def filename = file.getOriginalFilename()
		def isXLS = utilsService.isXLS(filename)
		def idEncuesta = params.idEncuesta
		
		if ( isXLS )
		{
			/* IF AJAX REQUEST
			def destinationDirectory = app_path + grailsApplication.config.asignacionMedicoFilesPath + filename
			def InputStream is
			def FileOutputStream fos
			
			
			try {
				is = request.getInputStream();
				fos = new FileOutputStream(new File(copyTo));
				IOUtils.copy(is, fos);
				
				Map result = asignacionMedicosService.getMatriculasList(idEncuesta, app_path, filename)
	
			} catch (IOException ex) {

			}
			finally {
				try {
					fos.close();
					is.close();
				} catch (IOException ignored) {}
			}*/
			
			def destinationDirectory = grailsApplication.config.asignacionMedicoFilesPath
			def filenamePath = fileUploadService.uploadFile(file, filename, destinationDirectory)
			
			Map result = asignacionMedicosService.asignarMedicosAEncuesta(idEncuesta, filenamePath)
			if (result.error)
			{
				flash.message = message(code: result.error)
				redirect(action: "asignarMedicos", params: params)
			}
			else
			{
				response.contentType = 'text/plain' // or whatever content type your resources are
				response.setHeader "Content-disposition" , "attachment; filename=Asignar_Medicos.log"
				response.outputStream << result.logging
				response.outputStream.flush()
			}
			
		}
		else
		{
			flash.message = message(code: 'asignacion.medico.archivo.no.xls')
			redirect(action: "asignarMedicos", params: [idEncuesta: idEncuesta])
		}
	}
	
	def exportMinutas()
	{
		def encuestaInstance = Encuesta.get(params.id)
		def List<EncuestaAsignada> listado = EncuestaAsignada.getListadoEncuestaMedicosMinuta(encuestaInstance)
		
		def titles =
			[
				Medico.MATRICULA_NACIONAL_LABEL,
				Medico.MATRICULA_PROVINCIAL_LABEL,
				Medico.MATRICULA_ESPECIAL_LABEL,
				Medico.NOMBRE_LABEL,
				Medico.ESPECIALIDAD_LABEL,
				EncuestaAsignada.MINUTA_LABEL
			]
		def file = excelExporterService.createMedicoMinutaXLSFile(listado, titles)
		
		def fileName = encuestaInstance.nombre.replaceAll(" ", "_") 
		fileName = fileName + "_minutas.xls"
		
		response.setHeader('Content-disposition', "attachment;filename=${fileName}")
		response.setHeader('Content-length', "${file.size()}")

		OutputStream out = new BufferedOutputStream(response.outputStream)

		try {
			out.write(file.bytes)

		} finally {verEstadisticas
			out.close()
			return false
		}
	}
	
	def procesoEstadisticas( encuestaInstance )
	{
		encuestaInstance.preguntas.each { Pregunta p ->
			p.respuestas.each { Respuesta r ->
				List<EncuestaAsignada> encuestaAsignadas = EncuestaAsignada.createCriteria().list{
					respuestas{
						eq('id', r.id)
					}
				}
				
				def _size = encuestaAsignadas.size()
				r.cantidadRespondida = _size
			}
		}
		
		encuestaInstance.encuestaEncuestasAsignadas.each { EncuestaAsignada ea ->
			Medico m = ea.medico
			def BigDecimal montoPromedio = 0
			def BigDecimal totalMonto = 0
			
			def List<Derivaciones> listaDerivaciones = Derivaciones.findAllByMedico(m)
			if ( listaDerivaciones && listaDerivaciones.size() > 0 )
			{
				listaDerivaciones.each{ Derivaciones d ->
					totalMonto = totalMonto + d.monto
				}
				montoPromedio = totalMonto / listaDerivaciones.size()
			}
			
			m.montoPromedioDerivado = montoPromedio
		}
		
		def Map map = utilsService.getUltimoMesYAnioDeDerivaciones()
		encuestaInstance.encuestaEncuestasAsignadas.each { EncuestaAsignada ea ->
			Medico m = ea.medico
			DateTime now = new DateTime(map.get("anio"),map.get("mes"), 1, 0, 0)
			DateTime nowLess3 = now.minusMonths(2) // Incluye ya el ultimo mes por eso es 2 mas para atras aparte del ultimo
			def List<BigDecimal> histrogam = new ArrayList<BigDecimal>()
			
			def List<Derivaciones> listaDerivaciones = Derivaciones.createCriteria().list{
				between("month", nowLess3.getMonthOfYear(), now.getMonthOfYear())
				and {
					between("year", nowLess3.getYear(), now.getYear())
				}
				and{
					medico{
						eq('id', m.id)
					}
				}
			}
			
			if ( listaDerivaciones && listaDerivaciones.size() > 0 )
			{
				listaDerivaciones.each{ Derivaciones d ->
					histrogam.add(d.monto)
				}
				
				m.histrogramData = histrogam
			}
		}
	}
	
	
	def verEstadisticas()
	{
		def encuestaInstance = Encuesta.get(params.id)
		procesoEstadisticas(encuestaInstance)
		
		def encuestaAsignadaList = encuestaInstance.encuestaEncuestasAsignadas
		
		[encuestaInstance: encuestaInstance, encuestaAsignadaList: encuestaAsignadaList]
	}
	
	def filtrarMedicos()
	{
		def idEncuesta = params.idEncuesta
		def idVisitador = params.encuestaAsignada.medico.visitador
		
		def encuestaInstance = Encuesta.get(idEncuesta)
		def visitador = Visitador.get(idVisitador)
		
		procesoEstadisticas(encuestaInstance)
		
		def listMedicos = visitador.medicosAsignados
		def encuestaAsignadaList = EncuestaAsignada.findAllByEncuestaAndMedicoInList(encuestaInstance, listMedicos)

		render( view:'verEstadisticas', model:[ encuestaInstance: encuestaInstance, encuestaAsignadaList: encuestaAsignadaList] )
	}
}

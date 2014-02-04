package com.cdrossi

import java.util.List;
import java.util.Map;
import org.grails.datastore.mapping.validation.ValidationException
import org.apache.commons.logging.*

class AsignacionMedicosService {
	
	static String MEDICO_NO_EXISTE_ERROR		= "MEDICO NO EXISTE EN LA BASE DE MEDICOS"
	static String MEDICO_EXISTE_ERROR			= "MEDICO DUPLICADO"
	
	private static Log log = LogFactory.getLog(AsignacionMedicosService.class.getName())
	
	def excelImportService
	def grailsApplication
	
    def Map asignarMedicosAEncuesta(idEncuesta, filename) {	
		log.info "Dentro del metodo [asignarMedicosAEncuesta]"
		
		def logDate
		def line = ""
		def path
		def int pos = 1
		
		try
		{
			AsignacionMedicoExcelImporter importer = new AsignacionMedicoExcelImporter(excelImportService, filename)
			List matriculasList = importer.getMatriculasList()
			
			Encuesta encuestaInstance = Encuesta.get(idEncuesta)
			
			//Borramos todos los medicos asignados a esta encuesta. Para que puedan volver a asignarse nuevamente los medicos a traves del archivo.
			def query = EncuestaAsignada.where {
				encuesta == encuestaInstance
			}
			int total = query.deleteAll()
			
			log.info "Se eleminaron ${total} medicos de la encuesta ${encuestaInstance.nombre}"
			
			matriculasList.each { Map element ->
				pos++
				logDate = new Date().format('dd/MM/yyyy hh:mm')
				
				def matriculaNacional = element.get(Medico.MATRICULA_NACIONAL_LABEL)
				def matriculaProvincial = element.get(Medico.MATRICULA_PROVINCIAL_LABEL)
				def matriculaEspecial = element.get(Medico.MATRICULA_ESPECIAL_LABEL)
				
				Medico medicoInstance = Medico.getMedicoByMatriculas(matriculaNacional, matriculaProvincial, matriculaEspecial)
				if ( medicoInstance )
				{
					EncuestaAsignada encuestaAsignadaInstance = EncuestaAsignada.findByEncuestaAndMedico(encuestaInstance, medicoInstance)
					if ( encuestaAsignadaInstance )
					{
						line = line + "["+logDate+"] - [#"+ pos + "] - "+ MEDICO_EXISTE_ERROR +"\n"
						log.info line
					}
					else
					{
						encuestaAsignadaInstance = new EncuestaAsignada(
							tipoEncuestaAsignada: TipoEncuestaAsignada.PENDIENTE,
							medico: medicoInstance,
							encuesta: encuestaInstance
						)
						
						encuestaAsignadaInstance.save()
						medicoInstance.addToMedicoEncuestasAsignadas(encuestaAsignadaInstance).save()
						encuestaInstance.addToEncuestaEncuestasAsignadas(encuestaAsignadaInstance).save()
						
						log.info "La encuesta [${encuestaInstance.nombre}] fue asignada al Medico [${medicoInstance.nombreCompleto}]"
					}
				}
				else
				{
					line = line + "["+logDate+"] - [#"+ pos + "] - "+ MEDICO_NO_EXISTE_ERROR +"\n"
					
					log.info line
				}
			}
	
			[logging: line]
		}
		catch( Exception e )
		{
			[error: "error.read.file"]
		}
    }
	
	def Map asignarMedicosAVisitador(idUsuario, filename) {
		log.info "Dentro del metodo [asignarMedicosAVisitador]"
		
		def logDate
		def line = ""
		def path
		def int pos = 1
		
		try
		{
			AsignacionMedicoExcelImporter importer = new AsignacionMedicoExcelImporter(excelImportService, filename)
			List matriculasList = importer.getMatriculasList()
			
			Visitador usuarioInstance = Usuario.get(idUsuario)
			
			//Borramos todos los medicos asignados al usuario. Para que puedan volver a asignarse nuevamente los medicos a traves del archivo.
			usuarioInstance.medicosAsignados?.clear()
			
			matriculasList.each { Map element ->
				pos++
				logDate = new Date().format('dd/MM/yyyy hh:mm')
				
				def matriculaNacional = element.get(Medico.MATRICULA_NACIONAL_LABEL)
				def matriculaProvincial = element.get(Medico.MATRICULA_PROVINCIAL_LABEL)
				def matriculaEspecial = element.get(Medico.MATRICULA_ESPECIAL_LABEL)
				
				Medico medicoInstance = Medico.getMedicoByMatriculas(matriculaNacional, matriculaProvincial, matriculaEspecial)
				if ( medicoInstance )
				{
					usuarioInstance.addToMedicosAsignados(medicoInstance).save()
					medicoInstance.visitador = usuarioInstance
					medicoInstance.save()
					log.info "El Medico [${medicoInstance.nombreCompleto}] fue asignado al Usuario [${usuarioInstance.nombre}]"
				}
				else
				{
					line = line + "["+logDate+"] - [#"+ pos + "] - "+ MEDICO_NO_EXISTE_ERROR +"\n"
					log.info line
				}
			}
			
			usuarioInstance.save()
	
			[logging: line]
		}
		catch( Exception e )
		{
			[error: "error.read.file"]
		}
	}
}

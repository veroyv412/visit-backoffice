package com.cdrossi

import org.apache.commons.logging.Log;
import org.apache.commons.logging.*

class ImportarDerivacionesService {

	def excelImportService
	def grailsApplication
	def utilsService
	
	private static Log log = LogFactory.getLog(AsignacionMedicosService.class.getName())
	private static String FALTA_DE_DATOS = "No pudo ser importada por faltarle datos"
	private static String MEDICO_NO_EXISTE_ERROR = "El Medico no existe en la base de datos"
	
    def importarDerivaciones( filename, month, year ) {
		log.info "Dentro del metodo [importarDerivaciones]"
		
		try
		{
			ImportarDerivacionExcelImporter importer = new ImportarDerivacionExcelImporter(excelImportService, filename)
			List derivacionesList = importer.getDerivacionesList()
			
			def logDate
			def line = ""
			def path
			def int pos = 1
			def int cantInsertados = 0
			def int cantLoggeados = 0
			
			//Borramos todas las importaciones para ese mes y ese anio
			def query = Derivaciones.where {
				month == month && year == year
			}
			int total = query.deleteAll()
			
			derivacionesList.each { Map element ->
				pos++
				logDate = new Date().format('dd/MM/yyyy hh:mm')
				
				def medico = element.get(ImportarDerivacionExcelImporter.MEDICO_LABEL)
				def especialidad = element.get(ImportarDerivacionExcelImporter.ESPECIALIDAD_LABEL)
				
				def matricula = element.get(ImportarDerivacionExcelImporter.MATRICULA_LABEL)
				def servicio = element.get(ImportarDerivacionExcelImporter.SERVICIO_LABEL)
				def cantEstudios = element.get(ImportarDerivacionExcelImporter.CANT_ESTUDIOS_LABEL)
				def monto = element.get(ImportarDerivacionExcelImporter.MONTO_LABEL)
				def cantPacientes = element.get(ImportarDerivacionExcelImporter.CANT_PACIENTES_LABEL)
				
				if ( validarCamposObligatorios(element) )
				{
					matricula = utilsService.getNumeroMatricula(matricula)
					
					Medico medicoInstance = Medico.getMedicoByMatriculas(matricula, matricula, matricula)
					if ( medicoInstance )
					{
						cantInsertados++
						Derivaciones derivaciones = new Derivaciones()
						derivaciones.medico = medicoInstance
						derivaciones.servicio = Servicio.getServicio(servicio)
						derivaciones.cantEstudios = new Integer(cantEstudios)
						derivaciones.cantPacientes = new Integer(cantPacientes)
						derivaciones.monto = new Double(monto)
						derivaciones.month = new Integer(month)
						derivaciones.year = new Integer(year)
						derivaciones.save()
						
						log.info "El Medico [${medicoInstance.nombreCompleto}] fue derivado"
					}
					else
					{
						cantLoggeados++
						line = line + "["+logDate+"] - [#"+ pos + "] - "+ MEDICO_NO_EXISTE_ERROR +"\n"
						log.info line
					}
				}
				else
				{
					cantLoggeados++
					line = line + "["+logDate+"] - [#"+ pos + "] - "+ FALTA_DE_DATOS +"\n"
					log.info line
				}
			}
			
			[logging: line, cantInsertados: cantInsertados, cantLoggeados: cantLoggeados]
		}
		catch( Exception e )
		{
			[error: "error.read.file"]
		}
    }
	
	def validarCamposObligatorios( element )
	{
		def especialidad = element.get(ImportarDerivacionExcelImporter.ESPECIALIDAD_LABEL)
		def matricula = element.get(ImportarDerivacionExcelImporter.MATRICULA_LABEL)
		def servicio = element.get(ImportarDerivacionExcelImporter.SERVICIO_LABEL)
		def cantEstudios = element.get(ImportarDerivacionExcelImporter.CANT_ESTUDIOS_LABEL)
		def monto = element.get(ImportarDerivacionExcelImporter.MONTO_LABEL)
		def cantPacientes = element.get(ImportarDerivacionExcelImporter.CANT_PACIENTES_LABEL)
		
		if ( especialidad != null && matricula != null && servicio != null && cantEstudios != null  && monto != null && cantPacientes != null)
		{
			return true
		}
		
		return false
	}
}

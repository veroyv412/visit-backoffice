package com.cdrossi

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class DerivacionesController {

	def utilsService
	def importarDerivacionesService
	def fileUploadService
	def grailsApplication
	
    def index() {
		redirect(action: "leerDerivaciones", params: params) 
	}
	
	def leerDerivaciones()
	{
		def date = new Date()
		def month = date.getAt(Calendar.MONTH) + 1
		def year = date.getAt(Calendar.YEAR)
		
		[currentMonth: month, currentYear: year]
	}
	
	def procesarDerivaciones()
	{
		def file = request.getFile("qqFile")
		def month = new Integer(params.mes)
		def year =  new Integer(params.anio)
		def filename = file.getOriginalFilename()
		def isXLS = utilsService.isXLS(filename)
		
		if ( isXLS )
		{
			def destinationDirectory = grailsApplication.config.importarDerivacionesFileParh
			def filenamePath = fileUploadService.uploadFile(file, filename, destinationDirectory)
			
			def Map info = importarDerivacionesService.importarDerivaciones(filenamePath, month, year)
			if (info.error)
			{
				flash.message = message(code: info.error)
				redirect(action: "leerDerivaciones")
			}
			else
			{
				session.importarDerivacionesLog = info.logging
				[logging: info.logging, cantInsertados: info.cantInsertados, cantLoggeados: info.cantLoggeados]
			}
		}
		else
		{
			flash.message = message(code: 'importar.derivaciones.archivo.no.xls')
			redirect(action: "leerDerivaciones")
		}
	}
	
	def validateImportacionParaPeriodoSeleccionado()
	{
		def month = new Integer(params.mes)
		def year =  new Integer(params.anio)
		
		def derivacion = Derivaciones.getDerivacionByMonthYear(month, year);
		if ( derivacion )
		{
			render([error: true] as JSON)
			return
		}
		
		render([success: true] as JSON)
		return
	}
	
	def descargarLog()
	{
		def logging = session.importarDerivacionesLog
		
		response.contentType = 'text/plain' // or whatever content type your resources are
		response.setHeader "Content-disposition" , "attachment; filename=Importar_Derivaciones.log"
		response.outputStream << logging
		response.outputStream.flush()
	}
}

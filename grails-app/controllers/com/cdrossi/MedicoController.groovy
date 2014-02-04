package com.cdrossi

import grails.converters.*
import grails.plugins.springsecurity.Secured

import org.grails.plugin.filterpane.FilterPaneUtils

@Secured(['ROLE_ADMIN'])
class MedicoController {

	def filterPaneService
	def medicoDerivacionesCSVReaderService
	def grailsApplication
	def fileUploadService
	def utilsService
	def excelExporterService
	def estadisticasMedicoService
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
		estadisticasMedicoService.procesarEstadisticasMedicos()
		redirect(action: "list", params: params)
    }

    def list() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [medicoInstanceList: Medico.list(params), medicoCount: Medico.count(), filterParams: FilterPaneUtils.extractFilterParams(params) ]
    }
	
	def listJSON() {
		render Medico.list() as JSON
	}

	def filter = {
		if(!params.max) params.max = 10
		render( view:'list', model:[ medicoInstanceList: filterPaneService.filter( params, Medico ), medicoCount: filterPaneService.count( params, Medico ), filterParams: FilterPaneUtils.extractFilterParams(params), params:params ] )
	}
	
    def show() {
        def medicoInstance = Medico.get(params.id)
		
		if (!medicoInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'medico.label', default: 'Medico'), params.id])
            redirect(action: "list")
            return
        }

		[medicoInstance: medicoInstance]
    }
	
	def importarMedicos()
	{
		render(view: "leerDerivaciones")
	}
	
	def procesarDerivaciones()
	{
		def file = request.getFile("qqFile")
		def filename = file.getOriginalFilename()
		def isCSV = utilsService.isCSV(filename)
		
		if ( isCSV )
		{
			def destinationDirectory = grailsApplication.config.derivacionesMedicoFilesPath
			def filenamePath = fileUploadService.uploadFile(file, filename, destinationDirectory)
			
			def Map info = medicoDerivacionesCSVReaderService.readFile(filenamePath)
			[info: info]
		}
		else
		{
			flash.message = message(code: 'medico.derivaciones.archivo.no.csv')
			redirect(action: "importarMedicos")
		}
	}
	
	def exportNotes()
	{
		List<Medico> listMedicos = Medico.list()
		def titles = 
			[
				Medico.MATRICULA_NACIONAL_LABEL,
				Medico.MATRICULA_PROVINCIAL_LABEL,
				Medico.MATRICULA_ESPECIAL_LABEL,
				Medico.NOMBRE_LABEL,
				Medico.ESPECIALIDAD_LABEL,
				Medico.NOTAS_LABEL
			]
		def file = excelExporterService.createMedicoNotaXLSFile(listMedicos, titles)
		
		response.setHeader('Content-disposition', 'attachment;filename=Notas_Medicos.xls')
        response.setHeader('Content-length', "${file.size()}")

        OutputStream out = new BufferedOutputStream(response.outputStream)

        try {
            out.write(file.bytes)

        } finally {
            out.close()
            return false
        }
	}
}

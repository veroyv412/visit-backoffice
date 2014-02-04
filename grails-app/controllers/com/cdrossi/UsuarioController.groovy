package com.cdrossi

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

import org.springframework.dao.DataIntegrityViolationException
import org.apache.commons.io.IOUtils;

@Secured(['ROLE_ADMIN'])
class UsuarioController {

	def grailsApplication
	def asignacionMedicosService
	def utilsService
	def springSecurityService
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [usuarioInstanceList: Usuario.list(params), usuarioInstanceTotal: Usuario.count()]
    }

    def create() {
        [usuarioInstance: new Usuario(params)]
    }

    def save() {
        def usuarioInstance
		if ( params.tipoUsuario.equals("Visitador") )
		{
			usuarioInstance = new Visitador()
		}
		else
		{
			usuarioInstance = new Supervisor()
		}
		
		usuarioInstance.nombre = params.nombre
		usuarioInstance.apellido = params.apellido
		usuarioInstance.contrasenia = params.contrasenia
		usuarioInstance.email = params.email
		
		if (!usuarioInstance.save(flush: true)) 
		{
			render(view: "create", model: [usuarioInstance: usuarioInstance])
            return
        }

		if ( params.tipoUsuario.equals("Visitador") )
		{
			if ( params.filename )
			{
				def app_path = request.getSession().getServletContext().getRealPath("/")
				def destinationDirectory = app_path + grailsApplication.config.asignacionVisitadorFileParh + params.filename
				
				Map result = asignacionMedicosService.asignarMedicosAVisitador(usuarioInstance.id, destinationDirectory)

				if (result.error)
				{
					flash.message = message(code: result.error)
					redirect(action: "edit", id: usuarioInstance.id)
					return 
				}
				else
				{
					if ( !result.logging.equals("") )
					{
						redirect(action: "envioLog", params:[logging: result.logging])
						return
					}
					else
					{
						flash.message = message(code: 'default.created.message', args: [message(code: 'usuario.label', default: 'Usuario')])
						redirect(action: "show", id: usuarioInstance.id)
						return
					}
				}
			}
		}
		
		flash.message = message(code: 'default.created.message', args: [message(code: 'usuario.label', default: 'Usuario')])
		redirect(action: "show", id: usuarioInstance.id)
		return
        
    }
	
	def envioLog()
	{
		def logging = params.logging
		response.contentType = 'text/plain' // or whatever content type your resources are
		response.setHeader "Content-disposition" , "attachment; filename=Asignar_Medicos.log"
		response.outputStream << logging
		response.outputStream.flush()
	}
	
    def show() {
        def usuarioInstance = Usuario.get(params.id)
        if (!usuarioInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])
            redirect(action: "list")
            return
        }

        [usuarioInstance: usuarioInstance]
    }

    def edit() {
        def usuarioInstance = Usuario.get(params.id)
		
		def flag = false
		if ( usuarioInstance.tipoUsuario == TipoUsuario.VISITADOR )
		{
			flag = usuarioInstance.tieneMedicosConEncuestasAsignadasPendientes()
			if ( flag )
			{
				flash.message = message(code: 'usuario.no.editar.error' )
				redirect(action: "show", id: usuarioInstance.id)
				return
			}
		}
		
        if (!usuarioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])
            redirect(action: "list")
            return
        }

        [usuarioInstance: usuarioInstance]
    }

    def update() {
        def usuarioInstance = Usuario.get(params.id)
        if (!usuarioInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])
            redirect(action: "list")
            return
        }

		if (params.version) {
            def version = params.version.toLong()
            if (usuarioInstance.version > version) {
                usuarioInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'usuario.label', default: 'Usuario')] as Object[],
                          "Another user has updated this Usuario while you were editing")
                render(view: "edit", model: [usuarioInstance: usuarioInstance])
                return
            }
        }

        usuarioInstance.properties = params

        if (!usuarioInstance.save(flush: true)) {
            render(view: "edit", model: [usuarioInstance: usuarioInstance])
            return
        }
		
		if ( usuarioInstance.tipoUsuario == TipoUsuario.VISITADOR )
		{
			if ( params.filename )
			{
				def app_path = request.getSession().getServletContext().getRealPath("/")
				def destinationDirectory = app_path + grailsApplication.config.asignacionVisitadorFileParh + params.filename
				
				Map result = asignacionMedicosService.asignarMedicosAVisitador(usuarioInstance.id, destinationDirectory)
				if (result.error)
				{
					flash.message = message(code: result.error)
					redirect(action: "edit", id: usuarioInstance.id)
					return
				}
				else
				{
					if ( !result.logging.equals("") )
					{
						redirect(action: "envioLog", params:[logging: result.logging])
						return
					}
					else
					{
						flash.message = message(code: 'default.created.message', args: [message(code: 'usuario.label', default: 'Usuario')])
						redirect(action: "show", id: usuarioInstance.id)
						return
					}
				}
			}
		} else {
			// Vuelvo a autenticar por si cambio el nombre / apellido
			springSecurityService.reauthenticate usuarioInstance.email
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuarioInstance.id])
        redirect(action: "show", id: usuarioInstance.id)
		return 
    }

    def delete() {
        def usuarioInstance = Usuario.get(params.id)
        if (!usuarioInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])
            redirect(action: "list")
            return
        }

        try {
            //usuarioInstance.delete(flush: true)
			usuarioInstance.enabled = false
			usuarioInstance.save()
			
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
	
	def subirAsigancionMedicosFile()
	{
		def app_path = request.getSession().getServletContext().getRealPath("/")
		def filename = request.getHeader("X-File-Name")
		def isXLS = utilsService.isXLS( filename )
		
		if ( isXLS )
		{
			def destinationDirectory = app_path + grailsApplication.config.asignacionVisitadorFileParh + filename
			def InputStream is
			def FileOutputStream fos

			try {
				is = request.getInputStream()
				fos = new FileOutputStream(new File(destinationDirectory))
				IOUtils.copy(is, fos)
	
			} catch (IOException ex) {}
			finally {
				try {
					fos.close();
					is.close();
				} catch (IOException ignored) {}
			}
			
			render([success: true, filename: filename] as JSON)
			return
		}
		
		render([success: false, error: message(code: 'asignacion.medico.archivo.no.xls')] as JSON)
		return
	}
}

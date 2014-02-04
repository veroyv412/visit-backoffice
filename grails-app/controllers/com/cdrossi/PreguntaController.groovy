package com.cdrossi

import grails.converters.JSON
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class PreguntaController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
		def idEncuesta = params.idEncuesta
		def encuesta = Encuesta.get(idEncuesta)
		def preguntaInstanceList = encuesta ? Pregunta.findAllByEncuesta(encuesta, [sort: "orden", order: "asc"]) : []
        [preguntaInstanceList: preguntaInstanceList, preguntaInstanceTotal: Pregunta.count(), idEncuesta: idEncuesta]
    }

    def create() {
		def idEncuesta = params.idEncuesta
        [preguntaInstance: new Pregunta(params), idEncuesta: idEncuesta]
    }

    def save() {
		def preguntaInstance = new Pregunta(params)
		
		if ( !preguntaInstance.hasErrors() && params.listaRespuestas.trim().equals("") )
		{
			flash.message = message(code: 'encuesta.preguntas.respuestas.vacias.error')
			render(view: "create", model: [preguntaInstance: preguntaInstance])
			return
		}
		
		if (!preguntaInstance.save(flush: true)) {
            render(view: "create", model: [preguntaInstance: preguntaInstance])
            return
        }
		
		def respuestasValue = params.listaRespuestas
		List<Respuesta> respuestas = new ArrayList<Respuesta>()
		
		List<String> list = Arrays.asList(respuestasValue.split("\n"))
		
		def pos = 0
		for (String answer : list) {
			pos++
			def Respuesta r = new Respuesta(valor: answer, pregunta: preguntaInstance, orden: pos)
			respuestas.add(r);
		}
		
		preguntaInstance.respuestas = respuestas
		
		def idEncuesta = params.idEncuesta
		def encuesta = Encuesta.get(idEncuesta)
		preguntaInstance.encuesta = encuesta;
		preguntaInstance.orden = preguntaInstance.getOrderForInsert()
		
		encuesta.addToPreguntas(preguntaInstance)
		encuesta.save()
		preguntaInstance.save()

		flash.message = message(code: 'default.created.message', args: [message(code: 'pregunta.label', default: 'Pregunta')])
        redirect(action: "show", id: preguntaInstance.id, params:[idEncuesta: idEncuesta])
    }

    def show() {
		def idEncuesta = params.idEncuesta
        def preguntaInstance = Pregunta.get(params.id)
		
        if (!preguntaInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'pregunta.label', default: 'Pregunta'), params.id])
            redirect(action: "list")
            return
        }

        [preguntaInstance: preguntaInstance, idEncuesta: idEncuesta]
    }

    def edit() {
		def preguntaInstance = Pregunta.get(params.id)
		def idEncuesta = preguntaInstance.encuesta.id
        if (!preguntaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pregunta.label', default: 'Pregunta'), params.id])
            redirect(action: "list")
            return
        }

        [preguntaInstance: preguntaInstance, idEncuesta: idEncuesta]
    }

    def update() {
        def preguntaInstance = Pregunta.get(params.id)
        if (!preguntaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pregunta.label', default: 'Pregunta'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (preguntaInstance.version > version) {
                preguntaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'pregunta.label', default: 'Pregunta')] as Object[],
                          "Another user has updated this Pregunta while you were editing")
                render(view: "edit", model: [preguntaInstance: preguntaInstance])
                return
            }
        }

        preguntaInstance.properties = params
		def query = Respuesta.where {
			pregunta == preguntaInstance
		}
		query.deleteAll()
		preguntaInstance.respuestas = null
		
		def respuestasValue = params.listaRespuestas
		List<Respuesta> respuestas = new ArrayList<Respuesta>()
		
		List<String> list = Arrays.asList(respuestasValue.split("\n"))
		
		def pos = 0
		for (String answer : list) {
			pos++
			def Respuesta r = new Respuesta(valor: answer, pregunta: preguntaInstance, orden: pos)
			respuestas.add(r);
		}
		
		preguntaInstance.respuestas = respuestas

        if (!preguntaInstance.save(flush: true)) {
            render(view: "edit", model: [preguntaInstance: preguntaInstance])
            return
        }

		def idEncuesta = preguntaInstance.encuesta.getId()
		
		flash.message = message(code: 'default.updated.message', args: [message(code: 'pregunta.label', default: 'Pregunta'), preguntaInstance.id])
        redirect(action: "show", id: preguntaInstance.id, params: [idEncuesta: idEncuesta] )
    }
	
	def updateOrder()
	{
		def p = params
		def o = JSON.parse(p.order)
		
		for ( e in o ) {
			def idPregunta = e.idPregunta
			def position = e.position
			
			def preguntaInstance = Pregunta.get(idPregunta)
			preguntaInstance.orden = position
			preguntaInstance.save()
		}
		
		render([success: true] as JSON)
	}
}

package com.cdrossi

class Respuesta {
	
	Pregunta pregunta
	String valor
	int orden
	int cantidadRespondida
	
	//This is needed to make the relationship bidirectional
	static belongsTo = [pregunta:Pregunta]
	
	static transients = ['cantidadRespondida']
	
    static constraints = {
		pregunta(nullable: true)
    }
	
	def getOrderForInsert() {
		List list = Respuesta.findAllByPregunta(this.pregunta)
		this.orden = list.toArray().size() + 1
	}
}

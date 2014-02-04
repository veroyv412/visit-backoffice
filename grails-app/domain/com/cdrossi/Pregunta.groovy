package com.cdrossi

class Pregunta {

	String enunciado
	int orden
	TipoPregunta tipoPregunta
	List<Respuesta> respuestas
	Encuesta encuesta
	
	static hasMany = [ respuestas:Respuesta ]
	static belongsTo = [ encuesta:Encuesta ]
	
    static constraints = {
		enunciado (blank:false, nullable:false )
		tipoPregunta (blank:false, nullable:false )
		respuestas bindable: false
		encuesta (nullable: true)
    }
	
	static mapping = {
		respuestas (sort: 'orden', order: 'asc')
	}
	
	def getOrderForInsert() {
		List list = Pregunta.findAllByEncuesta(this.encuesta)
		this.orden = list.toArray().size() + 1
	}
	
	def getRespuestasToString()
	{
		String value = "";
		for (Respuesta respuesta : respuestas) {
			value = value + respuesta.valor + "\n"
		}
		
		value = !value.equals("") ? value.substring(0, value.length()-1) : "";
		
		return value
	}
}

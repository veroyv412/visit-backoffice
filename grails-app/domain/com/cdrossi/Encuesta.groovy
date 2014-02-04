package com.cdrossi

class Encuesta {
	
	EstadoEncuesta estadoEncuesta
	String nombre
	String descripcion
	Date fechaInicio
	Date fechaFin
	List<Pregunta> preguntas
	EstadisticasEncuesta estadisticasEncuesta
	List<EncuestaAsignada> encuestaEncuestasAsignadas
	
	static hasMany = [preguntas: Pregunta, encuestaEncuestasAsignadas: EncuestaAsignada]
	
    static constraints = {
		estadoEncuesta ( nullable:true )
		descripcion ( nullable:true )
		preguntas ( nullable:true )
		estadisticasEncuesta ( nullable:true )
		fechaInicio (nullable:true, validator: { field, encuesta ->
			def fechaFin = encuesta.properties['fechaFin']
			field < fechaFin ? true : ['encuesta.fechaInicio.fechaFin.error']
		})
		fechaFin (nullable: true)
    }
	
}

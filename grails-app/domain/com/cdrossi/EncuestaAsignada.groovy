package com.cdrossi

import java.util.List;

class EncuestaAsignada {
	
	static String MINUTA_LABEL		= "Minuta"
	
	TipoEncuestaAsignada tipoEncuestaAsignada
	Medico medico
	Encuesta encuesta
	List<Respuesta> respuestas
	String minuta
	
	static hasMany = [respuestas:Respuesta]
	static belongsTo = [medico: Medico, encuesta: Encuesta]
	
    static constraints = {
		respuestas (nullable: true)
		medico (nullable: true)
		encuesta (nullable: true)
		minuta (nullable: true)
    }
	
	static mapping = {
		minuta type:"text"
	}
	
	static def getListadoEncuestaMedicosMinuta( enc )
	{
		List<EncuestaAsignada> l = EncuestaAsignada.findAllByEncuestaAndTipoEncuestaAsignada(enc, TipoEncuestaAsignada.ENCUESTADA)
		return l
	}
}

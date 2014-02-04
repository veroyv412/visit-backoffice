package com.cdrossi

class Servicio {

    String nombre
	
    static constraints = {
		nombre (nullable:false, unique:true )
    }
	
	static def getServicio( nombreServicio )
	{
		def servicio = Servicio.findByNombre(nombreServicio)
		if ( !servicio )
		{
			servicio = new Servicio(nombre:nombreServicio)
			servicio.save()
		}
		
		return servicio
	}
}

package com.cdrossi

class Especialidad {

	String nombre
	
    static constraints = {
		nombre (nullable:false, unique:true )
    }
	
	static def getEspecialidad( nombreEspecialidad )
	{
		def especialidad = Especialidad.findByNombre(nombreEspecialidad)
		if ( !especialidad )
		{
			especialidad = new Especialidad(nombre:nombreEspecialidad)
			especialidad.save()
		}
		
		return especialidad
	}
}

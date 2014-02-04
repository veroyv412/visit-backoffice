package com.cdrossi

class Domicilio {
	String direccion
	String codigoPostal
	String localidad
	Provincia provincia
	
	static embedded = ['provincia']
	
	static constraints = {
		direccion(nullable:true)
		codigoPostal(nullable:true)
		localidad(nullable:true)
		provincia(nullable:true)
	}
	
	String toString()
	{
		def dirLabel = direccion?:""
		def codigoLabel = codigoPostal?:""
		def localidadLabel = localidad?:""
		def provinciaLabel = provincia?.nombre?:""
		def toString = dirLabel.concat(" - ").concat(codigoLabel).concat(" - ").concat(localidadLabel).concat(" - ").concat(provinciaLabel)
		return toString
	}
	
	static def getDomicilio( direccionLabel, localidadLabel, provinciaLabel, codigoPostalLabel )
	{
		def provinciaInstance = new Provincia(nombre:provinciaLabel)
		
		def domicilio = new Domicilio(direccion: direccionLabel, codigoPostal: codigoPostalLabel, localidad: localidadLabel, provincia: provinciaInstance)
		
		return domicilio
	}
}

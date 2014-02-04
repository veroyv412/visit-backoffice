package com.cdrossi

class Derivaciones {

	Medico medico
	Servicio servicio
	Integer cantEstudios
	Integer cantPacientes
	Double monto
	Integer month
	Integer year
	
    static constraints = {
    }
	
	static def getDerivacionByMonthYear( month, year )
	{
		def derivacion = Derivaciones.findByMonthAndYear(month, year)
		return derivacion
	}
}

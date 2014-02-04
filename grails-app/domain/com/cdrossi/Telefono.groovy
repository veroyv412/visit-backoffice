package com.cdrossi

class Telefono {
	
	static String NULL_SEPARATOR	= "-"
	
	TipoTelefono tipoTelefono
	String numero
	
	def tipos = [CELULAR: "Cel.", OFICINA: "Oficina", PARTICULAR: "Part."]
	
	static constraints = {
		numero (blank:false, nullable:true)
	}

	static def getListaTelefonos(telConsultorio, telParticular, telCelular )
	{
		def telConsultorioInstance
		def telParticularInstance
		def telCelularInstance
		def List<Telefono> telefonos = new ArrayList<Telefono>()
		
		if ( !telConsultorio.equals(NULL_SEPARATOR) )
		{
			telConsultorioInstance = Telefono.findByNumero(telConsultorio)
			
			if ( !telConsultorioInstance )
			{
				telConsultorioInstance = new Telefono(tipoTelefono: TipoTelefono.CONSULTORIO, numero:telConsultorio)
				telConsultorioInstance.save()
			}
			
			telefonos.add(telConsultorioInstance)
		}
		
		if ( !telParticular.equals(NULL_SEPARATOR) )
		{
			telParticularInstance = Telefono.findByNumero(telParticular)
			
			if ( !telParticularInstance )
			{
				telParticularInstance = new Telefono(tipoTelefono: TipoTelefono.PARTICULAR, numero:telParticular)
				telParticularInstance.save()
			}
			
			telefonos.add(telParticularInstance)
		}
		
		if ( !telCelular.equals(NULL_SEPARATOR) )
		{
			telCelularInstance = Telefono.findByNumero(telCelular)
			
			if ( !telCelularInstance )
			{
				telCelularInstance = new Telefono(tipoTelefono: TipoTelefono.CELULAR, numero:telCelular)
				telCelularInstance.save()
			}
			
			telefonos.add(telCelularInstance)
		}
		
		return telefonos

	}
}

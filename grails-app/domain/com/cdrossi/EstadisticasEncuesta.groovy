package com.cdrossi

class EstadisticasEncuesta {
	
	BigDecimal porcentajeEncuestado
	BigDecimal porcentajeCancelado
	BigDecimal porcentajePendiente
	BigDecimal cantVisitasSemCupo
	BigDecimal canVisitasSemRealizadas
	BigDecimal cantVisitadasCanceladas
	BigDecimal cantDerivacionesRealizadas
	Encuesta encuesta
	
	static belongsTo = [encuesta: Encuesta]
	
    static constraints = {
		porcentajeEncuestado (nullable:true )
		porcentajeCancelado (nullable:true )
		porcentajePendiente (nullable:true )
		cantVisitasSemCupo (nullable:true )
		canVisitasSemRealizadas (nullable:true )
		cantVisitadasCanceladas (nullable:true )
		cantDerivacionesRealizadas (nullable:true )
		encuesta (nullable:true )
    }
	
	static def getEstadisticasEncuesta( Encuesta e )
	{
		def estadisticasEncuesta = e.estadisticasEncuesta
		if ( !estadisticasEncuesta )
		{
			estadisticasEncuesta = new EstadisticasEncuesta()
			estadisticasEncuesta.encuesta = e
			estadisticasEncuesta.save()
			e.estadisticasEncuesta = estadisticasEncuesta
			e.save()
		}
		
		return estadisticasEncuesta
	}
}

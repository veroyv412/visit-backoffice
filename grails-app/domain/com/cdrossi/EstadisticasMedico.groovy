package com.cdrossi

import grails.converters.JSON

class EstadisticasMedico {
	
	BigDecimal derivacionesHistoricas
	BigDecimal derivacionesUltimosTresMeses
	BigDecimal derivacionesUltimoMes
	BigDecimal facturacionHistoricas
	BigDecimal facturacionUltimosTresMeses
	BigDecimal facturacionUltimoMes
	String actividadUltimosTresMeses
	String cantidadPrestacionesPorServicioUltimosTresMeses
	 
	Medico medico
	
	static belongsTo = [medico: Medico]
	
    static constraints = {
		derivacionesHistoricas (nullable:true )
		derivacionesUltimosTresMeses (nullable:true )
		derivacionesUltimoMes (nullable:true )
		facturacionHistoricas (nullable:true )
		facturacionUltimosTresMeses (nullable:true )
		facturacionUltimoMes (nullable:true )
		actividadUltimosTresMeses (nullable:true )
		cantidadPrestacionesPorServicioUltimosTresMeses (nullable:true )
	}
	
	static def getEstadisticasMedico( Medico medico )
	{
		def estadisticasMedico = medico.estadisticasMedico
		if ( !estadisticasMedico )
		{
			estadisticasMedico = new EstadisticasMedico()
			estadisticasMedico.medico = medico
			estadisticasMedico.save()
			medico.estadisticasMedico = estadisticasMedico
			medico.save()
		}
		
		return estadisticasMedico
	}
}

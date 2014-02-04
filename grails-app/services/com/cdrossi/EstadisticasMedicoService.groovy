package com.cdrossi

import grails.converters.JSON
import groovy.json.JsonBuilder;

import java.util.Map;
import org.joda.time.DateTime
import org.joda.time.Period

class EstadisticasMedicoService {

	def utilsService
	def mapUltimoPeriodo
	
    def procesarEstadisticasMedicos() 
	{
		List<Medico> listMedicos = Medico.list()
		listMedicos.each{ Medico e ->
			procesarEstadisticasMedico(e)
		}
    }
	
	def procesarEstadisticasMedico( Medico medico )
	{
		mapUltimoPeriodo = utilsService.getUltimoMesYAnioDeDerivaciones()
		
		BigDecimal derivacionesHistoricas = derivacionesHistoricas( medico )
		BigDecimal derivacionesUltimosTresMeses = derivacionesUltimosTresMeses( medico )
		BigDecimal derivacionesUltimoMes = derivacionesUltimoMes( medico )
		BigDecimal facturacionHistoricas = facturacionHistoricas( medico )
		BigDecimal facturacionUltimosTresMeses = facturacionUltimosTresMeses( medico )
		BigDecimal facturacionUltimoMes = facturacionUltimoMes( medico )
		Map actividadUltimosTresMeses = actividadUltimosTresMeses( medico )
		def actividadUltimosTresMesesJSON = actividadUltimosTresMeses as JSON
		def actividadUltimosTresMesesString  = actividadUltimosTresMesesJSON.toString()
		
		List<Map> cantidadPrestacionesPorServicioUltimosTresMeses = cantidadPrestacionesPorServicioUltimosTresMeses( medico )
		def cantidadPrestacionesPorServicioUltimosTresMesesJSON = cantidadPrestacionesPorServicioUltimosTresMeses as JSON
		def cantidadPrestacionesPorServicioUltimosTresMesesString = cantidadPrestacionesPorServicioUltimosTresMesesJSON.toString() 
		
		EstadisticasMedico em = EstadisticasMedico.getEstadisticasMedico(medico)
		em.derivacionesHistoricas = derivacionesHistoricas
		em.derivacionesUltimosTresMeses = derivacionesUltimosTresMeses
		em.derivacionesUltimoMes = derivacionesUltimoMes
		em.facturacionHistoricas = facturacionHistoricas
		em.facturacionUltimosTresMeses = facturacionUltimosTresMeses
		em.facturacionUltimoMes = facturacionUltimoMes
		em.actividadUltimosTresMeses = actividadUltimosTresMesesString
		em.cantidadPrestacionesPorServicioUltimosTresMeses = cantidadPrestacionesPorServicioUltimosTresMesesString
		em.save()
	}
	
	private BigDecimal derivacionesHistoricas( Medico medico )
	{
		def result = Derivaciones.executeQuery(
			"select sum(cantPacientes) as  derivacionesHistoricas " +
			"from Derivaciones where medico_id = :medico_id", [medico_id: medico.id])
		
		def derivacionesHistoricas = result.get(0)
	}
	
	private BigDecimal derivacionesUltimosTresMeses( Medico medico )
	{
		DateTime now = new DateTime(mapUltimoPeriodo.get("anio"),mapUltimoPeriodo.get("mes"), 1, 0, 0)
		DateTime nowLess3 = now.minusMonths(2)
		
		def mes1 = nowLess3.getMonthOfYear()
		def mes2 = now.getMonthOfYear()
		def anio1 = nowLess3.getYear()
		def anio2 = now.getYear()
		
		def result = Derivaciones.executeQuery(
			"select sum(cantPacientes) as  derivacionesHistoricas " +
			"from Derivaciones where medico_id = :medico_id " + 
			"and month between :mes1 and :mes2 " +
			"and year between :anio1 and :anio2", [medico_id: medico.id, mes1: mes1, mes2: mes2, anio1: anio1, anio2: anio2])
		
		def derivacionesUltimosTresMeses = result.get(0)
	}
	
	private BigDecimal derivacionesUltimoMes( Medico medico )
	{
		DateTime lastPeriod = new DateTime(mapUltimoPeriodo.get("anio"),mapUltimoPeriodo.get("mes"), 1, 0, 0)
		DateTime lastPeriodMinus1 = lastPeriod.minusMonths(1)	
		
		def mes1 = lastPeriodMinus1.getMonthOfYear()
		def mes2 = lastPeriod.getMonthOfYear()
		def anio1 = lastPeriodMinus1.getYear()
		def anio2 = lastPeriod.getYear()
		
		def result = Derivaciones.executeQuery(
			"select sum(cantPacientes) as  derivacionesHistoricas " +
			"from Derivaciones where medico_id = :medico_id " +
			"and month between :mes1 and :mes2 " +
			"and year between :anio1 and :anio2", [medico_id: medico.id, mes1: mes1, mes2: mes2, anio1: anio1, anio2: anio2])
		
		def derivacionesUltimosTresMeses = result.get(0)
	}
	
	private facturacionHistoricas( Medico medico )
	{
		def result = Derivaciones.executeQuery(
			"select sum(monto) as  derivacionesHistoricas " +
			"from Derivaciones where medico_id = :medico_id", [medico_id: medico.id])
		
		def facturacionHistoricas = result.get(0)
	}
	
	private facturacionUltimosTresMeses( Medico medico )
	{
		DateTime now = new DateTime(mapUltimoPeriodo.get("anio"),mapUltimoPeriodo.get("mes"), 1, 0, 0)
		DateTime nowLess3 = now.minusMonths(2)
		
		def mes1 = nowLess3.getMonthOfYear()
		def mes2 = now.getMonthOfYear()
		def anio1 = nowLess3.getYear()
		def anio2 = now.getYear()
		
		def result = Derivaciones.executeQuery(
			"select sum(monto) as  derivacionesHistoricas " +
			"from Derivaciones where medico_id = :medico_id " +
			"and month between :mes1 and :mes2 " +
			"and year between :anio1 and :anio2", [medico_id: medico.id, mes1: mes1, mes2: mes2, anio1: anio1, anio2: anio2])
		
		def facturacionUltimosTresMeses = result.get(0)
	}
	
	private facturacionUltimoMes( Medico medico )
	{
		DateTime lastPeriod = new DateTime(mapUltimoPeriodo.get("anio"),mapUltimoPeriodo.get("mes"), 1, 0, 0)
		DateTime lastPeriodMinus1 = lastPeriod.minusMonths(1)
		
		def mes1 = lastPeriodMinus1.getMonthOfYear()
		def mes2 = lastPeriod.getMonthOfYear()
		def anio1 = lastPeriodMinus1.getYear()
		def anio2 = lastPeriod.getYear()
		
		def result = Derivaciones.executeQuery(
			"select sum(monto) as  derivacionesHistoricas " +
			"from Derivaciones where medico_id = :medico_id " +
			"and month between :mes1 and :mes2 " +
			"and year between :anio1 and :anio2", [medico_id: medico.id, mes1: mes1, mes2: mes2, anio1: anio1, anio2: anio2])
		
		def facturacionUltimoMes = result.get(0)
	}
	
	private Map actividadUltimosTresMeses( medico )
	{
		DateTime now = new DateTime(mapUltimoPeriodo.get("anio"),mapUltimoPeriodo.get("mes"), 1, 0, 0)
		DateTime nowLess3 = now.minusMonths(2)
		
		def mes1 = nowLess3.getMonthOfYear()
		def mes2 = now.getMonthOfYear()
		def anio1 = nowLess3.getYear()
		def anio2 = now.getYear()
		
		def result = Derivaciones.executeQuery(
			"SELECT new map(d.month as mes, d.year as anio, sum(d.cantPacientes) as cantidadPrestaciones) " +
			"FROM Derivaciones d " +
			"WHERE medico_id = :medico_id " +
			"and month between :mes1 and :mes2 " +
			"and year between :anio1 and :anio2 " +
			"group by d.month, d.year "+
			"order by d.month, d.year ", [medico_id: medico.id, mes1: mes1, mes2: mes2, anio1: anio1, anio2: anio2])
		
		def Map map = [:]
		def List ticks = []
		def List data = []
		
		result.each{ o ->
			def mes = o.get("mes")
			def anio = o.get("anio")
			def cantidadPrestaciones = o.get("cantidadPrestaciones")
			
			ticks.add(utilsService.getMonthByNumber(mes))
			data.add(cantidadPrestaciones)
		}
		
		map.put("ticks", ticks)
		map.put("data", data)
		
		return map
	}
	
	private List<Map> cantidadPrestacionesPorServicioUltimosTresMeses( Medico medico )
	{
		List<Map> lista = new ArrayList<Map>()
		
		DateTime now = new DateTime(mapUltimoPeriodo.get("anio"),mapUltimoPeriodo.get("mes"), 1, 0, 0)
		DateTime nowLess3 = now.minusMonths(2)
		
		def mes1 = nowLess3.getMonthOfYear()
		def mes2 = now.getMonthOfYear()
		def anio1 = nowLess3.getYear()
		def anio2 = now.getYear()
		
		def result = Derivaciones.executeQuery(
			"SELECT new map(d.servicio as servicio, sum(d.cantPacientes) as cantidadPrestaciones) " +
			"FROM Derivaciones d " + 
			"WHERE medico_id = :medico_id " +
			"and month between :mes1 and :mes2 " +
			"and year between :anio1 and :anio2 " +
			"group by d.servicio", [medico_id: medico.id, mes1: mes1, mes2: mes2, anio1: anio1, anio2: anio2])
		
		result.each{ o ->
			List map = []
			def int cantidadPrestaciones = o.get("cantidadPrestaciones")
			def Servicio servicio = o.get("servicio")
			
			map.add(servicio.nombre + ": " + cantidadPrestaciones)
			map.add(cantidadPrestaciones)
			
			lista.add(map)
		}
		
		def cantidadPrestacionesPorServicioUltimosTresMeses = lista
	}
}

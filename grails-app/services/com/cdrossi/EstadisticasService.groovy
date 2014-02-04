package com.cdrossi

import java.math.MathContext

class EstadisticasService {

	def utilsService
	
	
    def procesarEstadisticasEncuestas() {
		List<Encuesta> listaEncuestas = Encuesta.list()
		
		listaEncuestas.each{ Encuesta e ->
			def cantidadVisitasSemanalesARealizar = cantidadVisitasSemanalesARealizar(e)
			def cantidadVisitasSemanalesRealizadas = cantidadVisitasSemanalesRealizadas(e)
			def cantidadDerivaciones = cantidadDerivaciones(e)
			def cantidadVisitasMedicosCancelados = cantidadVisitasMedicosCancelados(e)
			def porcentajeVisitasMedicosCancelados = porcentajeVisitasMedicosCancelados(e)
			def porcentajeVisitasMedicosEncuestados = porcentajeVisitasMedicosEncuestados(e)
			def porcentajeVisitasMedicosPendientes = 100 - porcentajeVisitasMedicosCancelados - porcentajeVisitasMedicosEncuestados
			
			EstadisticasEncuesta ee = EstadisticasEncuesta.getEstadisticasEncuesta(e)
			ee.porcentajeEncuestado = porcentajeVisitasMedicosEncuestados
			ee.porcentajeCancelado = porcentajeVisitasMedicosCancelados
			ee.porcentajePendiente = porcentajeVisitasMedicosPendientes
			ee.cantVisitasSemCupo = cantidadVisitasSemanalesARealizar
			ee.canVisitasSemRealizadas = cantidadVisitasSemanalesRealizadas
			ee.cantVisitadasCanceladas = cantidadVisitasMedicosCancelados
			ee.cantDerivacionesRealizadas = cantidadDerivaciones
			ee.save()
		}
    }
	
	private def cantidadVisitasSemanalesARealizar( Encuesta e )
	{
		def cantidadSemanas = utilsService.getWeeksBetween(e.fechaInicio, e.fechaFin)
		def cantidadMedicosTotales =  cantidadVisitasMedicosTotales(e)
		def cantidadVisitasSemanalesARealizar = 0
		
		if ( cantidadSemanas > 0 )
		{
			cantidadVisitasSemanalesARealizar = cantidadMedicosTotales / cantidadSemanas
			cantidadVisitasSemanalesARealizar = cantidadVisitasSemanalesARealizar.round (new MathContext(3))
		}
		
		return cantidadVisitasSemanalesARealizar
	}
	
	private def cantidadVisitasSemanalesRealizadas( Encuesta e )
	{
		def cantidadSemanas = utilsService.getWeeksBetween(e.fechaInicio, e.fechaFin)
		
		def cantidadMedicosTotales =  cantidadVisitasMedicosEncuestados(e)
		
		def cantidadVisitasSemanalesRealizadas = 0
		if ( cantidadSemanas > 0 )
		{
			cantidadVisitasSemanalesRealizadas = cantidadMedicosTotales / cantidadSemanas
			cantidadVisitasSemanalesRealizadas = cantidadVisitasSemanalesRealizadas.round (new MathContext(3))
		}
		
		return cantidadVisitasSemanalesRealizadas
	}
	
	private def cantidadDerivaciones(Encuesta e)
	{
		org.joda.time.DateTime t1 = new org.joda.time.DateTime(e.fechaInicio);
		org.joda.time.DateTime t2 = new org.joda.time.DateTime(e.fechaFin);
		
		//ver de ahcer el where con fechas between dates! a ver si se puede y con eso sacar el cpunt
		def List<Derivaciones> listaDerivaciones = Derivaciones.createCriteria().list{
					between("month", t1.getMonthOfYear(), t2.getMonthOfYear())
					and {
						between("year", t1.getYear(), t2.getYear())
					}
		}
		
		def cantidadDerivaciones =  listaDerivaciones.size()
		
		return cantidadDerivaciones
		
	}
	
	private def cantidadVisitasMedicosTotales(Encuesta e)
	{
		def List<EncuestaAsignada> listaMedicosEncuesta = EncuestaAsignada.createCriteria().list{
			eq('encuesta', e)
		}
		
		def cantidadVisitasMedicosTotales = listaMedicosEncuesta.size()
		
		return cantidadVisitasMedicosTotales
	}
	
	private def cantidadVisitasMedicosCancelados(Encuesta e)
	{
		def List<EncuestaAsignada> listaMedicosEncuesta = EncuestaAsignada.createCriteria().list{
			eq('encuesta', e)
			eq('tipoEncuestaAsignada', TipoEncuestaAsignada.CANCELADA)
		}
		
		def cantidadVisitasMedicosCancelados = listaMedicosEncuesta.size()
		
		return cantidadVisitasMedicosCancelados
	}
	
	private def porcentajeVisitasMedicosCancelados(Encuesta e)
	{
		def cantidadVisitasMedicosTotales = cantidadVisitasMedicosTotales(e)
		def cantidadVisitasMedicosCancelados = cantidadVisitasMedicosCancelados(e)
		
		def porcentaje = 0
		if ( cantidadVisitasMedicosTotales > 0 )
		{
			porcentaje = (cantidadVisitasMedicosCancelados * 100) / cantidadVisitasMedicosTotales
			porcentaje = porcentaje.round (new MathContext(3))
		}
		
		return porcentaje
	}
	
	private def cantidadVisitasMedicosPendientes(Encuesta e)
	{
		def List<EncuestaAsignada> listaMedicosEncuesta = EncuestaAsignada.createCriteria().list{
			eq('encuesta', e)
			eq('tipoEncuestaAsignada', TipoEncuestaAsignada.PENDIENTE)
		}
		
		def cantidadVisitasMedicosPendientes = listaMedicosEncuesta.size()
		
		return cantidadVisitasMedicosPendientes
	}
	
	private def porcentajeVisitasMedicosPendientes(Encuesta e)
	{
		def cantidadVisitasMedicosTotales = cantidadVisitasMedicosTotales(e)
		def cantidadVisitasMedicosPendientes = cantidadVisitasMedicosPendientes(e)
		
		def porcentaje = 0
		if ( cantidadVisitasMedicosTotales > 0 )
		{
			porcentaje = (cantidadVisitasMedicosPendientes * 100) / cantidadVisitasMedicosTotales
			porcentaje = porcentaje.round (new MathContext(3))
		}
		
		return porcentaje
	}
	
	private def cantidadVisitasMedicosEncuestados(Encuesta e)
	{
		def List<EncuestaAsignada> listaMedicosEncuesta = EncuestaAsignada.createCriteria().list{
			eq('encuesta', e)
			eq('tipoEncuestaAsignada', TipoEncuestaAsignada.ENCUESTADA)
		}
		
		def cantidadVisitasMedicosEncuestados = listaMedicosEncuesta.size()
		
		return cantidadVisitasMedicosEncuestados
	}
	
	private def porcentajeVisitasMedicosEncuestados(Encuesta e)
	{
		def cantidadVisitasMedicosTotales = cantidadVisitasMedicosTotales(e)
		def cantidadVisitasMedicosEncuestados = cantidadVisitasMedicosEncuestados(e)
		
		def porcentaje = 0
		if ( cantidadVisitasMedicosTotales )
		{
			porcentaje = (cantidadVisitasMedicosEncuestados * 100) / cantidadVisitasMedicosTotales
			porcentaje = porcentaje.round (new MathContext(3))
		}
		
		return porcentaje
	}
}

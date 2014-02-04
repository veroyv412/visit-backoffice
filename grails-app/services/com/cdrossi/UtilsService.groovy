package com.cdrossi

import org.joda.time.Interval
import com.cdrossi.Derivaciones

class UtilsService {

    def isXLS( filename )
	{
		 def returned_value = ""
		 def m = (filename =~ /(\.[^\.]*)$/)
		 if (m.size()>0)
		 {
			 returned_value = ((m[0][0].size()>0) ? m[0][0].substring(1).trim().toLowerCase() : "")
		 }
		return returned_value.toUpperCase().equals("XLS")
	}
	
	def isCSV( filename )
	{
		 def returned_value = ""
		 def m = (filename =~ /(\.[^\.]*)$/)
		 if (m.size()>0)
		 {
			 returned_value = ((m[0][0].size()>0) ? m[0][0].substring(1).trim().toLowerCase() : "")
		 }
		return returned_value.toUpperCase().equals("CSV")
	}
	
	/** Devuelve el numero de Matricula. Sin importar el tipo
	 * 
	 * @param matricula
	 * @return matricula SIN el MN, MP o ME
	 */
	def getNumeroMatricula( matricula )
	{
		def mtr = matricula.substring(2).trim()
	}
	
	/** Obtiene la cantidad de semanas entre 2 fechas
	 * 
	 * @param fechaInicio
	 * @param fechaFin
	 * @return int
	 */
	def int getWeeksBetween(Date fechaInicio, Date fechaFin) {
		org.joda.time.DateTime t2 = new org.joda.time.DateTime(fechaInicio);
		org.joda.time.DateTime t1 = new org.joda.time.DateTime(fechaFin);
		Interval interval = new Interval(t2, t1);  
		int weeksBetween = interval.toPeriod(org.joda.time.PeriodType.weeks()).getWeeks();
		
		return weeksBetween
	}
	
	/**
	 * Obtiene el Ultimo Mes y Anio hecha de las Derivaciones
	 * 
	 * @return Map [mes:x, anio: x]
	 */
	def Map getUltimoMesYAnioDeDerivaciones()
	{
		def query = "SELECT new map(d.month as mes, d.year as anio) FROM Derivaciones d where month = (select max(month) from Derivaciones ) and year = (select max(year) from Derivaciones)"
		def result = Derivaciones.executeQuery(query)
		def firstElement = result.get(0)
		
		def map = [mes: firstElement.get("mes"), anio: firstElement.get("anio")]
	}
	
	def String getMonthByNumber( month )
	{
		def map = [1: 'Ene', 2: 'Feb', 3: 'Mar', 4: 'Abr', 5: 'May', 6: 'Jun', 7: 'Jul', 8: 'Ago', 9:'Sep', 10: 'Oct', 11: 'Nov', 12: 'Dic']
		return map.get(month)
	}
}

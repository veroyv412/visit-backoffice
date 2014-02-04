package com.cdrossi

import org.codehaus.groovy.grails.commons.*
import org.grails.plugins.excelimport.*

class AsignacionMedicoExcelImporter  extends AbstractExcelImporter{
	
	def ExcelImportService excelImportService
	
	static Map CONFIG_ASIGNACION_MEDICO_COLUMN_MAP = [
		sheet:'Sheet1', 
		startRow: 1,
		columnMap: [ 
			'A': Medico.MATRICULA_NACIONAL_LABEL,
			'B': Medico.MATRICULA_PROVINCIAL_LABEL,
			'C': Medico.MATRICULA_ESPECIAL_LABEL,
		 ]
	]
	
	static Map propertyConfigurationMap = [
		(Medico.MATRICULA_NACIONAL_LABEL):([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
		(Medico.MATRICULA_PROVINCIAL_LABEL):([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
		(Medico.MATRICULA_ESPECIAL_LABEL):[expectedType: ExpectedPropertyType.StringType, defaultValue:null],
	]

	
	def AsignacionMedicoExcelImporter(ExcelImportService excelImportService, fileName) {
		super(fileName)
		this.excelImportService = excelImportService
	}
	
	List<Map> getMatriculasList() {
		List matriculasList = excelImportService.columns(workbook, CONFIG_ASIGNACION_MEDICO_COLUMN_MAP, null, propertyConfigurationMap)
	}
}

package com.cdrossi

import java.util.List;
import java.util.Map;
import org.grails.plugins.excelimport.AbstractExcelImporter;
import org.grails.plugins.excelimport.ExcelImportService;
import org.codehaus.groovy.grails.commons.*
import org.grails.plugins.excelimport.*

class ImportarDerivacionExcelImporter extends AbstractExcelImporter{

	static String MEDICO_LABEL				= "Medico"
	static String MATRICULA_LABEL			= "Matricula"
	static String ESPECIALIDAD_LABEL		= "Especialidad"
	static String SERVICIO_LABEL			= "Servicio"
	static String CANT_ESTUDIOS_LABEL		= "Cant. Estudios"
	static String MONTO_LABEL				= "Monto"
	static String CANT_PACIENTES_LABEL		= "Cant. Pacientes"
	
	def ExcelImportService excelImportService
	
	static Map CONFIG_IMPORTAR_DERIVACIONES_COLUMN_MAP = [
		sheet:'Sheet1',
		startRow: 1,
		columnMap: [
			'A': MEDICO_LABEL,
			'B': MATRICULA_LABEL,
			'C': ESPECIALIDAD_LABEL,
			'D': SERVICIO_LABEL,
			'E': CANT_ESTUDIOS_LABEL,
			'F': MONTO_LABEL,
			'G': CANT_PACIENTES_LABEL,
		 ]
	]
	
	static Map propertyConfigurationMap = [
		(MEDICO_LABEL):([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
		(MATRICULA_LABEL):([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
		(ESPECIALIDAD_LABEL):([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
		(SERVICIO_LABEL):([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
		(CANT_ESTUDIOS_LABEL):([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
		(MONTO_LABEL):([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
		(CANT_PACIENTES_LABEL):([expectedType: ExpectedPropertyType.StringType, defaultValue:null]),
	]
	
	def ImportarDerivacionExcelImporter(ExcelImportService excelImportService, fileName) {
		super(fileName)
		this.excelImportService = excelImportService
	}
	
	List<Map> getDerivacionesList() {
		List derivacionesList = excelImportService.columns(workbook, CONFIG_IMPORTAR_DERIVACIONES_COLUMN_MAP, null, propertyConfigurationMap)
	}
}

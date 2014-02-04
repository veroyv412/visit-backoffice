package com.cdrossi

import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.DomainClassArtefactHandler

import jxl.Workbook
import jxl.WorkbookSettings
import jxl.write.Label
import jxl.write.WritableCellFormat
import jxl.write.WritableFont
import jxl.write.WritableSheet
import jxl.write.WritableWorkbook

class ExcelExporterService {

    def createMedicoNotaXLSFile(listado, titles) {
		
		def file = File.createTempFile('tempXLSFile', '.xls')
		file.deleteOnExit()
		
		WorkbookSettings workbookSettings = new WorkbookSettings()
		workbookSettings.locale = Locale.default
		
		WritableWorkbook workbook = Workbook.createWorkbook(file, workbookSettings)
		WritableSheet sheet = workbook.createSheet('Sheet1', 0)
		
		WritableFont fontTitle = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)
		WritableCellFormat formatTitle = new WritableCellFormat(fontTitle)
		
		WritableFont fontContent = new WritableFont(WritableFont.ARIAL, 12)
		WritableCellFormat formatContent = new WritableCellFormat(fontContent)
		
		/* Title */
		titles.eachWithIndex{ title, i ->
			sheet.addCell(new Label(i, 0, title, formatTitle))
		}

		def row = 1
		listado.each { entidad ->
			sheet.addCell(new Label(0, row, entidad.matriculaNacional, formatContent))
			sheet.addCell(new Label(1, row, entidad.matriculaProvincial, formatContent))
			sheet.addCell(new Label(2, row, entidad.matriculaEspecial, formatContent))
			sheet.addCell(new Label(3, row, entidad.nombreCompleto, formatContent))
			sheet.addCell(new Label(4, row, entidad.especialidad.nombre, formatContent))
			sheet.addCell(new Label(5, row, entidad.nota, formatContent))
			
			row++
		}
		
		
		workbook.write();
		workbook.close();
		
		return file
    }
	
	def createMedicoMinutaXLSFile(listado, titles) {
		
		def file = File.createTempFile('tempXLSFile', '.xls')
		file.deleteOnExit()
		
		WorkbookSettings workbookSettings = new WorkbookSettings()
		workbookSettings.locale = Locale.default
		
		WritableWorkbook workbook = Workbook.createWorkbook(file, workbookSettings)
		WritableSheet sheet = workbook.createSheet('Sheet1', 0)
		
		WritableFont fontTitle = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)
		WritableCellFormat formatTitle = new WritableCellFormat(fontTitle)
		
		WritableFont fontContent = new WritableFont(WritableFont.ARIAL, 12)
		WritableCellFormat formatContent = new WritableCellFormat(fontContent)
		
		/* Title */
		titles.eachWithIndex{ title, i ->
			sheet.addCell(new Label(i, 0, title, formatTitle))
		}

		def row = 1
		listado.each { entidad ->
			sheet.addCell(new Label(0, row, entidad.medico.matriculaNacional, formatContent))
			sheet.addCell(new Label(1, row, entidad.medico.matriculaProvincial, formatContent))
			sheet.addCell(new Label(2, row, entidad.medico.matriculaEspecial, formatContent))
			sheet.addCell(new Label(3, row, entidad.medico.nombreCompleto, formatContent))
			sheet.addCell(new Label(4, row, entidad.medico.especialidad.nombre, formatContent))
			sheet.addCell(new Label(5, row, entidad.minuta, formatContent))
			
			row++
		}
		
		
		workbook.write();
		workbook.close();
		
		return file
	}
}

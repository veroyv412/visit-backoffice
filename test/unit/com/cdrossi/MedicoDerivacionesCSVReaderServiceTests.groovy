package com.cdrossi



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(MedicoDerivacionesCSVReaderService)
class MedicoDerivacionesCSVReaderServiceTests {

    void testCantidadColumnasCorrectas() {
        
		def element = 
			[
				"Matrícula Nacional" : "117057", 
				"Matrícula Provincial":"-", 
				"Matricula  Especialidad":"-", 
				"Nombre":"FERNANDEZ, GUILLERMO R.", 
				"Especialidad":"MEDICO", 
				"Sexo":"Masculino", 
				"Domicilio":"-", 
				"Localidad":"CAPITAL FEDERAL", 
				"Provincia":"CIUDAD AUTONOMA DE BS.AS.", 
				"Código Postal":"-", 
				"Teléfono Consultorio":"-", 
				"Teléfono Particular":"-", 
				"Teléfono Celular":"-", 
				"E-mail":"info@paideianet.com.ar"
			]
		
		def result = service.validarCantidadColumnas(element)
		
		assert result == true
    }
	
	void testCantidadColumnasIncorrectas() {
		
		def element =
			[
				"Matrícula Nacional" : "117057",
				"Matrícula Provincial":"-",
				"Matricula  Especialidad":"-",
				"Nombre":"FERNANDEZ, GUILLERMO R.",
				"Especialidad":"MEDICO",
				"Sexo":"Masculino",
				"Domicilio":"-",
				"Localidad":"CAPITAL FEDERAL",
				"Provincia":"CIUDAD AUTONOMA DE BS.AS.",
				"Código Postal":"-",
				"Teléfono Consultorio":"-",
				"Teléfono Particular":"-"
			]
		
		def result = service.validarCantidadColumnas(element)
		
		assert result == false
	}
	
	void testTodasMatriculasVacias() {
		
		def element =
			[
				"Matrícula Nacional" : "-",
				"Matrícula Provincial":"-",
				"Matricula  Especialidad":"-",
				"Nombre":"FERNANDEZ, GUILLERMO R.",
				"Especialidad":"MEDICO",
				"Sexo":"Masculino",
				"Domicilio":"-",
				"Localidad":"CAPITAL FEDERAL",
				"Provincia":"CIUDAD AUTONOMA DE BS.AS.",
				"Código Postal":"-",
				"Teléfono Consultorio":"-",
				"Teléfono Particular":"-"
			]
		
		def result = service.validarSonTodasLasMatriculasVacias(element)
		
		assert result == true
	}
	
	void testMatriculaNacionalNoVacia() {
		
		def element =
			[
				"Matrícula Nacional" : "117057",
				"Matrícula Provincial":"-",
				"Matricula  Especialidad":"-",
				"Nombre":"FERNANDEZ, GUILLERMO R.",
				"Especialidad":"MEDICO",
				"Sexo":"Masculino",
				"Domicilio":"-",
				"Localidad":"CAPITAL FEDERAL",
				"Provincia":"CIUDAD AUTONOMA DE BS.AS.",
				"Código Postal":"-",
				"Teléfono Consultorio":"-",
				"Teléfono Particular":"-"
			]
		
		def result = service.validarSonTodasLasMatriculasVacias(element)
		
		assert result == false
	}
	
	void testMatriculaNacionalYProvincialNoVacia() {
		
		def element =
			[
				"Matrícula Nacional" : "117057",
				"Matrícula Provincial":"117057",
				"Matricula  Especialidad":"-",
				"Nombre":"FERNANDEZ, GUILLERMO R.",
				"Especialidad":"MEDICO",
				"Sexo":"Masculino",
				"Domicilio":"-",
				"Localidad":"CAPITAL FEDERAL",
				"Provincia":"CIUDAD AUTONOMA DE BS.AS.",
				"Código Postal":"-",
				"Teléfono Consultorio":"-",
				"Teléfono Particular":"-"
			]
		
		def result = service.validarSonTodasLasMatriculasVacias(element)
		
		assert result == false
	}
	
	void testFileExtensionCSV()
	{
		def result = service.validarFileExtension("resources/test.csv")
		
		assert result == true
	}
	
	void testFileExtensionNoCSV()
	{
		def result = service.validarFileExtension("resources/test.html")
		
		assert result == false
	}
	
	void testNombreValido()
	{
		def element =
			[
				"Matrícula Nacional" : "117057",
				"Matrícula Provincial":"117057",
				"Matricula  Especialidad":"-",
				"Nombre":"FERNANDEZ, GUILLERMO R.",
				"Especialidad":"MEDICO",
				"Sexo":"Masculino",
				"Domicilio":"-",
				"Localidad":"CAPITAL FEDERAL",
				"Provincia":"CIUDAD AUTONOMA DE BS.AS.",
				"Código Postal":"-",
				"Teléfono Consultorio":"-",
				"Teléfono Particular":"-"
			]
		
		def result = service.validarNombreCompleto(element)
		
		assert result == true
	}
	
	void testNombreInvalido()
	{
		def element =
			[
				"Matrícula Nacional" : "117057",
				"Matrícula Provincial":"117057",
				"Matricula  Especialidad":"-",
				"Nombre":"-",
				"Especialidad":"MEDICO",
				"Sexo":"Masculino",
				"Domicilio":"-",
				"Localidad":"CAPITAL FEDERAL",
				"Provincia":"CIUDAD AUTONOMA DE BS.AS.",
				"Código Postal":"-",
				"Teléfono Consultorio":"-",
				"Teléfono Particular":"-"
			]
		
		def result = service.validarNombreCompleto(element)
		
		assert result == false
	}
}

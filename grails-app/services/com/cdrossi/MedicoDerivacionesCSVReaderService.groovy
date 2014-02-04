package com.cdrossi

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.MessageSource
import grails.validation.ValidationException
import grails.test.mixin.*
import com.cdrossi.Medico
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

@TestFor(MedicoDerivacionesCSVReaderService)
class MedicoDerivacionesCSVReaderService{
	
	def static String NULL_SEPARATOR = "-"
	def static String FILE_EXTENSION = "csv"
	def settings = [charset:'UTF-8']
	def static int CANTIDAD_COLUMNAS = 14
	MessageSource messageSource
	
    def readFile( fileName ) 
	{
		def List errors = new ArrayList()
		def messages = [cantidadActualizados: 0, cantidadInsertados: 0]
		def int cantidadActualizados = 0
		def int cantidadInsertados = 0
		def mapError
		
		def validarExtension = validarFileExtension(fileName)
		
		if ( !validarExtension )
		{
			mapError = [nombre: "", message: "medico.derivaciones.error.archivo.no.es.csv"]
			errors.add(mapError)
		}
		else
		{
			try
			{
				def reader = new File("${fileName}").toCsvMapReader(settings)
				reader.eachWithIndex{ element,i -> 
					def int pos = i+1
					
					def nombre = element.get(Medico.NOMBRE_LABEL)
					def validarNombreCompleto = validarNombreCompleto( element )
					if ( !validarNombreCompleto )
					{
						
						mapError = [nombre: "Medico #" + pos, message: "medico.derivaciones.error.nombre.incompleto"]
						errors.add(mapError)
					}
					else
					{
						def errorCantidadColumnas = validarCantidadColumnas( element )
						if ( !errorCantidadColumnas )
						{
							mapError = [nombre: "Medico #" + pos + " [" +nombre + "]", message: "medico.derivaciones.error.cantidadColumnas"]
							errors.add(mapError)
						}
						else
						{
							def errorSonTodasLasMatriculasVacias = validarSonTodasLasMatriculasVacias( element )
							if ( errorSonTodasLasMatriculasVacias )
							{
								mapError = [nombre: "Medico #" + pos + " [" +nombre + "]", message: "medico.derivaciones.error.matriculaFaltante"]
								errors.add(mapError)
							}
							else
							{
								def matriculaNacional = element.get(Medico.MATRICULA_NACIONAL_LABEL)
								def matriculaProvincial = element.get(Medico.MATRICULA_PROVINCIAL_LABEL)
								def matriculaEspecial = element.get(Medico.MATRICULA_ESPECIAL_LABEL)
								
								def especialidad = element.get(Medico.ESPECIALIDAD_LABEL)
								def especialidadInstance = Especialidad.getEspecialidad(especialidad)
								
								def sexoLabel = element.get(Medico.SEXO_LABEL)
								def sexo = sexoLabel.toUpperCase().equals(Sexo.FEMENINO.toString().toUpperCase()) ? Sexo.FEMENINO : sexoLabel.toUpperCase().equals(Sexo.MASCULINO.toString().toUpperCase()) ? Sexo.MASCULINO : null
								
								def direccion = element.get(Medico.DOMICILIO_LABEL)
								def localidad = element.get(Medico.LOCALIDAD_LABEL)
								def provincia = element.get(Medico.PROVINCIA_LABEL)
								def codigoPostal = element.get(Medico.CODIGO_POSTAL_LABEL)
								
								def domicilioInstance = Domicilio.getDomicilio(direccion, localidad, provincia, codigoPostal)
								
								def telConsultorio = element.get(Medico.TEL_CONSULTORIO_LABEL)
								def telParticular = element.get(Medico.TEL_PARTICULAR_LABEL)
								def telCelular = element.get(Medico.TEL_CELULAR_LABEL)
								
								def List<Telefono> telefonos = Telefono.getListaTelefonos(telConsultorio, telParticular, telCelular)
								
								def email = element.get(Medico.EMAIL_LABEL)
								
								
								def medicoInstance = Medico.getMedicoByMatriculas(matriculaNacional, matriculaProvincial, matriculaEspecial)
								if ( medicoInstance )
								{
									cantidadActualizados++
									medicoInstance.matriculaNacional = matriculaNacional.equals(NULL_SEPARATOR) ? null : matriculaNacional
									medicoInstance.matriculaProvincial = matriculaProvincial.equals(NULL_SEPARATOR) ? null : matriculaProvincial
									medicoInstance.matriculaEspecial = matriculaEspecial.equals(NULL_SEPARATOR) ? null : matriculaEspecial
									medicoInstance.nombreCompleto = nombre
									medicoInstance.email = email
									medicoInstance.especialidad = especialidadInstance
									medicoInstance.domicilio = domicilioInstance
									medicoInstance.telefonos = telefonos
									medicoInstance.sexo = sexo
									medicoInstance.estadoMedico = EstadoMedico.ACTIVO
								}
								else
								{
									cantidadInsertados++
									medicoInstance = new Medico( 
										matriculaNacional: matriculaNacional.equals(NULL_SEPARATOR) ? null : matriculaNacional,
										matriculaProvincial: matriculaProvincial.equals(NULL_SEPARATOR) ? null : matriculaProvincial,
										matriculaEspecial: matriculaEspecial.equals(NULL_SEPARATOR) ? null : matriculaEspecial,
										nombreCompleto: nombre,
										email: email, 
										especialidad: especialidadInstance,
										domicilio: domicilioInstance,
										telefonos: telefonos,
										sexo: sexo,
										estadoMedico: EstadoMedico.ACTIVO
									)
								}

								medicoInstance.save()
							}
						}
					}
	
					messages = [cantidadActualizados: cantidadActualizados, cantidadInsertados: cantidadInsertados]
				}
			}
			catch(Exception e)
			{
				mapError = [nombre: "", message: "error.read.file"]
				errors.add(mapError)
			}
		}
		
		return [errors: errors, messages: messages]
    }
	
	/** 
	 * Este metodo valida si existe al menos una matricula en el CSV
	 * 
	 * @param Map element
	 * @return Boolean true = error (Todas las matriculas son NULL) | false = si existe al menos una
	 */
	def validarSonTodasLasMatriculasVacias( element )
	{
		def toReturn = false
		def matriculaNacional = element.get(Medico.MATRICULA_NACIONAL_LABEL)
		def matriculaProvincial = element.get(Medico.MATRICULA_PROVINCIAL_LABEL)
		def matriculaEspecial = element.get(Medico.MATRICULA_ESPECIAL_LABEL)
		
		if ( matriculaNacional.equals(NULL_SEPARATOR) && matriculaProvincial.equals(NULL_SEPARATOR) && matriculaEspecial.equals(NULL_SEPARATOR) )
		{
			toReturn = true
		}
		
		return toReturn
	}
	
	/** 
	 * Este metodo valida si la cantidad de columnas es la correcta
	 * 
	 * @param Map element
	 * @return Boolean true = cantidad de columnas es correcta | false = cantidad de columnas NO es correcta
	 */
	def validarCantidadColumnas( element )
	{
		return element.size() == CANTIDAD_COLUMNAS
	}
	
	/**
	 * 
	 * @param element
	 * @return Boolean true si el nombre es completo | false si el nombre es vacio
	 */
	def validarNombreCompleto( element )
	{
		def nombre = element.get(Medico.NOMBRE_LABEL)
		
		return !nombre.equals(NULL_SEPARATOR)
	}
	
	/**
	 * 
	 * @param fileName
	 * @return Boolean true = CSV file extension | false = otherwise
	 */
	def validarFileExtension( fileName )
	{
		int dot = fileName.lastIndexOf(".")
		def extension = fileName.substring(dot + 1);
		
		def result = extension.toLowerCase().equals(FILE_EXTENSION);
		
		return result
	}
}

package com.cdrossi

import java.util.List;

class Medico {

	static String MATRICULA_NACIONAL_LABEL		= "Matrícula Nacional"
	static String MATRICULA_PROVINCIAL_LABEL 	= "Matrícula Provincial"
	static String MATRICULA_ESPECIAL_LABEL 		= "Matricula  Especialidad"
	static String NOMBRE_LABEL 					= "Nombre"
	static String ESPECIALIDAD_LABEL 			= "Especialidad"
	static String SEXO_LABEL 					= "Sexo"
	static String DOMICILIO_LABEL 				= "Domicilio"
	static String LOCALIDAD_LABEL 				= "Localidad"
	static String PROVINCIA_LABEL 				= "Provincia"
	static String CODIGO_POSTAL_LABEL			= "Código Postal"
	static String TEL_CONSULTORIO_LABEL 		= "Teléfono Consultorio"
	static String TEL_PARTICULAR_LABEL 			= "Teléfono Particular"
	static String TEL_CELULAR_LABEL 			= "Teléfono Celular"
	static String EMAIL_LABEL 					= "E-mail"
	static String NOTAS_LABEL 					= "Notas"
	
	
	String matriculaNacional
	String matriculaProvincial
	String matriculaEspecial
	String nombreCompleto
	String email
	Especialidad especialidad
	Domicilio domicilio
	List<Telefono> telefonos
	Sexo sexo
	EstadoMedico estadoMedico
	Visitador visitador
	List<EncuestaAsignada> medicoEncuestasAsignadas
	String picturePath = 'medicos/person.png'
	String nota
	BigDecimal montoPromedioDerivado
	List<BigDecimal> histrogramData
	EstadisticasMedico estadisticasMedico
	
	/*In this case instead of mapping classes onto separate tables a class can be "embedded" within the current table.*/
	static embedded = ['domicilio'] 
	static hasMany = [ telefonos:Telefono, medicoEncuestasAsignadas: EncuestaAsignada ]
	static transients = ['montoPromedioDerivado', 'histrogramData']
	static belongsTo = [visitador: Visitador]
	
    static constraints = {
		nombreCompleto (blank:false, nullable:false )
		matriculaNacional ( nullable:true )
		matriculaProvincial ( nullable:true )
		matriculaEspecial ( nullable:true )
		email (nullable:true )
		domicilio (nullable:true)
		sexo (nullable: true)
		telefonos (nullable: true)
		nota (nullable: true)
		visitador (nullable: true)
		estadisticasMedico (nullable: true)
    }
	
	static mapping = {
		nota type:"text"
	}
	
	Medico()
	{
		estadoMedico = EstadoMedico.ACTIVO
	}
	
	def static getMedicoByMatriculas( matriculaNacional, matriculaProvincial, matriculaEspecial )
	{
		def medico = Medico.findByMatriculaNacionalOrMatriculaProvincialOrMatriculaEspecial(matriculaNacional, matriculaProvincial, matriculaEspecial)

		return medico
	}
	
	def tieneEncuestasAsignadasPendientes()
	{
		def flag = false
		medicoEncuestasAsignadas.each() {
			if ( it.tipoEncuestaAsignada.equals(TipoEncuestaAsignada.PENDIENTE) )
			{
				flag = true
			}
		}
		
		flag
	}
}

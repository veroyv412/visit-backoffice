package com.cdrossi

import java.util.List;
import com.cdrossi.Medico

class Visitador extends Usuario{
	List<Medico> medicosAsignados
	
	def Visitador()
	{
		role = "ROLE_USER" 
		tipoUsuario = TipoUsuario.VISITADOR
	}
	
	static hasMany = [ medicosAsignados:Medico ]
	
	static constraints = {
    }
	
	def tieneMedicosConEncuestasAsignadasPendientes()
	{
		def flag = false
		medicosAsignados.each() {
			if ( it.tieneEncuestasAsignadasPendientes() )
			{
				flag = true
			}
		}
		
		flag
	}
	
	
	def String toString()
	{
		return this.apellido + ", " + this.nombre
	}
}

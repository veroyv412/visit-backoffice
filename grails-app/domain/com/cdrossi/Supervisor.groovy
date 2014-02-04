package com.cdrossi

class Supervisor extends Usuario{

	def Supervisor()
	{
		role = "ROLE_ADMIN"
		tipoUsuario = TipoUsuario.SUPERVISOR
	}
	
	static constraints = {
    }
	
}

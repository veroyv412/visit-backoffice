package com.cdrossi

class Usuario {

	transient springSecurityService

	String nombre
	String apellido
	String email
	String contrasenia
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	TipoUsuario tipoUsuario
	String role
	
	static constraints = {
		email (blank:false, nullable:false, size:3..100, email:true, unique:true)
		nombre (blank:false, nullable:false, size:3..100, matches:"[a-zA-Z1-9_]+")
		apellido (blank:false, nullable:false, size:3..100, matches:"[a-zA-Z1-9_]+")
		contrasenia (blank:false, nullable:false, size:4..100)		
		enabled (nullable:true)
		accountExpired (nullable:true)
		accountLocked (nullable:true)
		passwordExpired (nullable:true)
		tipoUsuario bindable: false
		role bindable: false
	}

	static transients = ['tipoUsuario', 'role']
	
	Usuario()
	{
		enabled = true
	}

	static mapping = {
	}

	Set<Role> getAuthorities() {
		UsuarioRole.findAllByUsuario(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('contrasenia')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		contrasenia = springSecurityService.encodePassword(contrasenia)
	}
}
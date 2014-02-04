package com.cdrossi.security

import java.util.Collection;

import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser
import org.springframework.security.core.GrantedAuthority;

/**
 * Implementación de {@link UserDetails} de Spring Security para CDR
 * 
 * @author Graion
 * 
 */
class CDRUserDetails extends GrailsUser {

	String nombre
	String apellido

	public CDRUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, 
		boolean credentialsNonExpired, boolean accountNonLocked, Collection<GrantedAuthority> authorities,
		Object id, String nombre, String apellido) {
		
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities, id);
		this.nombre = nombre
		this.apellido = apellido
	}
		
	String getNombreCompleto() {
		"$nombre $apellido"
	}
}

package com.cdrossi.security

import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUserDetailsService;
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.security.core.authority.GrantedAuthorityImpl
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException

import com.cdrossi.Usuario

/**
 * Implementación de {@link UserDetailsService} para que utilice {@link CDRUserDetails}
 * 
 * @author Graion
 *
 */
class CDRUserDetailsService implements GrailsUserDetailsService {

	/**
	 * Some Spring Security classes (e.g. RoleHierarchyVoter) expect at least
	 * one role, so we give a user with no granted roles this one which gets
	 * past that restriction but doesn't grant anything.
	 */
	static final List NO_ROLES = [
		new GrantedAuthorityImpl(SpringSecurityUtils.NO_ROLE)
	]

	UserDetails loadUserByUsername(String email, boolean loadRoles)	throws UsernameNotFoundException {
		return loadUserByUsername(email)
	}

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario.withTransaction { status ->

			Usuario usuario = Usuario.findByEmail(username)
			if (!usuario) throw new UsernameNotFoundException(
				'User not found', username)

			def authorities = usuario.authorities.collect {
				new GrantedAuthorityImpl(it.authority)
			}

			return new CDRUserDetails(usuario.email, usuario.contrasenia, usuario.enabled,
				!usuario.accountExpired, !usuario.passwordExpired,
				!usuario.accountLocked, authorities ?: NO_ROLES, usuario.id,
				usuario.nombre, usuario.apellido)
		}
	}
}

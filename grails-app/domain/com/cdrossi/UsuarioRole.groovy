package com.cdrossi

import org.apache.commons.lang.builder.HashCodeBuilder

class UsuarioRole implements Serializable {

	Usuario usuario
	Role role

	boolean equals(other) {
		if (!(other instanceof UsuarioRole)) {
			return false
		}

		other.usuario?.id == usuario?.id &&
			other.role?.id == role?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (usuario) builder.append(usuario.id)
		if (role) builder.append(role.id)
		builder.toHashCode()
	}

	static UsuarioRole get(long usuarioId, long roleId) {
		find 'from UsuarioRole where usuario.id=:usuarioId and role.id=:roleId',
			[usuarioId: usuarioId, roleId: roleId]
	}

	static UsuarioRole create(Usuario usuario, Role role, boolean flush = false) {
		new UsuarioRole(usuario: usuario, role: role).save(flush: flush, insert: true)
	}

	static boolean remove(Usuario usuario, Role role, boolean flush = false) {
		UsuarioRole instance = UsuarioRole.findByUsuarioAndRole(usuario, role)
		if (!instance) {
			return false
		}

		instance.delete(flush: flush)
		true
	}

	static void removeAll(Usuario usuario) {
		executeUpdate 'DELETE FROM UsuarioRole WHERE usuario=:usuario', [usuario: usuario]
	}

	static void removeAll(Role role) {
		executeUpdate 'DELETE FROM UsuarioRole WHERE role=:role', [role: role]
	}

	static mapping = {
		id composite: ['role', 'usuario']
		version false
	}
}

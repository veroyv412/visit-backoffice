<%@ page import="com.cdrossi.Usuario" %>
<%@ page import="com.cdrossi.TipoUsuario" %>

<div class="control-group ${hasErrors(bean: usuarioInstance, field: 'email', 'error')}">
	<label for="email" class="control-label">
		<g:message code="usuario.email.label" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls">
		<g:field type="email" name="email" required="" value="${usuarioInstance?.email}"/>
	</div>
</div>

<div class="control-group ${hasErrors(bean: usuarioInstance, field: 'nombre', 'error')}">
	<label for="nombre" class="control-label">
		<g:message code="usuario.nombre.label" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls">
		<g:textField name="nombre" maxlength="30" pattern="${usuarioInstance.constraints.nombre.matches}" required="" value="${usuarioInstance?.nombre}"/>
	</div>
</div>

<div class="control-group ${hasErrors(bean: usuarioInstance, field: 'apellido', 'error')}">
	<label for="apellido" class="control-label">
		<g:message code="usuario.apellido.label" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls">
		<g:textField name="apellido" maxlength="30" pattern="${usuarioInstance.constraints.apellido.matches}" required="" value="${usuarioInstance?.apellido}"/>
	</div>
</div>

<g:if test="${!usuarioInstance?.id}">
	<div class="control-group ${hasErrors(bean: usuarioInstance, field: 'contrasenia', 'error')}">
		<label for="contrasenia" class="control-label">
			<g:message code="usuario.contrasenia.label" />
			<span class="required-indicator">*</span>
		</label>
		<div class="controls">
			<g:passwordField name="contrasenia" maxlength="10" required="" value="${usuarioInstance?.contrasenia}"/>
		</div>
	</div>
</g:if>

<g:if test="${!usuarioInstance?.id}">
	<div class="control-group ${hasErrors(bean: usuarioInstance, field: 'tipoUsuario', 'error')}">
		<label for="rol" class="control-label">
			<g:message code="usuario.rol.label" />
		</label>
		<div class="controls inline">
			<label class="radio inline">Visitador<g:radio name="tipoUsuario" value="${TipoUsuario.VISITADOR}" checked="true"/>
			</label>			
			<label class="radio inline">Supervisor<g:radio name="tipoUsuario" value="${TipoUsuario.SUPERVISOR}"/>
			</label>
		</div>
	</div>
</g:if>

<g:hiddenField name="filename" id="filename"/>

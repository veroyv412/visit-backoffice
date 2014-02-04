<%@ page import="com.cdrossi.Usuario" %>
<%@ page import="com.cdrossi.TipoUsuario" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main-fullnavbar">
		<g:set var="entityName" value="${message(code: 'usuario.label')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>		
		<div id="show-usuario" role="main">
			<div class="page-header">
				<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			</div>
			<g:render template="nav-pills" />
			<g:if test="${flash.message}">
				<div class="alert alert-info" role="status">${flash.message}</div>
			</g:if>
			<dl class="dl-horizontal">
				<g:if test="${usuarioInstance?.email}">
					<dt><g:message code="usuario.email.label" /></dt>
					<dd><g:fieldValue bean="${usuarioInstance}" field="email"/></dd>
				</g:if>
					
				<g:if test="${usuarioInstance?.nombre}">
					<dt><g:message code="usuario.nombre.label" /></dt>
					<dd><g:fieldValue bean="${usuarioInstance}" field="nombre"/></dd>
				</g:if>
			
				<g:if test="${usuarioInstance?.apellido}">
					<dt><g:message code="usuario.apellido.label" /></dt>
					<dd><g:fieldValue bean="${usuarioInstance}" field="apellido"/></dd>
				</g:if>				
				
				<dt><g:message code="usuario.rol.label" /></dt>
				<g:if test="${usuarioInstance?.tipoUsuario.equals(TipoUsuario.VISITADOR)}">
					<dd><g:message code="usuario.rol.visitador.label" /></dd>
				</g:if>
				<g:else>
					<dd><g:message code="usuario.rol.supervisor.label" /></dd>
				</g:else>				
			</dl>
			<g:form>
				<div class="form-actions">
					<g:hiddenField name="id" value="${usuarioInstance?.id}" />
					<g:link class="btn btn-primary edit" action="edit" id="${usuarioInstance?.id}"><g:message code="default.button.edit.label" /></g:link>
					<g:actionSubmit class="btn btn-danger delete" action="delete" value="${message(code: 'default.button.delete.label')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message')}');" />
				</div>
			</g:form>
		</div>
	</body>
</html>

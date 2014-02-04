<%@ page import="com.cdrossi.Usuario" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main-fullnavbar">
		<g:set var="entityName_pl" value="${message(code: 'usuarios.label')}" />
		<title><g:message code="default.list.label" args="[entityName_pl]" /></title>
		<title><g:message code="menu.usuarios" /></title>
	</head>
	<body>				
		<div id="list-usuario" role="main">
			<div class="page-header">
				<h1><g:message code="default.list.label" args="[entityName_pl]" /></h1>
			</div>			
			<g:render template="nav-pills" />
			<g:if test="${flash.message}">
				<div class="alert alert-info" role="status">${flash.message}</div>
			</g:if>
			
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<g:sortableColumn property="class" title="${message(code: 'usuario.rol.label')}" />
						<g:sortableColumn property="nombre" title="${message(code: 'usuario.nombre.label')}" />
						<g:sortableColumn property="apellido" title="${message(code: 'usuario.apellido.label')}" />
						<g:sortableColumn property="email" title="${message(code: 'usuario.email.label')}" />
						<g:sortableColumn property="enabled" title="${message(code: 'usuario.enabled.label')}" />
					</tr>
				</thead>
				<tbody>
				<g:each in="${usuarioInstanceList}" status="i" var="usuarioInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td>${usuarioInstance.tipoUsuario }</td>
						<td><g:link action="show" id="${usuarioInstance.id}">${fieldValue(bean: usuarioInstance, field: "nombre")}</g:link></td>
						<td>${fieldValue(bean: usuarioInstance, field: "apellido")}</td>
						<td>${fieldValue(bean: usuarioInstance, field: "email")}</td>
						<g:if test="${usuarioInstance.enabled}">
							<td><g:message code="usuario.estado.activo"/></td>
						</g:if>
						<g:else>
							<td><g:message code="usuario.estado.inactivo"/></td>
						</g:else>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${usuarioInstanceTotal}" />
			</div>
			</div>
	</body>
</html>

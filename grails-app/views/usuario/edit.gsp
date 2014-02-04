<%@ page import="com.cdrossi.Usuario" %>
<%@ page import="com.cdrossi.TipoUsuario" %>

<!doctype html>
<html>
	<head>
		<meta name="layout" content="main-fullnavbar">
		<g:set var="entityName" value="${message(code: 'usuario.label')}" />
		<title><g:message code="default.edit.label" args="[entityName]" /></title>
	</head>
	<body>		
		<div id="edit-usuario" role="main">
			<div class="page-header">
				<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
			</div>
			<g:render template="nav-pills" />
			<g:if test="${flash.message}">
				<div class="alert alert-info" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${usuarioInstance}">
				<ul class="alert alert-error" role="alert">
					<g:eachError bean="${usuarioInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
			</g:hasErrors>
			<g:form method="post" class="form-horizontal">
				<g:hiddenField name="id" value="${usuarioInstance?.id}" />
				<g:hiddenField name="version" value="${usuarioInstance?.version}" />
				<fieldset>
					<g:render template="form"/>
				</fieldset>
				
				<g:if test="${usuarioInstance.tipoUsuario.equals(TipoUsuario.VISITADOR)}">
					<div id="asigancionmedicos">
						<fieldset>
							<g:render template="formasignacionmedicos"/>
						</fieldset>
					</div>
				</g:if>
				
				<div class="form-actions">
					<g:actionSubmit class="btn btn-primary" action="update" value="${message(code: 'default.button.update.label')}" />
					<g:actionSubmit class="btn btn-danger" action="delete" value="${message(code: 'default.button.delete.label')}" formnovalidate="" onclick="return confirm('${message(code: 'default.button.delete.confirm.message')}');" />
				</div>
			</g:form>
		</div>
	</body>
</html>

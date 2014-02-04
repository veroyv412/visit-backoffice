<%@ page import="com.cdrossi.Pregunta" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main-fullnavbar">
		<g:set var="entityName" value="${message(code: 'pregunta.label', default: 'Pregunta')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="create-pregunta" role="main">
			<div class="page-header">
				<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			</div>
			<g:render template="nav-pills" />
			<g:if test="${flash.message}">
				<div class="alert alert-info" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${preguntaInstance}">
				<div class="alert alert-error">
					<ul class="errors" role="alert">
						<g:eachError bean="${preguntaInstance}" var="error">
						<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
						</g:eachError>
					</ul>
				</div>
			</g:hasErrors>
			<g:form action="save" class="form-horizontal">
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<div class="form-actions">
					<g:submitButton name="create" class="btn btn-primary" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</div>
			</g:form>
		</div>
	</body>
</html>

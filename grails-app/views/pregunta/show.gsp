
<%@ page import="com.cdrossi.Pregunta" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main-fullnavbar">
		<g:set var="entityName" value="${message(code: 'pregunta.label', default: 'Pregunta')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>		
		<div id="show-pregunta" class="content scaffold-show" role="main">
			<div class="page-header">
				<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			</div>
			<g:render template="nav-pills" />
			<g:if test="${flash.message}">
				<div class="alert alert-info" role="status">${flash.message}</div>
			</g:if>
			<dl class="dl-horizontal">			
				<g:if test="${preguntaInstance?.enunciado}">				
					<dt><g:message code="pregunta.enunciado.label" /></dt>
					<dd><g:fieldValue bean="${preguntaInstance}" field="enunciado"/></dd>
				</g:if>
			
				<g:if test="${preguntaInstance?.tipoPregunta}">
					<dt><g:message code="pregunta.tipoPregunta.label" /></dt>
					<dd><g:fieldValue bean="${preguntaInstance}" field="tipoPregunta"/><dd>
				</g:if>
				
				<g:if test="${preguntaInstance?.respuestas}">
					<dt><g:message code="pregunta.respuestas.label" /></dt>
					<dd><g:textArea name="listaRespuestas" readonly="true" value="${preguntaInstance?.getRespuestasToString()}"/><dd>
				</g:if>
			
			</dl>
			<g:form>
				<div class="form-actions">
					<g:hiddenField name="id" value="${preguntaInstance?.id}" />
					<g:link class="btn btn-primary edit" action="edit" id="${preguntaInstance?.id}" params="[idEncuesta: idEncuesta]"><g:message code="default.button.edit.label" /></g:link>
				</div>
			</g:form>
		</div>
	</body>
</html>

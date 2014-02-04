
<%@ page import="com.cdrossi.Encuesta" %>
<%@ page import="com.cdrossi.EstadoEncuesta" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main-fullnavbar">
		<g:set var="entityName" value="${message(code: 'encuesta.label', default: 'Encuesta')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="show-encuesta" role="main">
			<div class="page-header">
				<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			</div>
			<g:render template="nav-pills" />
			<g:if test="${flash.message}">
				<div class="alert alert-info" role="status">${flash.message}</div>
			</g:if>
			<dl class="dl-horizontal">
				<g:if test="${encuestaInstance?.nombre}">
					<dt><g:message code="encuesta.nombre.label" /></dt>
					<dd><g:fieldValue bean="${encuestaInstance}" field="nombre"/></dd>
				</g:if>
				
				<g:if test="${encuestaInstance?.descripcion}">
					<dt><g:message code="encuesta.descripcion.label" /></dt>
					<dd><g:fieldValue bean="${encuestaInstance}" field="descripcion"/></dd>
				</g:if>
				
				<g:if test="${encuestaInstance?.fechaInicio}">
					<dt><g:message code="encuesta.fechaInicio.label" /></dt>
					<dd><g:formatDate date="${encuestaInstance?.fechaInicio}" format="dd/MM/yyyy"/></dd>
				</g:if>
				
				<g:if test="${encuestaInstance?.fechaFin}">
					<dt><g:message code="encuesta.fechaFin.label"  /></dt>
					<dd><g:formatDate date="${encuestaInstance?.fechaFin}" format="dd/MM/yyyy"/></dd>
				</g:if>
				
				<g:if test="${encuestaInstance?.estadoEncuesta}">
					<dt><g:message code="encuesta.estadoEncuesta.label" /></dt>
					<dd>${encuestaInstance?.estadoEncuesta}</dd>
				</g:if>
		
			</dl>
			
			
				<g:form>
					<div class="form-actions">
						<g:hiddenField name="id" value="${encuestaInstance?.id}" />											
						<g:if test="${encuestaInstance?.estadoEncuesta.equals(EstadoEncuesta.BORRADOR)}">
							<g:link class="btn btn-primary" action="edit" id="${encuestaInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
							<g:actionSubmit class="btn btn-danger delete" action="delete" value="${message(code: 'default.button.delete.label')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message')}');" />
							<g:link class="btn" action="asignarMedicos" params="[idEncuesta: encuestaInstance?.id]"><g:message code="encuesta.asignarMedicos.label"/></g:link>
						</g:if>
						<g:link class="btn" controller="pregunta" action="list" params="[idEncuesta: encuestaInstance?.id]"><g:message code="encuesta.pregunta.label" default="Lista de Preguntas" /></g:link>
					</div>
				</g:form>
			
		</div>
	</body>
</html>

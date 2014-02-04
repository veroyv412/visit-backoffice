<!doctype html>
<html>
	<head>
		<title><g:message code="medico.import.resultado"/></title>
		<g:set var="entityName" value="${message(code: 'medico.label')}" />
		<meta name="layout" content="main-fullnavbar">
	</head>
	<body>			
		<div id="list-medico" role="main">
			<div class="page-header">
				<h1><g:message code="medico.import.resultado"/></h1>
			</div>
			<g:render template="nav-pills" />		
			<g:if test="${info.errors}">
				<div class="alert alert-error">
					<ul role="alert">
						<g:each in="${info.errors}" var="error">
							<li>${error.nombre} - <g:message code="${error.message}"/></li>
						</g:each>
					</ul>
				</div>
			</g:if>
			
			<div class="alert alert-info">
				<p><g:message code="medico.import.cantidadActualizdos"/> = ${info.messages.cantidadActualizados}</p>
				<p><g:message code="medico.import.cantidadInsertados"/> = ${info.messages.cantidadInsertados}</p>
			</div>
		</div>
	</body>
</html>
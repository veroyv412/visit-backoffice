<!doctype html>
<html>
	<head>
		<title><g:message code="medico.derivaciones.label"/></title>
		<meta name="layout" content="main-fullnavbar">
	</head>
	<body>			
			<div id="list-medico" role="main">
				<div class="page-header">
					<h1><g:message code="importar.derivaciones.label"/></h1>
				</div>
				<g:render template="../medico/nav-pills" />
			</div>
			
			<div class="alert alert-info">
				<p><g:message code="importar.derivaciones.cantInsertados"/> = ${cantInsertados}</p>
				<p><g:message code="importar.derivaciones.cantLoggeados"/> = ${cantLoggeados}</p>
			</div>

			<div>
				<div><g:link class="btn btn-primary" action="descargarLog"><g:message code="importar.derivaciones.descargarLog"/></g:link></div>
			</div>

	</body>
</html>
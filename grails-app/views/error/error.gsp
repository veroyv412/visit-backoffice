<!doctype html>
<html>
<head>
<meta name='layout' content='main' />
<title><g:message code="${msg}" /></title>
</head>

<body>
	<div class="container" style="width: 600px;">
		<div class="alert alert-error">
			<h3 class="alert-heading">Error</h3>
			<g:message code="${msg}" />			
		</div>		
		<div style="text-align: center;">
			<a href="#" onclick="parent.history.back(); return false;">Volver a la página anterior</a>
		</div>
	</div>
</body>
</html>

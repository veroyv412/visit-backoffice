<!doctype html>
<html>
	<head>
		<title><g:message code="medico.import.label"/></title>
		<g:set var="entityName" value="${message(code: 'medico.label')}" />
		<meta name="layout" content="main-fullnavbar">
	</head>
	<body>			
			<div id="list-medico" role="main">
				<div class="page-header">
					<h1><g:message code="medico.import.label"/></h1>
				</div>
				<g:render template="nav-pills" />
			</div>
			
			<g:if test="${flash.message}">
				<div class="alert alert-info" role="status">${flash.message}</div>
			</g:if>
			
			<g:uploadForm class="form-horizontal" name="uploader" controller="medico" action="procesarDerivaciones">
				<label class="control-label">
					Planilla Excel (.xls)
				</label>
				<div class="controls">
			    	<input type="file" name="qqFile" />
			    </div>
			    <div class="form-actions">			    	
			    	<g:submitButton class="btn btn-primary" name="leerDerivaciones" value="${message(code:'medico.import.label') }" />
			    	<g:link class="btn" action="list">Volver</g:link>
			    </div>
			</g:uploadForm>
		
	</body>
</html>
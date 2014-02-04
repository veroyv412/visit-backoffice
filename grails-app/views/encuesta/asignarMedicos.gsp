<%@ page import="com.cdrossi.Encuesta" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main-fullnavbar">
		<g:set var="entityName" value="${message(code: 'encuesta.label')}" />
		<title><g:message code="encuesta.asignarMedicos.label" /></title>
	</head>
	<body>		
		<div id="show-encuesta" class="content scaffold-show" role="main">
			<div class="page-header">
				<h1><g:message code="encuesta.asignarMedicos.label" /></h1>
			</div>			
			<g:render template="nav-pills" />
			<g:if test="${flash.message}">
				<div class="alert alert-info" role="status">${flash.message}</div>
			</g:if>
			
			<g:uploadForm class="form-horizontal" name="uploader" controller="encuesta" action="procesarArchivoAsignacionMedicos" params="${[ idEncuesta: idEncuesta ]}" allowedExtensions=" [ 'xls']">
				<label class="control-label">
					Planilla Excel (.xls)
				</label>
				<div class="controls">
			    	<input type="file" name="qqFile" />
			    </div>
			    <div class="form-actions">			    	
			    	<g:submitButton class="btn btn-primary" name="asignarMedicos" value="Asignar Medicos" />
			    	<g:link class="btn" action="show" id="${idEncuesta}">Volver</g:link>
			    </div>
			</g:uploadForm>
		</div>
	</body>
</html>

<%@ page import="com.cdrossi.Usuario" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main-fullnavbar">
		<g:set var="entityName" value="${message(code: 'usuario.label')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
		
		<g:javascript>
			var $J = jQuery.noConflict();
			
			$J(document).ready(function(){
				$J("input[name=tipoUsuario]").click(function(){
					var tipoUsuario = $J("input[name=tipoUsuario]:checked").val();
					if ( tipoUsuario == "Visitador" )
					{
						$J("#asigancionmedicos").fadeIn();
					}
					else
					{
						$J("#asigancionmedicos").fadeOut();
					}
				});

			});
		</g:javascript>
		
	</head>
	<body>		
		<div id="create-usuario" role="main">
			<div class="page-header">
				<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			</div>
			<g:render template="nav-pills" />
			<g:if test="${flash.message}">
				<div class="alert alert-info" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${usuarioInstance}">
				<div class="alert alert-error">
					<ul role="alert">
						<g:eachError bean="${usuarioInstance}" var="error">
							<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
						</g:eachError>
					</ul>
				</div>
			</g:hasErrors>
			<g:form action="save" class="form-horizontal">
				<fieldset>
					<g:render template="form"/>
				</fieldset>
				
				
				<fieldset id="asigancionmedicos">
					<div class="control-group">
						<label class="control-label"><g:message code="usuario.asignarMedicos" /></label>					
						<div class="controls">
							<g:render template="formasignacionmedicos"/>
						</div>
					</div>
				</fieldset>
				
								
				<div class="form-actions">
					<g:submitButton name="create" class="btn btn-primary" value="${message(code: 'default.button.create.label')}" />
					<button type="reset" class="btn">Cancelar</button>
				</div>
			</g:form>
		</div>
	</body>
</html>

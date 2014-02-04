<!doctype html>
<html>
	<head>
		<title><g:message code="importar.derivaciones.label"/></title>
		<meta name="layout" content="main-fullnavbar">
		<g:set var="entityName" value="${message(code: 'medico.label')}" />
		<g:javascript>
			var $J = jQuery.noConflict();
			
			$J(document).ready(function(){
				$J("#leerDerivaciones").click(function(){
					var mes = $J("#mes").find("option:selected").val();
					var anio = $J("#anio").find("option:selected").val();
				
					$J.ajax({
					  url: 'validateImportacionParaPeriodoSeleccionado',
					  data: {mes: mes, anio: anio},
					  type: "POST",
					  dataType: "json",
					  success: function( result ){
            			if ( result.error )
            			{
            				if(confirm("Ya se han importado los datos para este período. ¿Desea volverlos a importar?"))
            				{
            					$J("#uploader").submit();
            				}
            			}
            			else
            			{
            				$J("#uploader").submit();
            			}
        			  }
					});
				});

			});
		</g:javascript>
		
	</head>
	<body>			
			<div id="list-derivaciones" role="main">
				<div class="page-header">
					<h1><g:message code="importar.derivaciones.label"/></h1>
				</div>
				<g:render template="../medico/nav-pills" />
			</div>
			
			<g:if test="${flash.message}">
				<div class="alert alert-info" role="status">${flash.message}</div>
			</g:if>
			
			<g:uploadForm name="uploader" controller="derivaciones" action="procesarDerivaciones" class="form-horizontal">
				<div class="control-group">
					<label for="mes" class="control-label">
						<g:message code="derivacion.mes.label" />
					</label>
					<div class="controls">
						<g:select name="mes" from="${01..12}" value="${currentMonth}"/>
					</div>
				</div>
				<div class="control-group">
					<label for="anio" class="control-label">
						<g:message code="derivacion.anio.label" />
					</label>
					<div class="controls">
						<g:select name="anio" from="${1980..2100}" value="${currentYear}"/>
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label">
						Archivo separado por comas (.csv)
					</label>
					<div class="controls">
				    	<input type="file" name="qqFile" />
				    </div>
			    </div>
				
				<div class="form-actions">			    	
			    	<input id="leerDerivaciones" class="btn btn-primary" type="submit" value="${message(code:'derivacion.importar.label') }" name="leerDerivaciones" onclick="return false;">
			    	<g:link class="btn" controller="medico" action="list">Volver</g:link>
			    </div>			    
			    
			</g:uploadForm>
		
	</body>
</html>
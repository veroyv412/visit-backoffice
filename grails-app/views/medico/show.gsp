<%@ page import="com.cdrossi.Medico" %>
<%@ page import="com.cdrossi.UtilsService" %>
<%@ page import="grails.converters.deep.JSON" %>
<%@ page import="grails.converters.*" %>
<%@ page import="org.codehaus.groovy.grails.web.json.*" %>

<!doctype html>
<html>
	<head>
		<meta name="layout" content="main-fullnavbar">
		<g:set var="entityName" value="${message(code: 'medico.label')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	
	<g:javascript>
			var $J = jQuery.noConflict();
			
			var actividadUltimosTresMesesJSON = JSON.parse('${medicoInstance.estadisticasMedico.actividadUltimosTresMeses}');
			var plot1 = $J.jqplot('chartActividadUltimosTresMeses', [actividadUltimosTresMesesJSON.data], {
		        seriesDefaults:{
		            renderer:$J.jqplot.BarRenderer,
		            rendererOptions: {fillToZero: true}
		        },
		        axes: {
		            // Use a category axis on the x axis and use our custom ticks.
		            xaxis: {
		                renderer: $J.jqplot.CategoryAxisRenderer,
		                ticks: actividadUltimosTresMesesJSON.ticks
		            },
		            // Pad the y axis just a little so bars can get close to, but
		            // not touch, the grid boundaries.  1.2 is the default padding.
		            yaxis: {
		                pad: 1.05,
		                tickOptions: {formatString: '%d'}
		            }
		        }
		    });
		    var chartCantidadPrestacionesTresMesesJSON = JSON.parse('${medicoInstance.estadisticasMedico.cantidadPrestacionesPorServicioUltimosTresMeses}');
		    if ( chartCantidadPrestacionesTresMesesJSON.size() <= 6  )
		    {
			    var plot2 = jQuery.jqplot ('chartCantidadPrestacionesTresMeses', [chartCantidadPrestacionesTresMesesJSON], {
			    	seriesDefaults: {
			        // Make this a pie chart.
			        renderer: jQuery.jqplot.PieRenderer, 
			        rendererOptions: {
			          // Put data labels on the pie slices.
			          // By default, labels show the percentage of the slice.
			          showDataLabels: true,
			        },
			       	pointLabels: { show:true } 
			      }, 
			      legend: { show:true, location: 'e' }
			    
			    });
		    }
		    
	</g:javascript>
	
	<body>		
		<div id="show-medico" role="main">
			<div class="page-header">
				<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			</div>
			<g:render template="nav-pills" />
			<g:if test="${flash.message}">
				<div class="alert alert-info" role="status">${flash.message}</div>
			</g:if>
			
			<div class="container-fluid">
  				<div class="row-fluid">
    				<div class="span5">    
						<dl class="dl-horizontal">
							<g:if test="${medicoInstance?.nombreCompleto}">
								<dt><g:message code="medico.nombreCompleto.label" /></dt>
								<dd><g:fieldValue bean="${medicoInstance}" field="nombreCompleto"/></dd>
							</g:if>
						
							<g:if test="${medicoInstance?.estadoMedico}">
								<dt><g:message code="medico.estadoMedico.label" /></dt>
								<dd><g:fieldValue bean="${medicoInstance}" field="estadoMedico"/></dd>
							</g:if>							
							
							<g:if test="${medicoInstance?.matriculaNacional}">
								<dt><g:message code="medico.matriculaNacional.label" /></dt>
								<dd><g:fieldValue bean="${medicoInstance}" field="matriculaNacional"/></dd>
							</g:if>
							
							<g:if test="${medicoInstance?.matriculaProvincial}">
								<dt><g:message code="medico.matriculaProvincial.label" /></dt>
								<dd><g:fieldValue bean="${medicoInstance}" field="matriculaProvincial"/></dd>
							</g:if>
							
							<g:if test="${medicoInstance?.matriculaEspecial}">
								<dt><g:message code="medico.matriculaEspecial.label" /></dt>
								<dd><g:fieldValue bean="${medicoInstance}" field="matriculaEspecial"/></dd>
							</g:if>
							
							<g:if test="${medicoInstance?.email}">
								<dt><g:message code="medico.email.label" /></dt>
								<dd><g:fieldValue bean="${medicoInstance}" field="email"/></dd>
							</g:if>
							
							<g:if test="${medicoInstance?.domicilio}">
								<dt><g:message code="medico.domicilio.label" /></dt>
								<dd><g:fieldValue bean="${medicoInstance}" field="domicilio"/></dd>
							</g:if>
							
							<g:if test="${medicoInstance?.sexo}">
								<dt><g:message code="medico.sexo.label" /></dt>
								<dd><g:fieldValue bean="${medicoInstance}" field="sexo"/></dd>
							</g:if>
							
							<g:if test="${medicoInstance?.especialidad}">
								<dt><g:message code="medico.especialidad.label" /></dt>
								<dd>${medicoInstance?.especialidad?.nombre}
							</g:if>				
							
							<g:if test="${medicoInstance?.telefonos}">
								<dt><g:message code="medico.telefonos.label" /></dt>
								<g:each in="${medicoInstance.telefonos}" var="t">
									<dd>${t?.tipoTelefono}: ${t?.numero}</dd>
								</g:each>
							</g:if>							
						</dl>
					</div>
				    <div class="span7">
				      <div><img src="${resource(dir:'images',file: medicoInstance.picturePath)}"></div>
				    </div>
				  </div>
				</div>
				
				<div class="row-fluid">
					<h2><g:message code="medico.perfil.estadisticas.label"/></h2>
					<dl class="dl-horizontal">
						<g:if test="${medicoInstance?.estadisticasMedico?.derivacionesHistoricas}">
							<dt><g:message code="medico.perfil.derivacionesHistoricas.label" /></dt>
							<dd><g:formatNumber number="${medicoInstance.estadisticasMedico.derivacionesHistoricas}" type="number" maxFractionDigits="2" /></dd>
						</g:if>
						<g:if test="${medicoInstance?.estadisticasMedico?.derivacionesUltimosTresMeses}">
							<dt><g:message code="medico.perfil.derivacionesUltimosTresMeses.label" /></dt>
							<dd><g:formatNumber number="${medicoInstance.estadisticasMedico.derivacionesUltimosTresMeses}" type="number" maxFractionDigits="2" /></dd>
						</g:if>
						<g:if test="${medicoInstance?.estadisticasMedico?.derivacionesUltimoMes}">
							<dt><g:message code="medico.perfil.derivacionesUltimoMes.label" /></dt>
							<dd><g:formatNumber number="${medicoInstance.estadisticasMedico.derivacionesUltimoMes}" type="number" maxFractionDigits="2" /></dd>
						</g:if>
						<g:if test="${medicoInstance?.estadisticasMedico?.facturacionHistoricas}">
							<dt><g:message code="medico.perfil.facturacionHistoricas.label" /></dt>
							<dd><g:formatNumber number="${medicoInstance.estadisticasMedico.facturacionHistoricas}" type="number" maxFractionDigits="2" /></dd>
						</g:if>
						<g:if test="${medicoInstance?.estadisticasMedico?.facturacionUltimosTresMeses}">
							<dt><g:message code="medico.perfil.facturacionUltimosTresMeses.label" /></dt>
							<dd><g:formatNumber number="${medicoInstance.estadisticasMedico.facturacionUltimosTresMeses}" type="number" maxFractionDigits="2" /></dd>
						</g:if>
						<g:if test="${medicoInstance?.estadisticasMedico?.facturacionUltimoMes}">
							<dt><g:message code="medico.perfil.facturacionUltimoMes.label" /></dt>
							<dd><g:formatNumber number="${medicoInstance.estadisticasMedico.facturacionUltimoMes}" type="number" maxFractionDigits="2" /></dd>
						</g:if>
					</dl>
					
					<h3><g:message code="medico.perfil.actividadUltimosTresMeses.label"/></h3>
					<div id="chartActividadUltimosTresMeses" style="width: 450px"></div>
					
					<h3><g:message code="medico.perfil.cantidadPrestacionesPorServicioUltimosTresMeses.label"/></h3>
					
					
					<g:set var="cantidadPrestacionesJSON" value="${JSON.parse(medicoInstance.estadisticasMedico.cantidadPrestacionesPorServicioUltimosTresMeses) }" />
					<g:set var="cantidadPrestaciones" value="${cantidadPrestacionesJSON.size()}"/>

					<g:if test="${cantidadPrestaciones <= 6 }">
						<div id="chartCantidadPrestacionesTresMeses" style="width: 450px"></div>
					</g:if>
					<g:else>
						<table class="table table-striped table-bordered">
							<thead>
								<th><g:message code="medico.perfil.servicio.label"/></th>
								<th><g:message code="medico.perfil.cantidadPrestaciones.label"/></th>
							</thead>
							<tbody>
							<g:each in="${cantidadPrestacionesJSON}" status="i" var="value">
							<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
								<td>${value[0].substring(0, value[0].indexOf(':'))}</td>
								<td>${value[1]}</td>
							</tr>
							</g:each>
							</tbody>
						</table>
					</g:else>				
				</div>
				
				<div class="form-actions">
					<g:link class="btn" action="list">Volver</g:link>
				</div>		
			</div>
			<div style="clear: both"></div>
		
	</body>
</html>

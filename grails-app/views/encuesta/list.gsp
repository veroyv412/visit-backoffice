
<%@ page import="com.cdrossi.Encuesta" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main-fullnavbar">
		<g:set var="entityName_pl" value="${message(code: 'encuestas.label')}" />
		<title><g:message code="default.list.label" args="[entityName_pl]" /></title>
	</head>
	<body>		
		<div id="list-encuesta" role="main">
			<div class="page-header">
				<h1><g:message code="default.list.label" args="[entityName_pl]" /></h1>
			</div>			
			<g:render template="nav-pills" />
			<g:if test="${flash.message}">
				<div class="alert alert-info" role="status">${flash.message}</div>
			</g:if>
			<table class="table table-striped table-bordered">
				<thead>
					<tr>					
						<g:sortableColumn property="nombre" title="${message(code: 'encuesta.nombre.label', default: 'Nombre')}" />
						<g:sortableColumn style="width: 70px" property="fechaInicio" title="${message(code: 'encuesta.fechaInicio.label')}" />
						<g:sortableColumn style="width: 70px" property="fechaFin" title="${message(code: 'encuesta.fechaFin.label')}" />
						<g:sortableColumn style="width: 105px" property="estadisticasEncuesta.porcentajeEncuestado" title="${message(code: 'encuesta.estadisticasEncuesta.porcentajeEncuestado.label', default: '% Encuestado')}" />
						<g:sortableColumn style="width: 100px" property="estadisticasEncuesta.porcentajeCancelado" title="${message(code: 'encuesta.estadisticasEncuesta.porcentajeCancelado.label', default: '% Cancelado')}" />
						<g:sortableColumn style="width: 95px" property="estadisticasEncuesta.porcentajePendiente" title="${message(code: 'encuesta.estadisticasEncuesta.porcentajePendiente.label', default: '% Pendiente')}" />
						<g:sortableColumn style="width: 70px" property="estadoEncuesta" title="${message(code: 'encuesta.estadoEncuesta.label')}" />
						<th style='width: 55px;'><g:message code="encuesta.asignacion.medicos.exportar.excel.label"/></th>
						<th style='width: 75px;'><g:message code="encuesta.estadistica.label"/></th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${encuestaInstanceList}" status="i" var="encuestaInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td><g:link action="show" id="${encuestaInstance.id}">${fieldValue(bean: encuestaInstance, field: "nombre")}</g:link></td>
						<td><g:formatDate format="dd/MM/yyyy" date="${encuestaInstance.fechaInicio}" /></td>
						<td><g:formatDate format="dd/MM/yyyy" date="${encuestaInstance.fechaFin}" /></td>
						<td style="text-align: right"><g:formatNumber number="${encuestaInstance?.estadisticasEncuesta?.porcentajeEncuestado?:0}" type="number" maxFractionDigits="2"/></td>
						<td style="text-align: right"><g:formatNumber number="${encuestaInstance?.estadisticasEncuesta?.porcentajeCancelado?:0}" type="number" maxFractionDigits="2"/></td>
						<td style="text-align: right"><g:formatNumber number="${encuestaInstance?.estadisticasEncuesta?.porcentajePendiente?:0}" type="number" maxFractionDigits="2"/></td>
						<td>${encuestaInstance.estadoEncuesta}</td>
						<td>
							<g:link class="excel" action="exportMinutas" id="${encuestaInstance.id}">
								<img src="${resource(dir:'images',file: 'icon_excel.png')}" style="padding-right: 5px">
							</g:link>
						</td>
						<td>
						<g:if test="${encuestaInstance.encuestaEncuestasAsignadas?.size() > 0}">
							<g:link class="excel" action="verEstadisticas" id="${encuestaInstance.id}">
								<img src="${resource(dir:'images',file: 'icon_chart.png')}" style="padding-right: 5px">
							</g:link>
						</g:if>
						<g:else>
							<img src="${resource(dir:'images',file: 'icon_chart_disabled.png')}" style="padding-right: 5px" title="${message(code: 'encuesta.no.posee.medicos.asociados')}" alt="${message(code: 'encuesta.no.posee.medicos.asociados')}">
						</g:else>
							
						</td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${encuestaInstanceTotal}" />
			</div>
		</div>
	</body>
</html>

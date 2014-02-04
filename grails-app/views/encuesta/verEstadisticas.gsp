<%@ page import="com.cdrossi.Encuesta" %>
<%@ page import="com.cdrossi.EncuestaAsignada" %>
<%@ page import="com.cdrossi.Usuario" %>
<%@ page import="com.cdrossi.Visitador" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main-fullnavbar">
		<g:set var="entityName" value="${message(code: 'encuesta.label')}" />
		<title><g:message code="encuesta.estadistica.label" /></title>
		<g:javascript library="prototype" />
        <g:javascript library="scriptaculous" />
        <filterpane:includes />
        
		<g:javascript>
			var $J = jQuery.noConflict();
			<g:each var="pregunta" in="${encuestaInstance.preguntas}">
				var divChartPreguntaContainer = $J("<div id='chartPreguntaContainer${pregunta.id}' class='accordion-group' style='padding-left: 10px;'></div>");
				
				var divChartPreguntaHeaderContainer = $J("<div id='chartPreguntaHeaderContainer${pregunta.id}' class='accordion-heading'></div>");
				var h3ChartPreguntaContainer = $J("<h4 id='h3PreguntaContainer${pregunta.id}' class='accordion-toggle' data-toggle='collapse' data-target='#chart${pregunta.id}'></h4>");
				$J(h3ChartPreguntaContainer).html('${pregunta.enunciado}');
				$J(divChartPreguntaHeaderContainer).append(h3ChartPreguntaContainer);
				
				var divChart = $J("<div id='chart${pregunta.id}' class='accordion-body collapse in' style='width: 500px'></div>");
				
				$J(divChartPreguntaContainer).append(divChartPreguntaHeaderContainer);
				$J(divChartPreguntaContainer).append(divChart);
				
				$J("#chartContainer").append(divChartPreguntaContainer);
				
				var ticks = new Array('${pregunta.enunciado}');
				
				var i = 0;
				var series = [];
				var labels = [];
				var max = 0;
				<g:each var="respuesta" in="${pregunta.respuestas}">
					var value = "${respuesta.cantidadRespondida}";
					
					if ( value > max )
					{
						max = value;
					}
					series[i] = new Array(value);
					labels[i] = {};
					labels[i].label = "${respuesta.valor}";
					i++;
				</g:each>
				max = parseInt(max) + 1;
				
				var plot1 = $J.jqplot('chart${pregunta.id}', series, {
				    // The "seriesDefaults" option is an options object that will
				    // be applied to all series in the chart.
				    seriesDefaults:{
				        renderer:$J.jqplot.BarRenderer,
				        rendererOptions: {fillToZero: true, barWidth: 90 }
				    },
				    // Custom labels for the series are specified with the "label"
				    // option on the series option.  Here a series option object
				    // is specified for each series.
				    series:labels,
				    // Show the legend and put it outside the grid, but inside the
				    // plot container, shrinking the grid to accomodate the legend.
				    // A value of "outside" would not shrink the grid and allow
				    // the legend to overflow the container.
				    legend: {
				        show: true,
				        placement: 'outsideGrid'
				    },
				    axes: {
				        // Use a category axis on the x axis and use our custom ticks.
				        xaxis: {
				            renderer: $J.jqplot.CategoryAxisRenderer,
				            ticks: ticks,
				            show: true
				        },
				        // Pad the y axis just a little so bars can get close to, but
				        // not touch, the grid boundaries.  1.2 is the default padding.
				        yaxis: {
				            pad: 1.2,
				            tickOptions: {formatString: '%d'},
				            show: true,
				            max: max,
				            min: 0
				        }
				    }
				});
				
				$J(divChart).on('shown',function(){
					var height = $J(this).children().height();
					$J(this).height(height);
				});
				
			</g:each>
			
			$J(".collapse").collapse();
			
			var totalSeries = [];
			<g:each var="encuestaAsignada" in="${encuestaInstance.encuestaEncuestasAsignadas}">
				var series = [];
				var i = 0;
				<g:each var="data" in="${encuestaAsignada.medico.histrogramData}">
					series[i] = ${data};
					i++;
				</g:each>
				totalSeries['${encuestaAsignada.medico.id}'] = series;
				
				$J("#chartContainer_${encuestaAsignada.medico.id}").on('shown',function(){
					var series = totalSeries['${encuestaAsignada.medico.id}'];
					var plot1 = $J.jqplot ('chart_${encuestaAsignada.medico.id}', [series],{
						axesDefaults: {
					        labelRenderer: $J.jqplot.CanvasAxisLabelRenderer
					      },
					    seriesDefaults:{
			               pointLabels: { show: true }
			            },
					    axes: {
					        xaxis: {
					          label: "Ultimos 3 meses",
					          tickOptions: {formatString: '%d'},
					        },
					        yaxis: {
					          label: "Monto",
					          tickOptions: {
						            mark: 'outside',    // Where to put the tick mark on the axis
						                                // 'outside', 'inside' or 'cross',
						            showMark: true,
						            showGridline: true, // wether to draw a gridline (across the whole grid) at this tick,
						            markSize: 4,        // length the tick will extend beyond the grid in pixels.  For
						                                // 'cross', length will be added above and below the grid boundary,
						            show: true,         // wether to show the tick (mark and label),
						            showLabel: true,    // wether to show the text label at the tick,
						            formatString: '',   // format string to use with the axis tick formatter
						        },
						        showTicks: true,        // wether or not to show the tick labels,
        						showTickMarks: true,    // wether or not to show the tick marks
					        }
					      }
					});	
					
				});
			</g:each>
		</g:javascript>
		
	</head>
	<body>		
		<div id="show-encuesta" role="main">
			<div class="page-header">
				<h1><g:message code="encuesta.estadistica.label" /></h1>
			</div>			
			<g:render template="nav-pills" />
			<g:if test="${flash.message}">
				<div class="alert alert-info" role="status">${flash.message}</div>
			</g:if>		
			
			<div id="seccion_general">
				<h2><g:message code="encuesta.estadistica.seccion.general.label" /></h2>
				<dl class="dl-horizontal">
					<g:if test="${encuestaInstance?.nombre}">
						<dt><g:message code="encuesta.nombre.label" default="Nombre" /></dt>
						<dd><g:fieldValue bean="${encuestaInstance}" field="nombre"/></dd>
					</g:if>
					<g:if test="${encuestaInstance?.descripcion}">
						<dt><g:message code="encuesta.descripcion.label" default="Descripción" /></dt>
						<dd><g:fieldValue bean="${encuestaInstance}" field="descripcion"/></dd>
					</g:if>
					<g:if test="${encuestaInstance?.fechaInicio}">
						<dt><g:message code="encuesta.fechaInicio.label" default="Fecha Inicio" /></dt>
						<dd><g:formatDate date="${encuestaInstance?.fechaInicio}" format="dd/MM/yyyy"/></dd>
					</g:if>
					<g:if test="${encuestaInstance?.fechaFin}">
						<dt><g:message code="encuesta.fechaFin.label" default="Fecha Fin" /></dt>
						<dd><g:formatDate date="${encuestaInstance?.fechaFin}" format="dd/MM/yyyy"/></dd>
					</g:if>
					<g:if test="${encuestaInstance?.estadisticasEncuesta?.cantVisitasSemCupo}">
						<dt><g:message code="encuesta.estadisticasEncuesta.cantVisitasSemCupo.label" default="Cantidad de visitas semanales a realizar para cubrir el cupo" /></dt>
						<dd><g:formatNumber number="${encuestaInstance.estadisticasEncuesta.cantVisitasSemCupo}" type="number" maxFractionDigits="2" /></dd>
					</g:if>
					<g:if test="${encuestaInstance?.estadisticasEncuesta?.canVisitasSemRealizadas}">
						<dt><g:message code="encuesta.estadisticasEncuesta.canVisitasSemRealizadas.label" default="Cantidad de visitas semanales realizadas" /></dt>
						<dd><g:formatNumber number="${encuestaInstance.estadisticasEncuesta.canVisitasSemRealizadas}" type="number" maxFractionDigits="2" /></dd>
					</g:if>
					<g:if test="${encuestaInstance?.estadisticasEncuesta?.cantVisitadasCanceladas}">
						<dt><g:message code="encuesta.estadisticasEncuesta.cantVisitadasCanceladas.label" default="Cantidad de visitas canceladas" /></dt>
						<dd><g:formatNumber number="${encuestaInstance.estadisticasEncuesta.cantVisitadasCanceladas}" type="number" maxFractionDigits="2" /></dd>
					</g:if>
					<g:if test="${encuestaInstance?.estadisticasEncuesta?.cantDerivacionesRealizadas}">
						<dt><g:message code="encuesta.estadisticasEncuesta.cantDerivacionesRealizadas.label" default="Cantidad de derivaciones realizadas" /></dt>
						<dd><g:formatNumber number="${encuestaInstance.estadisticasEncuesta.cantDerivacionesRealizadas}" type="number" maxFractionDigits="2" /></dd>
					</g:if>
				</dl>
			</div>
			
			<div style="clear: both"></div>
			<div id="seccion_encuesta">
				<h2><g:message code="encuesta.estadistica.seccion.encuesta.label" /></h2>
				
				<div id="chartContainer" class="accordion"></div>
			</div>
			
			<div id="seccion_medicos">
				<h2><g:message code="encuesta.estadistica.seccion.medicos.label" /></h2>
				
				<g:form  method="post" url="[action:'filtrarMedicos',controller:'encuesta']">
					<g:hiddenField name="idEncuesta" value="${ encuestaInstance.id}"/>
					<dl class="dl-horizontal">
						<dt><g:message code="encuestaAsignada.medico.visitador.nombre.label" default="Visitador" /></dt>
						<dd>
							<g:select name="encuestaAsignada.medico.visitador"
	          					from="${Visitador.list()}"
	          					value="${id}"
	          					optionKey="id" 
	          					optionValue="${apellido}"
	          				/>
	          					
	          				<g:submitButton name="aplicar" value="Aplicar" />
	          				
	          				<g:link action="verEstadisticas" id="${ encuestaInstance.id}">Cancelar</g:link>
						</dd>
					</dl>
	            </g:form>
	                 
				<table class="table table-striped table-bordered">
				<thead>
					<th><g:message code="encuesta.estadistica.medicos.nombreCompleto"/></th>
					<th><g:message code="encuesta.estadistica.medicos.especialidad"/></th>
					<th><g:message code="encuesta.estadistica.medicos.montoPromedio"/></th>
					<th><g:message code="encuesta.estadistica.medicos.minuta"/></th>
					<th><g:message code="encuesta.estadistica.medicos.histograma"/></th>
				</thead>
				<tbody>
					<g:each in="${encuestaAsignadaList}" status="i" var="encuestaAsignada">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td>${encuestaAsignada.medico.nombreCompleto}</td>
						<td>${encuestaAsignada.medico.especialidad.nombre}</td>
						<td style="text-align: right"><g:formatNumber number="${encuestaAsignada.medico.montoPromedioDerivado}" type="number" maxFractionDigits="2" /></td>
						<td>
							
							<g:if test="${encuestaAsignada?.minuta}">
								<a data-toggle="modal" href="#minuta_${encuestaAsignada.medico.id}">
									<img src="${resource(dir:'images',file: 'icon_note.png')}" style="padding-right: 5px">
								</a>
								<div class="modal hide fade" id="minuta_${encuestaAsignada.medico.id}" style="display: none; ">
								  <div class="modal-header">
								    <button class="close" data-dismiss="modal">×</button>
								    <h3><g:message code="encuesta.estadistica.medicos.minuta"/></h3>
								  </div>
								  <div class="modal-body">
								    <p>${encuestaAsignada.minuta}</p>
								  </div>
								  <div class="modal-footer">
								    <a href="#" class="btn" data-dismiss="modal">Cerrar</a>
								  </div>
								</div>
							</g:if>
							
						</td>
						<td>
							<a data-toggle="modal" href="#chartContainer_${encuestaAsignada.medico.id}">
								<img src="${resource(dir:'images',file: 'icon_histogram.png')}" style="padding-right: 5px">
							</a>
							
							<div class="modal hide fade" id="chartContainer_${encuestaAsignada.medico.id}" style="display: none; ">
								  <div class="modal-header">
								    <button class="close" data-dismiss="modal">×</button>
								    <h3><g:message code="encuesta.estadistica.medicos.histograma"/></h3>
								  </div>
								  <div class="modal-body">
								   		<div id="chart_${encuestaAsignada.medico.id}" style="width:500px; "></div>
								  </div>
								  <div class="modal-footer">
								    <a href="#" class="btn" data-dismiss="modal">Cerrar</a>
								  </div>
							</div>
							
							
						</td>
					</tr>
					</g:each>
				</tbody>
			</table>
			
			</div>

		</div>
	</body>
</html>

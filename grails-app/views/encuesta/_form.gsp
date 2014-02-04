<%@ page import="com.cdrossi.Encuesta" %>

<div class="control-group ${hasErrors(bean: encuestaInstance, field: 'nombre', 'error')}">
	<label for="nombre" class="control-label">
		<g:message code="encuesta.nombre.label" default="Nombre" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls">
		<g:textField name="nombre" value="${encuestaInstance?.nombre}" required="" value="${encuestaInstance?.nombre}"/>
	</div>
</div>

<div class="control-group ${hasErrors(bean: encuestaInstance, field: 'descripcion', 'error')}">
	<label for="descripcion" class="control-label">
		<g:message code="descripcion.nombre.label" default="Descripción" />
		
	</label>
	<div class="controls">
		<g:textArea name="descripcion" value="${encuestaInstance?.descripcion}" value="${encuestaInstance?.descripcion}"/>
	</div>
</div>


<div class="control-group ${hasErrors(bean: encuestaInstance, field: 'estadoEncuesta', 'error')}">
	<label for="estadoEncuesta" class="control-label">
		<g:message code="encuesta.estadoEncuesta.label" default="Estado Encuesta" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls">
		<g:if test="${encuestaInstance?.estadoEncuesta?.name()}">
			<g:set var="estadoEncuestaValue" value="${encuestaInstance?.estadoEncuesta?.name()}" />
		</g:if>
		<g:else>
			<g:set var="estadoEncuestaValue" value="${com.cdrossi.EstadoEncuesta.BORRADOR}" />
		</g:else>
		<g:select name="estadoEncuesta" from="${com.cdrossi.EstadoEncuesta?.values()}" keys="${com.cdrossi.EstadoEncuesta.values()*.name()}" required="" value="${estadoEncuestaValue}"/>
	</div>
</div>

<div class="control-group ${hasErrors(bean: encuestaInstance, field: 'fechaInicio', 'error')}">
	<label for="fechaInicio" class="control-label">
		<g:message code="encuesta.fechaInicio.label" default="Fecha Inicio" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls">
		<g:datePicker name="fechaInicio" precision="day"  value="${encuestaInstance?.fechaInicio}"  />
	</div>
</div>

<div class="control-group ${hasErrors(bean: encuestaInstance, field: 'fechaFin', 'error')}">
	<label for="fechaFin" class="control-label">
		<g:message code="encuesta.fechaFin.label" default="Fecha Fin" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls">
		<g:datePicker name="fechaFin" precision="day"  value="${encuestaInstance?.fechaFin}"  />
	</div>
</div>




<%@ page import="com.cdrossi.Pregunta" %>

<g:hiddenField name="idEncuesta" value="${idEncuesta}"/>

<div class="control-group ${hasErrors(bean: preguntaInstance, field: 'enunciado', 'error')}">
	<label for="enunciado" class="control-label">
		<g:message code="pregunta.enunciado.label" default="Enunciado" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls">
		<g:textField name="enunciado" value="${preguntaInstance?.enunciado}" required=""/>
	</div>
</div>

<div class="control-group ${hasErrors(bean: preguntaInstance, field: 'tipoPregunta', 'error')}">
	<label for="tipoPregunta" class="control-label">
		<g:message code="pregunta.tipoPregunta.label" default="Tipo Pregunta" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls">
		<g:select name="tipoPregunta" from="${com.cdrossi.TipoPregunta?.values()}" keys="${com.cdrossi.TipoPregunta.values()*.name()}" required="" value="${preguntaInstance?.tipoPregunta?.name()}"/>
	</div>
</div>

<div class="control-group">
	<label for="respuestas" class="control-label">
		<g:message code="pregunta.enunciado.label" />
		<span class="required-indicator">*</span>
	</label>
	<div class="controls">
		<g:textArea name="listaRespuestas" value="" required="" value="${preguntaInstance?.getRespuestasToString()}"/>
	</div>
</div>

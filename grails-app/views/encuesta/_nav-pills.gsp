<%@ page import="com.cdrossi.EstadoEncuesta" %>

<ul class="nav nav-pills">
    <%-- Set which "pill" of the menu is active --%>
	<li class="${ params.action == "list" ? 'active' : '' }">
		<g:link action="list"><g:message code="default.list.label" args="${[message(code:'encuestas.label')]}"/></g:link>
	</li>
	<li class="${ params.action == "create" || params.action == "save" ? 'active' : '' }">
		<g:link action="create"><g:message code="default.new_f.label"  args="${[message(code:'encuesta.label')]}"/></g:link>
	</li>
</ul>
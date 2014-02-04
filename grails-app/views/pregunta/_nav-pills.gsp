<ul class="nav nav-pills">
    <%-- Set which "pill" of the menu is active --%>    
	<li class="${ params.action == "list" ? 'active' : '' }">		
		<g:link controller="pregunta" action="list" params="[idEncuesta: idEncuesta]"><g:message code="default.list.label" args="${[message(code:'preguntas.label')]}"/></g:link>
	</li>
	<li class="${ params.action == "create" || params.action == "save" ? 'active' : '' }">
		<g:link class="create" action="create" params="[idEncuesta: idEncuesta]"><g:message code="default.new_f.label" args="${[message(code:'pregunta.label')]}"/></g:link>
	</li>
	<li>		
		<g:link controller="encuesta" action="show" id="${idEncuesta }">Volver a encuesta</g:link>
	</li>
</ul>
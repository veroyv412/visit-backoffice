<ul class="nav nav-pills">
    <%-- Set which "pill" of the menu is active --%>
	<li class="${ params.action == "list" ? 'active' : '' }">
		<g:link controller="medico" action="list"><g:message code="default.list.label" args="${[message(code:'medicos.label')]}"/></g:link>
	</li>		
	<li class="${ params.action == "importarMedicos" ? 'active' : '' }">
		<g:link class="list" controller="medico" action="importarMedicos"><g:message code="medico.import.label"/></g:link>
	</li>
	<li class="${ params.action == "leerDerivaciones" && params.controller == "derivaciones" ? 'active' : '' }">
		<g:link class="list" controller="derivaciones" action="leerDerivaciones"><g:message code="derivacion.importar.label"/></g:link>
	</li>
	<li>
		<g:link class="excel" controller="medico" action="exportNotes"><img src="${resource(dir:'images',file: 'icon_excel.png')}" style="padding-right: 5px"><g:message code="medico.notas.export.label"/></g:link>
	</li>
</ul>
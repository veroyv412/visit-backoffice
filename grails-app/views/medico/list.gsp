<%@ page import="com.cdrossi.Medico" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main-fullnavbar">
		<g:set var="entityName_pl" value="${message(code: 'medicos.label')}" />
		<title><g:message code="default.list.label" args="[entityName_pl]" /></title>
		<g:javascript library="prototype" />
        <g:javascript library="scriptaculous" />
        <filterpane:includes />
	</head>
	<body>		
		<div id="list-medico" role="main">
			<div class="page-header">
				<h1><g:message code="default.list.label" args="[entityName_pl]" /></h1>
			</div>
			<g:render template="nav-pills" />
			<g:if test="${flash.message}">
				<div class="alert alert-info" role="status">${flash.message}</div>
			</g:if>
			
			<filterpane:currentCriteria domainBean="com.cdrossi.Medico" removeImgDir="images" removeImgFile="bullet_delete.png" />
			
			<filterpane:filterButton/>
            <filterpane:filterPane id="filterPane" domain="com.cdrossi.Medico"
            	dialog="true"
            	titleKey="fp.tag.filterPane.titleText"
                dialog="false"
                visible="n"
                showSortPanel="n"
                showTitle="n"
                filterProperties="matriculaNacional,matriculaProvincial,matriculaEspecial,nombreCompleto,email,especialidad,sexo,estadoMedico"/>     				
			<table class="table table-striped table-bordered">
				<thead>
					<g:sortableColumn property="matriculaNacional" params="${filterParams}" title="${message(code: 'medico.matriculaNacional.label')}" />
					<g:sortableColumn property="matriculaProvincial" params="${filterParams}" title="${message(code: 'medico.matriculaProvincial.label')}" />
					<g:sortableColumn property="matriculaEspecial" params="${filterParams}" title="${message(code: 'medico.matriculaEspecial.label')}" />
					<g:sortableColumn property="nombreCompleto" params="${filterParams}" title="${message(code: 'medico.nombreCompleto.label')}" />
					<g:sortableColumn property="email" params="${filterParams}" title="${message(code: 'medico.email.label')}" />
				</thead>
				<tbody>
					<g:each in="${medicoInstanceList}" status="i" var="medicoInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						<td>${fieldValue(bean: medicoInstance, field: "matriculaNacional")}</td>
						<td>${fieldValue(bean: medicoInstance, field: "matriculaProvincial")}</td>
						<td>${fieldValue(bean: medicoInstance, field: "matriculaEspecial")}</td>
						<td><g:link action="show" id="${medicoInstance.id}">${fieldValue(bean: medicoInstance, field: "nombreCompleto")}</g:link></td>
						<td>${medicoInstance.email}</td>
					</tr>
					</g:each>
				</tbody>
			</table>
		</div>
  		 <div class="pagination">
	         <filterpane:paginate total="${medicoCount}" domain="com.cdrossi.Medico" />
        </div>
        
	</body>
</html>


<%@ page import="com.cdrossi.Pregunta" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main-fullnavbar">
		<g:set var="entityName_pl" value="${message(code: 'preguntas.label')}" />
		<title><g:message code="default.list.label" args="[entityName_pl]" /></title>
		
		<style>
			#sortable { list-style-type: none; cursor: move;}
			#sortable li { padding-top: 5px; padding-bottom: 5px; margin-top: 2px; margin-bottom: 2px;}
			#sortable span { margin-left: 15px; }
		</style>
		
		<g:javascript>
		var $J = jQuery.noConflict();
		
		$J(function() {
			$J("#sortable").sortable({ 
			    update : function () { 
			      var order = serialize($J('#sortable'));
			      order = JSON.stringify(order);
			      
			      $J.ajax({
					  url: 'updateOrder',
					  data: {order: order},
					  type: "POST",
					  dataType: "json"
					});
			    } 
			}); 
			
		});
		
		function serialize( element )
		{
			var serialized = [];
		    $J(element).find('li').each(function(i) {
		    		var obj = {};	
		           	var idPregunta = $J(this).attr("idPregunta");
		           	obj.idPregunta = idPregunta;
		           	obj.position = i + 1;
		           	
		           	serialized[i] = obj;
		    });  

			return serialized;
		}
		
		</g:javascript>
		
	</head>
	<body>			
		<div id="list-pregunta" class="content scaffold-list" role="main">
			<div class="page-header">
				<h1><g:message code="default.list.label" args="[entityName_pl]" /></h1>
			</div>			
			<g:render template="nav-pills" />
			<g:if test="${flash.message}">
				<div class="alert alert-info" role="status">${flash.message}</div>
			</g:if>
			<table style="margin-bottom: 1px;" class="table table-striped table-bordered">
				<thead>
					<tr>
						<th style="width: 700px">${message(code: 'pregunta.enunciado.label')}</th>
						<th style="width: 260px">${message(code: 'pregunta.tipoPregunta.label')}</th>
					</tr>
				</thead>
			</table>
			<ul id="sortable" class="unstyled">
				<g:each in="${preguntaInstanceList}" status="i" var="preguntaInstance">
				<li class="ui-state-default" idPregunta="${preguntaInstance.id}">
					<div style="float: left; width: 35px"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span></div>
					<div style="float: left; width: 645px"><g:link action="show" id="${preguntaInstance.id}" params="[idEncuesta: idEncuesta]">${fieldValue(bean: preguntaInstance, field: "enunciado")}</g:link></div>
					<div style="float: left; width: 240px">${fieldValue(bean: preguntaInstance, field: "tipoPregunta")}</div>
					<div style="clear: both"></div>
				</li>
				</g:each>				
			</ul>			
			
			<div class="pagination">
				<g:paginate total="${preguntaInstanceTotal}" />
			</div>
		</div>
	</body>
</html>

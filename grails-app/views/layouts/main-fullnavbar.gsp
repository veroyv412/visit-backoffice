<g:applyLayout name="main">
<head>
	<title><g:layoutTitle /></title>
	<g:layoutHead />
</head>
<body>
<g:layoutBody />
</body>
<content tag="navbar">
	<ul class="nav">							
		<li <g:if test="${controllerName == 'medico' || controllerName == 'derivaciones'}">class="active"</g:if>>
			<g:link controller="medico"><g:message code="menu.medicos" /></g:link>
		</li>
		<li <g:if test="${controllerName == 'usuario' }">class="active" </g:if>>
			<g:link controller="usuario" action="list"><g:message code="menu.usuarios" /></g:link>
		</li>
		<li <g:if test="${controllerName == 'encuesta' || controllerName == 'pregunta' }">class="active" </g:if>>
			<g:link controller="encuesta" action="list"><g:message code="menu.encuestas" /></g:link>
		</li>
	</ul>
	
	<ul class="nav pull-right">
		<li class="dropdown">
		    <a href="#" class="dropdown-toggle"
		          data-toggle="dropdown">
		          <sec:loggedInUserInfo field="nombreCompleto"/>
		          <b class="caret"></b>
		    </a>
		    <ul class="dropdown-menu">
		      <g:link controller="logout">cerrar sesión</g:link>
		    </ul>
	  </li>
	</ul>
</content>	
</g:applyLayout>
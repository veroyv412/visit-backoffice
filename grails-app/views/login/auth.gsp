<html>
<head>
	<meta name='layout' content='main'/>
	<title><g:message code="login.title"/></title>	
</head>

<body>
	<div class="container well" style="width: 600px;">	
		<g:if test='${flash.message}'>
			<div class='alert alert-error'>${flash.message}</div>
		</g:if>			
		
		<form action='${postUrl}' method='POST' id='loginForm' autocomplete='off' class="form-horizontal">
			<fieldset>
				<legend><g:message code="login.header"/></legend>
				
				<div class="control-group">
					<label class="control-label" for='username'><g:message code="login.username.label"/></label>
					<div class="controls">
						<input type='text' name='j_username' id='username'/>
					</div>
				</div>			
				<div class="control-group">				
					<label class="control-label" for='password'><g:message code="login.password.label"/></label>
					<div class="controls">
						<input type='password' name='j_password' id='password'/>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">										
						<button type="submit" id="submit" class="btn btn-primary">${message(code: "login.submit")}</button>
						<label class="checkbox inline">
							<input type='checkbox' name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>/>
							<g:message code="login.remember.me.label"/>
						</label>						
					</div>
				</div>						
			</fieldset>
		</form>
		
		
	</div>
	<script type='text/javascript'>
		<!--
		(function() {
			document.forms['loginForm'].elements['j_username'].focus();
		})();
		// -->
	</script>
</body>
</html>

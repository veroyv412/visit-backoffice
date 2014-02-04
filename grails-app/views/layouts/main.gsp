<!doctype html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>CDR - <g:layoutTitle/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href="/images/favicon.ico" rel="shortcut icon" />
		<r:require modules="bootstrap-js, custom-bootstrap"/>
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
		
		<g:javascript library="jquery" plugin="jquery" />
		<r:require module="jquery-ui"/>
		
		<g:javascript library="prototype" />
		<g:javascript library="application"/>
		<g:javascript library="json"/>
				
		<g:layoutHead/>
        <r:require module="fileuploader" />
  		<r:layoutResources />
        
        <jqplot:resources/>
        <jqplot:plugin name="barRenderer"/>
        <jqplot:plugin name="categoryAxisRenderer"/>
        <jqplot:plugin name="pointLabels"/>
        <jqplot:plugin name="canvasTextRenderer"/>
        <jqplot:plugin name="canvasAxisLabelRenderer"/>
        <jqplot:plugin name="highlighter"/>
        <jqplot:plugin name="pointLabels"/>
        <jqplot:plugin name="pieRenderer"/>
        
        <g:javascript>
           function showSpinner(visible) {
              $('spinner').style.display = visible ? "inline" : "none";
           }
           Ajax.Responders.register({
           onLoading: function() {
                 showSpinner(true);
           },
           onComplete: function() {
             if(!Ajax.activeRequestCount) showSpinner(false);
           }
           });
         </g:javascript>
	</head>
	<body>
		<div class="navbar navbar-fixed-top">
			<div class="navbar-inner">
				<div class="container">
					<a href="${createLink(uri: '/')}" class="brand" style="padding-bottom: 5px; padding-top: 5px;"><img src="${resource(dir:'images',file:'cdr-logo.png')}" style="height: 28px;"/></a>					
					<g:pageProperty name="page.navbar"/>
				</div>		   		
			</div>
        </div>
        <div class="container" style="margin-top: 45px;">
        	<div id="spinner" class="spinner" style="display:none;">
				<img src="${resource(dir:'images',file:'spinner.gif')}" alt="${message(code:'spinner.alt',default:'Cargando...')}" />
			</div>					
			<g:layoutBody/>
			<div class="footer" role="contentinfo"></div>
			<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
	        <r:layoutResources />
		</div>
	</body>
</html>
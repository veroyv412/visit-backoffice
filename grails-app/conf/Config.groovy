import grails.plugins.springsecurity.SecurityConfigType;

// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }


grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
						xml: ['text/xml', 'application/xml'],
						text: 'text-plain',
						js: 'text/javascript',
						rss: 'application/rss+xml',
						atom: 'application/atom+xml',
						css: 'text/css',
						csv: 'text/csv',
						pdf: 'application/pdf',
						rtf: 'application/rtf',
						excel: 'application/vnd.ms-excel',
						ods: 'application/vnd.oasis.opendocument.spreadsheet',
						all: '*/*',
						json: ['application/json','text/json'],
						form: 'application/x-www-form-urlencoded',
						multipartForm: 'multipart/form-data'
					  ]



// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']


// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// enable query caching by default
grails.hibernate.cache.queries = true

// set per-environment serverURL stem for creating absolute links
environments {
    development {
        grails.logging.jul.usebridge = true
		grails.gorm.failOnError=false
		// Don't use security annotations for development
		grails.plugins.springsecurity.securityConfigType = SecurityConfigType.Requestmap
		
		asignacionMedicoFilesPath = 'resources/medicos/asignaciones/files/'
		asignacionMedicoLogFilePath = 'resources/medicos/asignaciones/log/'
		asignacionMedicoLogFileName = 'asignacionMedico.log'
		
		asignacionVisitadorFileParh = 'resources/usuarios/asignaciones/files/'
		
		importarDerivacionesFileParh = 'resources/derivaciones/files/'
		
		derivacionesMedicoFilesPath = 'resources/medicos/derivaciones/'
		derivacionesMedicoFileName = 'MedicosDerivadores2.csv'
		
    }
	test {
		grails.logging.jul.usebridge = false
		
		baseCDRPath = '/var/cdr'
		
		asignacionMedicoFilesPath = baseCDRPath + 'resources/medicos/asignaciones/files/'
		asignacionMedicoLogFilePath = baseCDRPath + 'resources/medicos/asignaciones/log/'
		asignacionMedicoLogFileName = baseCDRPath + 'asignacionMedico.log'
		
		asignacionVisitadorFileParh = baseCDRPath + 'resources/usuarios/asignaciones/files/'
		
		importarDerivacionesFileParh = baseCDRPath + 'resources/derivaciones/files/'
		
		derivacionesMedicoFilesPath = baseCDRPath + 'resources/medicos/derivaciones/'
		derivacionesMedicoFileName = 'MedicosDerivadores2.csv'		
	}
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
		
		baseCDRPath = '/var/cdr'
		
		asignacionMedicoFilesPath = baseCDRPath + 'resources/medicos/asignaciones/files/'
		asignacionMedicoLogFilePath = baseCDRPath + 'resources/medicos/asignaciones/log/'
		asignacionMedicoLogFileName = baseCDRPath + 'asignacionMedico.log'
		
		asignacionVisitadorFileParh = baseCDRPath + 'resources/usuarios/asignaciones/files/'
		
		importarDerivacionesFileParh = baseCDRPath + 'resources/derivaciones/files/'
		
		derivacionesMedicoFilesPath = baseCDRPath + 'resources/medicos/derivaciones/'
		derivacionesMedicoFileName = 'MedicosDerivadores2.csv'
    }
}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console
    // appender:
    //
	
	//trace 'org.hibernate.type'
	//trace 'org.hibernate.SQL' 
	
    appenders {
        console name:'stdout', layout:pattern(conversionPattern: '%d{yyyy-MM-dd HH:mm:ss}')
    }

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'
}

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'com.cdrossi.Usuario'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'com.cdrossi.UsuarioRole'
grails.plugins.springsecurity.authority.className = 'com.cdrossi.Role'
grails.plugins.springsecurity.userLookup.usernamePropertyName = 'email'
grails.plugins.springsecurity.userLookup.passwordPropertyName = 'contrasenia'
grails.plugins.springsecurity.password.algorithm='SHA-1'

/*
 * CONFIGURAR cuando tenga bien el CSS
modules = {
	
		fileuploader {
			resource url:'/js/fileuploader.js'
			resource url:'/css/uploader.css'
		}
	
	}*/


grails.plugins.twitterbootstrap.fixtaglib = true

grails.resources.modules = {
	
		'custom-bootstrap' {
			dependsOn 'bootstrap'
			resource url:[dir: 'less', file: 'custom-bootstrap.less'], attrs:[rel: "stylesheet/less", type:'css']
		}
	
	}
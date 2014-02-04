import org.codehaus.groovy.grails.plugins.log4j.EnvironmentsLog4JConfig;

class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"	(controller : "medico", action: 'index')
		"403"(controller: "error", action: "forbidden")
		"404"(controller: "error", action: "notFound")
		"500"(controller: "error", action: "serverError")
		
		/*"/sync-medicos/"(controller:"medico") {
			action = [GET: "listJSON"]
		}*/
	}
}

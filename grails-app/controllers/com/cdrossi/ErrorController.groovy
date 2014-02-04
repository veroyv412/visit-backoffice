package com.cdrossi

import grails.util.Environment

class ErrorController {

	def serverError() {
		switch (Environment.current) {
			case Environment.DEVELOPMENT:
				render(view:'exception')
				break
			case Environment.PRODUCTION:
				render( view:'error', model:[msg:'error.500'])
				break
		}		
	}

	def forbidden() {
		render( view:'error', model:[msg:'error.403'])
	}

	def notFound() {
		render( view:'error', model:[msg:'error.404'])
	}
}

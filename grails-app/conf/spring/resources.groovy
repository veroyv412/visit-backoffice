// Place your Spring DSL code here
beans = {
	userDetailsService(com.cdrossi.CustomUserDetailsService)

	localeResolver(org.springframework.web.servlet.i18n.SessionLocaleResolver) {
		defaultLocale = new Locale("es", "AR")
		java.util.Locale.setDefault(defaultLocale)
	}
	
	userDetailsService(com.cdrossi.security.CDRUserDetailsService)
}

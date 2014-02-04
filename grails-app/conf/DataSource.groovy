dataSource {
    pooled = true    
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
	show_sql=true
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE"
	    driverClassName = "org.h2.Driver"
	    username = "sa"
	    password = ""
        }
    }
    test {
        /*dataSource {
			dbCreate = "update"
			url = "jdbc:mysql://localhost:3306/cdr-visitadores"
			driverClassName = "com.mysql.jdbc.Driver"
			dialect = org.hibernate.dialect.MySQL5InnoDBDialect
			username = "cdr"
			password = "cdr"
        }*/
		
		dataSource {
			dbCreate = "update"
			url = "jdbc:mysql://192.168.0.10:3306/cdr-visitadores"
			driverClassName = "com.mysql.jdbc.Driver"
			dialect = org.hibernate.dialect.MySQL5InnoDBDialect
			username = "cdr"
			password = "cdr"
		}
    }
    production {
        dataSource {
            dbCreate = "validate"
			url = "jdbc:mysql://otherhost:3306/cdr-visitadores"
			driverClassName = "com.mysql.jdbc.Driver"
			dialect = org.hibernate.dialect.MySQL5InnoDBDialect
			username = "cdr"
			password = "cdr"
        }
    }
}

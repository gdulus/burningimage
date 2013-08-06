dataSource {
	pooled = true
	driverClassName = "org.hsqldb.jdbcDriver"
	username = "sa"
	password = ""
}
hibernate {
    cache.use_second_level_cache=true
    cache.use_query_cache=true
    cache.provider_class='com.opensymphony.oscache.hibernate.OSCacheProvider'
}
// environment specific settings
environments {
	development {
		dataSource {
            driverClassName = "org.h2.Driver"
            // In memory database
            url = "jdbc:h2:mem:devDb;MVCC=TRUE"

            // File-based database
            //url = "jdbc:h2:prodDb;MVCC=TRUE"
		}
	}
	test {
		dataSource {
            driverClassName = "org.h2.Driver"
            // In memory database
            url = "jdbc:h2:mem:devDb;MVCC=TRUE"

            // File-based database
            //url = "jdbc:h2:prodDb;MVCC=TRUE"
		}
	}
	production {
		dataSource {
            driverClassName = "org.h2.Driver"
            // In memory database
            url = "jdbc:h2:mem:devDb;MVCC=TRUE"

            // File-based database
            //url = "jdbc:h2:prodDb;MVCC=TRUE"
		}
	}
}
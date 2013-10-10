hibernate {
    cache.use_second_level_cache = false
    cache.use_query_cache = false
}

environments {
    test {
        dataSource {
            driverClassName = "org.h2.Driver"
            url = "jdbc:h2:mem:devDb;MVCC=TRUE"
            username = "sa"
            password = ""
            dbCreate = 'create-drop'
        }
    }
}
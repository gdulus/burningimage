grails.project.dependency.resolution = {
    inherits("global") {}
    log "error"
    checksums true
    legacyResolve false

    repositories {
        inherits true
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }

    dependencies {
    }

    plugins {
    }
}

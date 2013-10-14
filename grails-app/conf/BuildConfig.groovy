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
        compile 'jmagick:jmagick:6.2.4'
        compile 'asm:asm:3.3.1'
        compile 'commons-lang:commons-lang:2.6'
    }

    plugins {
    }
}

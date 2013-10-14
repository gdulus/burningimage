import pl.burningice.plugins.image.ResourcePathProvider
import pl.burningice.plugins.image.container.ContainerWorkerFactory

class BurningImageGrailsPlugin {

    def version = "0.5.0"

    def grailsVersion = "1.2 > *"

    def dependsOn = [:]

    def pluginExcludes = [
        "grails-app/views/error.gsp",
        "grails-app/domain/pl/burningice/plugins/image/ast/test/**",
        "resources/**",
        "web-app/**"
    ]

    def author = "Pawel Gdula"

    def authorEmail = "pawel.gdula@burningice.pl"

    def title = "Burning Image"

    def description = "Image manipulation plugin"

    def documentation = "https://github.com/gdulus/burningimage/wiki"

    def doWithSpring = {
        resourcePathProvider(ResourcePathProvider) { it.autowire = 'byName' }
        containerWorkerFactory(ContainerWorkerFactory) { it.autowire = 'byName' }
    }

    def doWithApplicationContext = { applicationContext ->
    }

    def doWithWebDescriptor = { xml ->
    }

    def doWithDynamicMethods = { ctx ->
    }

    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }
}

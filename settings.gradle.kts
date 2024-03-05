import java.io.FileInputStream
import java.net.URI
import java.util.Properties

fun magic(key: String): String {

    val propFile = File(settings.rootDir.path, "local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties[key] as String


}

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = URI.create("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication {
                BasicAuthentication { "kutas" }
            }
            credentials {
                username = "infinifen"
                password = magic("MAP_API_KEY")
            }
        }
    }
}

rootProject.name = "gexplorer-mobile"
include(":app")

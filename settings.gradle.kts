import java.io.FileInputStream
import java.io.InputStreamReader
import java.net.URI
import java.util.Properties

fun getLocalProperty(key: String, file: String = "local.properties"): String? {
    val properties = Properties()
    val localProperties = File(file)
    if (localProperties.isFile) {
        InputStreamReader(FileInputStream(localProperties)).use { reader ->
            properties.load(reader)
        }
    } else error("File: $file not found")

    return properties.getProperty(key)
}
/*fun magic(key: String): String {

    val propFile = File(settings.rootDir.path, "local.properties")
    val properties = Properties()
    properties.load(FileInputStream(propFile))
    return properties[key] as String
*/

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
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
                password = getLocalProperty("MAPS_API_KEY")
                //magic("MAPS_API_KEY")
            }
        }
    }
}
rootProject.name = "gexplorer-mobile"
include(":app")

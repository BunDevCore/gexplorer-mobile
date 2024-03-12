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
            //USE THE RIGHT DOCS -> https://docs.mapbox.com/android/maps/guides/install
            url = URI.create("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication.create<BasicAuthentication>("basic")
            credentials.username = "mapbox"
            credentials.password = getLocalProperty("MAPS_API_KEY")
        }
    }
}
rootProject.name = "gexplorer-mobile"
include(":app")

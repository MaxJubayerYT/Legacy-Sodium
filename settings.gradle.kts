rootProject.name = "legacy-sodium"

pluginManagement {
    repositories {
        mavenLocal()
        maven { url = uri("https://maven.fabricmc.net/") }
        maven { url = uri("https://maven.legacyfabric.net/") }
        maven { url = uri("https://maven.neoforged.net/releases/") }
        gradlePluginPortal()
    }
}

val versionProfile = providers.gradleProperty("versionProfile").orElse("1.8.9").get()
val versionProps = java.util.Properties()
file("versions/$versionProfile.properties").takeIf { it.exists() }?.inputStream()?.use {
    versionProps.load(it)
} ?: throw GradleException("Missing version profile: versions/$versionProfile.properties")

gradle.beforeProject {
    versionProps.forEach { (key, value) ->
        project.extensions.extraProperties.set(key.toString(), value)
    }
}

// Legacy Sodium targets Legacy Fabric. Modern Fabric/NeoForge subprojects are kept in-tree
// for reference but excluded from the default build. Pass -Plegacy.mode=false to include them.
val legacyMode = providers.gradleProperty("legacy.mode").orElse("true").get().toBoolean()

include("legacy-fabric")

if (!legacyMode) {
    include("common")
    include("frapi")
    include("fabric")
    include("neoforge")
}

plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven {
        name = "FabricMC"
        url = uri("https://maven.fabricmc.net/")
    }
    maven {
        name = "LegacyFabric"
        url = uri("https://maven.legacyfabric.net/")
    }
}

dependencies {
    // fabric-loom-remap and legacy-looming are applied as plugins inside the
    // convention scripts (multiloader-legacy-platform.gradle.kts → legacy-fabric/build.gradle.kts).
    // Gradle requires their classpath to be declared here so the convention plugin
    // can resolve them at configuration time.
    implementation("net.fabricmc:fabric-loom:1.15-SNAPSHOT")
    implementation("legacy-looming:legacy-looming.gradle.plugin:1.15-SNAPSHOT")
}
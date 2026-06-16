plugins {
    id("java-library")
    id("idea")
}

group = project.findProperty("maven_group") ?: "io.github.maxjubayeryt"
version = BuildConfig.createVersionString(project)

val javaVersion = BuildConfig.javaVersion(project)

java {
    toolchain {
        // Use Java 21 as the toolchain (available in CI), but compile targeting javaVersion (8)
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(javaVersion)
}

tasks.withType<GenerateModuleMetadata>().configureEach {
    enabled = false
}

repositories {
    maven {
        name = "LegacyFabric"
        url = uri("https://maven.legacyfabric.net/")
    }
    maven {
        name = "FabricMC"
        url = uri("https://maven.fabricmc.net/")
    }
}
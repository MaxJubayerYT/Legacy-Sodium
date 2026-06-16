plugins {
    id("java-library")
    id("idea")
}

group = project.findProperty("maven_group") ?: "io.github.maxjubayeryt"
version = BuildConfig.createVersionString(project)

val javaVersion = BuildConfig.javaVersion(project)

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(maxOf(javaVersion, 8)))
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
        url = uri("https://maven.legacyfabric.net/repository/legacyfabric/")
    }
}

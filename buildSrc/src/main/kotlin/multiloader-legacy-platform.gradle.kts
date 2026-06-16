plugins {
    id("multiloader-legacy")
    id("maven-publish")
}

val configurationDesktopIntegrationJava: Configuration = configurations.create("commonDesktopIntegration") {
    isCanBeResolved = true
}

tasks {
    processResources {
        inputs.property("version", version)

        filesMatching(listOf("fabric.mod.json")) {
            expand(
                mapOf(
                    "version" to inputs.properties["version"],
                    "minecraft_version" to BuildConfig.minecraftVersion(project),
                )
            )
        }
    }

    jar {
        duplicatesStrategy = DuplicatesStrategy.FAIL
        from(rootDir.resolve("LICENSE.md"))
    }
}

publishing {
    repositories {
        // Publishing configuration will be added when releases are ready.
    }
}

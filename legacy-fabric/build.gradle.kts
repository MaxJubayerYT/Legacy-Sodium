plugins {
    id("multiloader-legacy-platform")
    id("net.fabricmc.fabric-loom-remap") version(extra["loom_version"] as String)
    id("legacy-looming") version(extra["loom_version"] as String)
}

base {
    archivesName = project.findProperty("archives_base_name") as String? ?: "legacy-sodium"
}

val mcVersion = BuildConfig.minecraftVersion(project)
val yarnBuild = BuildConfig.yarnBuild(project)

dependencies {
    minecraft("com.mojang:minecraft:$mcVersion")
    mappings("net.legacyfabric:yarn:$mcVersion+build.$yarnBuild:v2")
    modImplementation("net.fabricmc:fabric-loader:${BuildConfig.loaderVersion(project)}")
    modImplementation("net.legacyfabric.legacy-fabric-api:legacy-fabric-api:${BuildConfig.legacyFabricApiVersion(project)}")

    compileOnly("net.fabricmc:sponge-mixin:0.13.2+mixin.0.8.5")
    annotationProcessor("net.fabricmc:sponge-mixin:0.13.2+mixin.0.8.5")
}

loom {
    mixin {
        useLegacyMixinAp = false
    }

    // Required for 1.7.x — the vanilla launcher manifest doesn't include these versions
    if (mcVersion.startsWith("1.7.")) {
        customMinecraftManifest.set("https://meta.legacyfabric.net/v2/manifest/$mcVersion")
    }

    runs {
        named("client") {
            client()
            configName = "Legacy Sodium/Client ($mcVersion)"
            appendProjectPathToConfigName = false
            ideConfigGenerated(true)
            runDir("run")

            if (mcVersion.startsWith("1.7.")) {
                programArgs("--userProperties", "{}")
            }
        }
    }
}

tasks {
    jar {
        destinationDirectory.set(file(rootProject.layout.buildDirectory).resolve("mods"))
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group as String
            artifactId = rootProject.name + "-" + project.name
            version = version

            from(components["java"])
        }
    }
}
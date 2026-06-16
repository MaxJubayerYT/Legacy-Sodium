import org.gradle.api.Project

object BuildConfig {
    const val MOD_ID: String = "legacy-sodium"
    const val MOD_NAME: String = "Legacy Sodium"
    const val MOD_AUTHOR: String = "MaxJubayerYT"

    // https://semver.org/
    const val MOD_VERSION: String = "0.1.0-SNAPSHOT"

    val CURSEFORGE_PROJECT_ID = ""
    val MODRINTH_PROJECT_ID = ""

    fun minecraftVersion(project: Project): String =
        project.property("minecraft_version") as String

    fun yarnBuild(project: Project): String =
        project.property("yarn_build") as String

    fun loaderVersion(project: Project): String =
        project.property("loader_version") as String

    fun loomVersion(project: Project): String =
        project.property("loom_version") as String

    fun legacyFabricApiVersion(project: Project): String =
        project.property("legacy_fabric_api_version") as String

    fun javaVersion(project: Project): Int =
        (project.findProperty("java_version") as String?)?.toIntOrNull() ?: 8

    val MINECRAFT_VERSION: String get() = "1.8.9"

    val MINECRAFT_VERSION_SHORT: String
        get() = MINECRAFT_VERSION
            .replace("-snapshot-", "s")
            .replace("-pre-", "p")
            .replace("-rc-", "r")

    val RELEASE_TAG: String get() = "mc$MINECRAFT_VERSION_SHORT-${MOD_VERSION.substringBefore("-")}"

    fun createVersionString(project: Project): String {
        val builder = StringBuilder()
        val mcVersion = minecraftVersion(project)
        val mcVersionShort = mcVersion
            .replace("-snapshot-", "s")
            .replace("-pre-", "p")
            .replace("-rc-", "r")

        val isReleaseBuild = project.hasProperty("build.release")
        val buildId = System.getenv("GITHUB_RUN_NUMBER")

        if (isReleaseBuild) {
            builder.append(MOD_VERSION.substringBefore("-"))
        } else {
            builder.append(MOD_VERSION.substringBefore("-"))
            builder.append("-SNAPSHOT")
        }

        builder.append("+mc").append(mcVersionShort)

        if (!isReleaseBuild) {
            if (buildId != null) {
                builder.append("-build.${buildId}")
            } else {
                builder.append("-local")
            }
        }

        return builder.toString()
    }

    fun calculateGitHash(project: Project): String = try {
        val output = project.providers.exec {
            workingDir(project.projectDir)
            commandLine("git", "rev-parse", "HEAD")
        }
        output.standardOutput.asText.get().trim()
    } catch (_: Throwable) {
        "unknown"
    }

    fun getChangelog(project: Project): String {
        val changelogFile = project.rootProject.file("CHANGELOG.md")
        if (!changelogFile.exists()) {
            return "Legacy Sodium ${MOD_VERSION} for Minecraft ${minecraftVersion(project)}"
        }

        return changelogFile.readText()
            .split("----------")
            .getOrNull(1)
            ?.trim()
            ?.replace("[ReleaseTag]()", RELEASE_TAG)
            ?.replace("[MCVersion]()", minecraftVersion(project))
            ?.replace("[SodiumVersion]()", MOD_VERSION)
            ?: "Legacy Sodium ${MOD_VERSION} for Minecraft ${minecraftVersion(project)}"
    }
}

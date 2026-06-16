import me.modmuss50.mpp.ReleaseType
import me.modmuss50.mpp.platforms.curseforge.CurseforgeOptions
import me.modmuss50.mpp.platforms.modrinth.ModrinthOptions
import java.util.*

plugins {
    id("me.modmuss50.mod-publish-plugin") version("1.1.0")
}

gradle.projectsEvaluated {
    publishMods {
        if (!project.hasProperty("build.release")) {
            return@publishMods println("Publishing is disabled, please use the CI publishing workflow")
        }

        val releasePlatform: String = project.providers.gradleProperty("build.release.platform").orNull
                ?: return@publishMods println("build.release.platform must be defined (expected: legacy-fabric)")

        val releaseDestination: String = project.providers.gradleProperty("build.release.destination").orNull
                ?: return@publishMods println("build.release.destination must be defined (expected: GH+MR+CF, GH+MR, GH)")
        val publishModrinth = releaseDestination.contains("MR")
        val publishCurseforge = releaseDestination.contains("CF")

        val modVersion = BuildConfig.createVersionString(project)

        type = when {
            modVersion.contains("alpha") -> ReleaseType.ALPHA
            modVersion.contains("beta") -> ReleaseType.BETA
            else -> ReleaseType.STABLE
        }
        changelog = BuildConfig.getChangelog(project)

        val mcVersion = BuildConfig.minecraftVersion(project)

        val curseforgeShared = curseforgeOptions {
            accessToken = project.providers.environmentVariable("CURSEFORGE_API_KEY")
            projectId = BuildConfig.CURSEFORGE_PROJECT_ID
            minecraftVersions.add(mcVersion)
        }

        val modrinthShared = modrinthOptions {
            accessToken = project.providers.environmentVariable("MODRINTH_API_KEY")
            projectId = BuildConfig.MODRINTH_PROJECT_ID
            minecraftVersions.add(mcVersion)
        }

        setupFor("LegacyFabric", releasePlatform, publishCurseforge, publishModrinth, curseforgeShared, modrinthShared)

        github {
            accessToken = project.providers.environmentVariable("GITHUB_TOKEN")
            repository = "MaxJubayerYT/Legacy-Sodium"
            commitish = BuildConfig.calculateGitHash(project)
            version = BuildConfig.RELEASE_TAG
            displayName = "Legacy Sodium ${BuildConfig.MOD_VERSION} for Minecraft $mcVersion"
            file.unset()
            file.unsetConvention()

            allowEmptyFiles = true
        }
    }
}

fun me.modmuss50.mpp.ModPublishExtension.setupFor(loaderName: String, releasePlatform: String, publishCurseforge: Boolean, publishModrinth: Boolean, curseforgeOptions: Provider<CurseforgeOptions>, modrinthOptions: Provider<ModrinthOptions>) {
    val loaderLowercase = loaderName.lowercase(Locale.ROOT)
    val projectName = if (loaderLowercase == "legacyfabric") "legacy-fabric" else loaderLowercase

    if (releasePlatform == "both" || releasePlatform == loaderLowercase || releasePlatform == projectName) {
        val jar = project(":$projectName").tasks.named<Jar>("jar").get().archiveFile

        val mcVersion = BuildConfig.minecraftVersion(project)
        val releaseTitle = "Legacy Sodium ${BuildConfig.MOD_VERSION} for $loaderName $mcVersion"
        val releaseVersion = "${BuildConfig.RELEASE_TAG}-$projectName"

        if (publishCurseforge) {
            curseforge("curseforge$loaderName") {
                from(curseforgeOptions)

                file.set(jar)
                displayName = releaseTitle
                version = releaseVersion
                modLoaders.add("fabric")

                clientRequired = true
                serverRequired = false
            }
        }

        if (publishModrinth) {
            modrinth("modrinth$loaderName") {
                from(modrinthOptions)

                file.set(jar)
                displayName = releaseTitle
                version = releaseVersion
                modLoaders.add("fabric")
            }
        }
    }
}

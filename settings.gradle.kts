pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "mobile_development"

include(":GuessTheNumber")
project(":GuessTheNumber").projectDir = file("GuessTheNumber/app")

include(":RandomFilm")
project(":RandomFilm").projectDir = file("RandomFilm/app")

include(":PortraitLandscape")
project(":PortraitLandscape").projectDir = file("PortraitLandscape/app")

include(":TestCanvas")
project(":TestCanvas").projectDir = file("TestCanvas/app")

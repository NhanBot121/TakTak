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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TakTak"
include(":app")
include(":core")
include(":common:theme")
include(":common:composable")
include(":feature:discovery")
include(":feature:home")
include(":data")
include(":feature:inbox")
include(":feature:myprofile")
include(":feature:cameramedia")

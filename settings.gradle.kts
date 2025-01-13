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
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) // This is correct for preventing project-level repositories
    repositories {
        google()
        mavenCentral()
        // Add a local Maven repository where your AAR files are stored
        maven {
//            url = uri("${rootDir}\\app\\libs\\repo")
            url = uri("https://github.com/alifhasnain/sdktest/raw/refs/heads/dev/repo")
        }
        maven {
            url = uri("https://storage.googleapis.com/download.flutter.io")
        }
    }
}


rootProject.name = "NativeSDKExample"
include(":app")
include(":cashbabanativesdk")

//pluginManagement {
//    repositories {
//        google {
//            content {
//                includeGroupByRegex("com\\.android.*")
//                includeGroupByRegex("com\\.google.*")
//                includeGroupByRegex("androidx.*")
//            }
//        }
//        mavenCentral()
//        gradlePluginPortal()
//    }
//}
//dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
//    repositories {
//        google()
//        mavenCentral()
//    }
//}
//
//rootProject.name = "TestAARIntegration"
//include(":app")
//


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
            url = uri("/home/rafid/Desktop/flutter_sdk_implementation/cashbaba_merchant_sdk_implementation/cashbaba_sdk/app/libs/repo")
            //url = uri("/home/rafid/Desktop/flutter_sdk_projects/cashbaba_merchant_sdk/build/host/outputs/repo")
        }
        maven {
            url =
                uri("https://storage.googleapis.com/download.flutter.io") // Use https URL for Flutter repo
        }
    }
}


rootProject.name = "TestAARIntegration"
include(":app")

//include(":sdk")
//project(":sdk").projectDir =  File("/home/rafid/Desktop/flutter_sdk_projects/FlutterAARIntegration/app/libs/repo/com/example/cashbaba_merchant_sdk/sdk")

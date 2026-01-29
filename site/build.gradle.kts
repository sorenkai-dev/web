
import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link
import kotlinx.html.script

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kobwebx.markdown)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.spotless)
}

group = "com.sorenkai.web"
version = "2.0-SNAPSHOT"

kobweb {
    app {
        index {
            head.add {
                script {
                    // You can inject the raw string here if the DSL is being stubborn
                    """<base href="/">""".trimIndent()
                }
                link {
                    href =
                        "https://unpkg.com/material-components-web@latest/dist/material-components-web.min.css"
                    rel = "stylesheet"
                }
                script {
                    src = "https://unpkg.com/material-components-web@latest/dist/material-components-web.min.js"
                }
                script {
                    """
                    <!-- Privacy-friendly analytics by Plausible -->
                    <script async src="https://plausible.io/js/pa-YfdNM71HTeSfoPy9iyEIN.js"></script>
                    <script>
                      window.plausible=window.plausible||function(){(plausible.q=plausible.q||[]).push(arguments)},plausible.init=plausible.init||function(i){plausible.o=i||{}};
                      plausible.init()
                    </script>
                    """.trimIndent()
                }
                link {
                    rel = "stylesheet"
                    href = "https://fonts.googleapis.com/icon?family=Material+Icons"
                }
                link {
                    rel = "stylesheet"
                    href = "https://fonts.googleapis.com/icon?family=Playfair+Display"
                }
                link {
                    rel = "stylesheet"
                    href = "https://fonts.googleapis.com/icon?family=Nunito"
                }
                link {
                    rel = "stylesheet"
                    href = "https://fonts.googleapis.com/css2?family=JetBrains+Mono"
                }
                // --- new hreflang links ---
                link {
                    rel = "alternate"
                    href = "/en/"
                    attributes["hreflang"] = "en"
                }
                link {
                    rel = "alternate"
                    href = "/es/"
                    attributes["hreflang"] = "es"
                }
            }
            description.set("Soren Kai — writer and technologist exploring culture, AI, and belonging.")
            interceptUrls { enableSelfHosting() }
        }
    }
}

kotlin {
    // This example is frontend only. However, for a fullstack app, you can uncomment the includeServer parameter
    // and the `jvmMain` source set below.
    configAsKobwebApplication("web")

    sourceSets["jsMain"].kotlin.srcDirs(
        listOfNotNull(
            "src/main/kotlin/com/sorenkai/web", // shared core (models, components, layouts)
            when (System.getenv("SITE_LANG")) {
                "en" -> "src/main/kotlin/com/sorenkai/web/en"
                "es" -> "src/main/kotlin/com/sorenkai/web/es"
                else -> "src/main/kotlin/com/sorenkai/web/pages" // root redirect site
            },
            kotlin.sourceSets["jsMain"].kotlin.srcDirs.filterNot {
                it.path.contains("build/generated")
            }
        )
    )
    sourceSets["jsMain"].kotlin.exclude("**/build/generated/**")
    sourceSets["jsMain"].kotlin.exclude("**/generated/**")

    sourceSets {
//        commonMain.dependencies {
//          // Add shared dependencies between JS and JVM here if building a fullstack app
//        }

        jsMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.html.core)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            // This default template uses built-in SVG icons, but what's available is limited.
            // Uncomment the following if you want access to a large set of font-awesome icons:
            // implementation(libs.silk.icons.fa)
            implementation(libs.kobwebx.markdown)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.silk.icons.fa)
            implementation(libs.kotlin.js)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.jetbrains.markdown)
            implementation(npm("oidc-client-ts", "3.4.1"))
        }

        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
                implementation(libs.kotlinx.coroutines.test)
            }
        }

        // Uncomment the following if you pass `includeServer = true` into the `configAsKobwebApplication` call.
//        jvmMain.dependencies {
//            compileOnly(libs.kobweb.api) // Provided by Kobweb backend at runtime
//        }
    }

    tasks.register<Exec>("exportEn") {
        dependsOn("compileProductionExecutableKotlinJs")
        commandLine("kobweb", "export", "--env=prod")
        environment("SITE_LANG", "en")
    }

    tasks.register<Exec>("exportEs") {
        dependsOn("compileProductionExecutableKotlinJs")
        commandLine("kobweb", "export", "--env=prod")
        environment("SITE_LANG", "es")
    }

    ktlint {
        version.set("1.7.1")
        verbose.set(true)
        outputToConsole.set(true)
        ignoreFailures.set(false)
        filter {
            exclude { entry ->
                entry.file.path.contains("build/generated")
            }
        }
    }
    spotless {
        kotlin {
            target("src/**/*.kt")
            ktlint()
            trimTrailingWhitespace()
            endWithNewline()
        }
        kotlinGradle {
            target("**/*.gradle.kts")
            ktlint()
            trimTrailingWhitespace()
            endWithNewline()
        }
    }
}

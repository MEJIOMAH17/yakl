plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.ktlint)
    id("com.vanniktech.maven.publish") version "0.36.0"
}
kotlin {
    jvm()
}
allprojects {
    repositories {
        mavenCentral()
    }

    apply<org.jlleitschuh.gradle.ktlint.KtlintPlugin>()

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
subprojects {
    apply(plugin = "org.jetbrains.kotlin.multiplatform")
    apply(plugin = "com.vanniktech.maven.publish")
    kotlin {
        explicitApi = org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode.Strict
    }
    dependencies {
        val libs = rootProject.libs
        commonTestImplementation(libs.kotest)
        commonTestImplementation(libs.junit)
        commonTestImplementation(libs.junit.platform.launcher)
    }
    mavenPublishing {
        publishToMavenCentral()
        apply<SigningPlugin>()

        configure<SigningExtension> {
            val signingKeyLocation: String by project
            val secretKey = File(signingKeyLocation).readText()
            val signingPassword: String by project
            useInMemoryPgpKeys(secretKey, signingPassword)
            publishing.publications.configureEach {
                sign(this)
            }
        }
        signAllPublications()

        coordinates(group.toString(), name, version.toString())
        val nexusUsername: String by project

        pom {
            name = "An YAKL ${project.name} module"
            description = name.get()
            url = "https://github.com/MEJIOMAH17/yakl"
            licenses {
                license {
                    name = "MIT"
                    url = "https://opensource.org/license/mit/"
                }
            }
            developers {
                developer {
                    id = nexusUsername
                    name = "Mark Epshtein"
                    email = "epshteinme@gmail.com"
                }
            }
            scm {
                url = "scm:git:git://github.com/MEJIOMAH17/yakl.git"
                connection = "scm:git:ssh://git@github.com/MEJIOMAH17/yakl.git"
                developerConnection = "https://github.com/MEJIOMAH17/yakl"
            }
        }
    }
}

plugins {
    kotlin("jvm") version "1.6.10"
    java
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    id("maven-publish")
}

group = "com.github.mejiomah17.yakl"
version = "0.1.3"

allprojects {
    repositories {
        mavenCentral()
    }

    apply<org.jlleitschuh.gradle.ktlint.KtlintPlugin>()
    apply<org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin>()

    if (this != rootProject) {
        apply<MavenPublishPlugin>()
        apply<JavaPlugin>()
        java {
            withSourcesJar()
        }
        afterEvaluate {
            publishing {
                repositories {
                    maven {
                        url = uri("https://maven.pkg.github.com/MEJIOMAH17/yakl")
                        credentials {
                            val githubToken: String by project
                            val githubUser: String by project

                            username = githubUser
                            password = githubToken
                        }
                    }
                }
                publications {
                    create<MavenPublication>("maven") {
                        from(components["java"])
                        this.groupId = rootProject.group.toString()
                        this.artifactId = project.name
                        this.version = rootProject.version.toString()
                    }
                }
            }
        }
    }
    dependencies {
        testImplementation("io.kotest:kotest-assertions-core:5.0.3")
        testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }
}


import java.net.URI

plugins {
    kotlin("jvm") version "1.6.10"
    java
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    id("maven-publish")
}
allprojects {
    repositories {
        mavenCentral()
    }

    apply<org.jlleitschuh.gradle.ktlint.KtlintPlugin>()
    apply<org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin>()

    if (this != rootProject) {
        apply<MavenPublishPlugin>()
        apply<JavaPlugin>()
        apply<SigningPlugin>()
        java {
            withJavadocJar()
            withSourcesJar()
        }
        afterEvaluate {
            configure<SigningExtension> {
                val signingKeyLocation: String by project
                val secretKey = File(signingKeyLocation).readText()
                val signingPassword: String by project
                useInMemoryPgpKeys(secretKey, signingPassword)
                publishing.publications.configureEach {
                    sign(this)
                }
            }
            publishing {
                val nexusUsername: String by project
                publications {
                    create<MavenPublication>("maven") {
                        from(components["java"])
                    }
                    configureEach {
                        if (this !is MavenPublication) return@configureEach
                        if (name == "jvm") {
                            artifact(tasks.getByName("javadocJar")) {
                                classifier = "javadoc"
                            }
                        }
                        version = project.version.toString()
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
                    repositories {
                        maven {
                            val releasesRepoUrl =
                                URI.create("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                            name = "mavenCentral"
                            url = releasesRepoUrl
                            val nexusToken: String by project
                            credentials {
                                username = nexusUsername
                                password = nexusToken
                            }
                        }
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

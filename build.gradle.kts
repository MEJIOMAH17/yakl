plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
}

group = "com.github.mejiomah17"
version = "0.1.0"

allprojects {
    repositories {
        mavenCentral()
    }
    apply<org.jlleitschuh.gradle.ktlint.KtlintPlugin>()
    apply<org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin>()
    dependencies {
        testImplementation("io.kotest:kotest-assertions-core:5.0.3")
        testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

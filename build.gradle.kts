plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.ktlint)
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
    kotlin {
        explicitApi = org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode.Strict
    }
    dependencies {
        val libs = rootProject.libs
        commonTestImplementation(libs.kotest)
        commonTestImplementation(libs.junit)
        commonTestImplementation(libs.junit.platform.launcher)
    }
}

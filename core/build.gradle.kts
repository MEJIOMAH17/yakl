kotlin {
    explicitApi = org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode.Strict
}
dependencies {
    api(project(":api"))
    testImplementation("org.awaitility:awaitility-kotlin:4.1.1")
    testImplementation("io.mockk:mockk:1.12.2")
}
